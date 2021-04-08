package com.github.DchyService.tools;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class ZipCompressor{
    static final int BUFFER = 8192;
    private OutputStream outputStream;
    private FTPClient ftpClient;
    private String DirectoryPath;

    public ZipCompressor(OutputStream fos,FTPClient ftpClient,String DirectoryPath)
    {
        this.outputStream = fos;
        this.ftpClient = ftpClient;
        this.DirectoryPath = DirectoryPath;
    }

    public void compress(String directoryName) {

        try {
            CheckedOutputStream cos = new CheckedOutputStream(this.outputStream, new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "";
            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(new String(DirectoryPath.getBytes("GBK"), "iso-8859-1"));
            FTPFile[] listFiles = ftpClient.listFiles();
            if(changeWorkingDirectory) {
                for (int i = 0; i < listFiles.length; i++) {
//                    目录下文件夹名相同进行打包
                    if(directoryName.equals(listFiles[i].getName())) {
                        compress(listFiles[i], out, basedir);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void compress(FTPFile directoryFile, ZipOutputStream out, String basedir)
    {
        try {
            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(new String(directoryFile.getName().getBytes("GBK"), "iso-8859-1"));
            if(changeWorkingDirectory) {
                FTPFile[] listFiles = ftpClient.listFiles();
                if(listFiles.length>0) {
                    for (int i = 0; i < listFiles.length; i++) {
                        if (listFiles[i].isDirectory()) {//文件夹
                            String path=directoryFile.getName()+"/"+listFiles[i].getName()+ "/";
                            compressDirectory(listFiles[i], out, path);
                        }else {//文件
                            System.out.println("压缩：" + directoryFile.getName()+"/"+ listFiles[i].getName());
                            compressFile(listFiles[i], out, directoryFile.getName()+"/");
                            System.out.println("压缩完毕：" + directoryFile.getName()+"/"+ listFiles[i].getName());
                        }
                    }
                }else {
                    ZipEntry entry = new ZipEntry(directoryFile.getName()+"//");
                    out.putNextEntry(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private void compressDirectory(FTPFile ftpFile, ZipOutputStream out, String basedir)
    {
        try{
            boolean changeDirectory = ftpClient.changeWorkingDirectory(new String(ftpFile.getName()
                    .getBytes("GBK"), "iso-8859-1"));
            FTPFile[] listFiles = ftpClient.listFiles();
            if (listFiles.length > 0) {
                if(changeDirectory) {
                    for (int i = 0; i < listFiles.length; i++) {
                        if(listFiles[i].isDirectory()) {//文件夹
                            String dirpath = basedir+ listFiles[i].getName() + "/";
                            compressDirectory(listFiles[i], out,dirpath);
                        }else {//文件
                            System.out.println("压缩：" + basedir + listFiles[i].getName());
                            compressFile(listFiles[i], out, basedir);
                        }
                    }
                    boolean changeToParentDirectory = ftpClient.changeToParentDirectory();
                    System.out.println(changeToParentDirectory);
                }
            }else {
                ZipEntry entry = new ZipEntry(basedir+"/");
                out.putNextEntry(entry);
                boolean changeToParentDirectory = ftpClient.changeToParentDirectory();
                System.out.println(changeToParentDirectory);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void compressFile(FTPFile fileName, ZipOutputStream out, String basedir)
    {

        try
        {
            String remote = new String(fileName.getName().getBytes("GBK"), "iso-8859-1");
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            System.out.println(ftpClient.printWorkingDirectory());
            boolean success = ftpClient.retrieveFile(remote, byteArray);
            if (success) {
                out.putNextEntry(new ZipEntry(basedir + fileName.getName()));
                out.write(byteArray.toByteArray());
                byteArray.close();
                out.closeEntry();
            } else {
                System.err.println("文件传输失败！");
            }
      /*InputStream retrieveFileStream = ftpClient.retrieveFileStream(new String(fileName.getName().getBytes("GBK"), "iso-8859-1"));
      System.out.println(retrieveFileStream.read());
      BufferedInputStream bis = new BufferedInputStream(retrieveFileStream);
      boolean completePendingCommand = ftpClient.completePendingCommand();//有一些FTPClient方法无法完成整个FTP事务序列以完成事务。
      if(!completePendingCommand){
            System.err.println("文件传输失败！");
      }
      ZipEntry entry = new ZipEntry(basedir + fileName.getName());
      out.putNextEntry(entry);

      byte[] data = new byte[2048];
      int count;
      while ((count = bis.read(data)) > 0) {
        out.write(data, 0, count);
        out.flush();
      }
      bis.close();
      out.closeEntry();
      retrieveFileStream.close();*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}