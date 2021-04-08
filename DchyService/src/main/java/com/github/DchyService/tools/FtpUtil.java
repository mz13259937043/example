package com.github.DchyService.tools;

import cn.hutool.extra.ftp.Ftp;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.net.ftp.FtpClient;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Map;

@Component
public class FtpUtil {
    @Value("${ftp.host}")
    private String ftphost;
    @Value("${ftp.port}")
    private int ftpport;
    @Value("${ftp.usr}")
    private String ftpuser;
    @Value("${ftp.pwd}")
    private String ftppassword;
    @Value("${ftp.path}")
    private String rootpath;

    static String LOCAL_CHARSET = "GBK";

    private FTPClient ftpClient;

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
//        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:"+this.ftphost+":"+this.ftpport);
            ftpClient.connect(ftphost, ftpport); //连接ftp服务器
            ftpClient.login(ftpuser, ftppassword); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed...ftp服务器:"+this.ftphost+":"+this.ftpport);
            }
            System.out.println("connect successfu...ftp服务器:"+this.ftphost+":"+this.ftpport);
            ftpClient.enterLocalPassiveMode();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    @Value("${ftp.filepath}")
//    private String filepath;
    public static void main(String[] args) throws IOException {
        String a ="1445151-854";
        String[] split = a.split("-");
        System.out.println(split[0]);
        System.out.println(split[1]);
    }

    public File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Description: 向FTP服务器上传文件
     * host FTP服务器hostname
     * port FTP服务器端口
     * username FTP登录账号
     * password FTP登录密码
     *
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String filePath, String filename, InputStream input) throws IOException {
        boolean result = false;
        initFtpClient();
//        ftp.setControlEncoding(LOCAL_CHARSET);
//        ftp.setControlEncoding("UTF-8");
//        ftp.sendCommand("OPTS UTF8", "ON");
        ftpClient.printWorkingDirectory();
        try {
            String[] pathArray=filePath.split("/");
            for (int j = 0; j < pathArray.length; j++) {
                if(!ftpClient.changeWorkingDirectory(new String(pathArray[j].getBytes(LOCAL_CHARSET),"iso-8859-1"))) {
                    if(ftpClient.makeDirectory(new String(pathArray[j].getBytes(LOCAL_CHARSET),"iso-8859-1"))){
                        ftpClient.changeWorkingDirectory((new String(pathArray[j].getBytes(LOCAL_CHARSET),"iso-8859-1")));
                    };
                };
            };
            //设置为被动模式
            ftpClient.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            ftpClient.printWorkingDirectory();
            if (!ftpClient.storeFile(new String(filename.getBytes(LOCAL_CHARSET),"iso-8859-1"), input)) {
                return false;
            }
            input.close();
            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }




    /** * 删除文件 *
     * @param
     * @param filename 要删除的文件名称
     * @return */
    public boolean deleteFile(String filePath, String filename) throws IOException {
        boolean result = false;
        initFtpClient();
        try {
            if(ftpClient.changeWorkingDirectory(new String(filePath.getBytes(LOCAL_CHARSET),"iso-8859-1"))){
                String ax = ftpClient.printWorkingDirectory();
                System.out.println(ax);
                FTPFile[] fs = ftpClient.listFiles();
                for (FTPFile f : fs) {
                    if (f.isFile()) {
                        ftpClient.deleteFile((new String(f.getName().getBytes(LOCAL_CHARSET),"iso-8859-1")));
                    }
                }
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {

                }
            }
        }
        return result;
    }

    public boolean downloadFileZip(String DirectoryPath, String directoryName, boolean binary, OutputStream out) {
        initFtpClient();
        boolean suc = false;
        try {
            ZipCompressor zipCompressor = new ZipCompressor(out, ftpClient, DirectoryPath);
                                    //参数目录名
            zipCompressor.compress(directoryName);
            suc = true;
            out.close();
        } catch (Exception e) {
            System.out.println("下载失败！");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return suc;
    }

    /**
    * @Description //删除文件或文件夹
    * @Author maoZhan
    * @Date 2020/10/28 16:10
    * @param  * @param remotePath（文件或文件加的全路径）
    * @return boolean
    **/
    public boolean deleteDoc(String remotePath){
        initFtpClient();
        boolean suc = false;
        try {
            System.out.println("delete service param remote path is :"
                    + remotePath);
            if (ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(LOCAL_CHARSET), "iso-8859-1"))) {
                ftpClient.printWorkingDirectory();
                FTPFile[] files = ftpClient.listFiles();
//                递归删除目录的文件和文件夹
                for (FTPFile f : files) {
                    if (f.isFile()) {
                        System.out.println("delete file, file name is:"+ f.getName());
                        ftpClient.deleteFile(f.getName());
                        suc =true;
                    }
                    if (f.isDirectory()) {
                        String newRemotePath = remotePath + "/" + f.getName();
                        System.out.println("loop deleteimpl new remote path is:"+ newRemotePath);
                        this.deleteDoc(newRemotePath);
                        suc =true;
                    }
                }
                ftpClient.changeToParentDirectory();
                ftpClient.removeDirectory(new String(remotePath.getBytes(LOCAL_CHARSET), "iso-8859-1"));
            } else {
                ftpClient.deleteFile(new String(remotePath.getBytes(LOCAL_CHARSET), "iso-8859-1"));
                suc =true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return suc;
        }
        return suc;
    }

    /**
    * @Description //打包下载
    * @Author maoZhan
    * @Date 2020/10/30 12:51
    * @param  * @param DirectoryPath
     * @param directoryName
     * @param binary
     * @param out
    * @return void
    **/
    public void downLoadDirectory(String DirectoryPath, String directoryName, boolean binary, OutputStream out) {
        initFtpClient();
        ftpClient.enterLocalPassiveMode();
        try {
            ZipCompressor zipCompressor = new ZipCompressor(out, ftpClient, DirectoryPath);
            zipCompressor.compress(directoryName);
            out.close();
        } catch (Exception e) {
            System.out.println("下载失败！");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
    * @Description //单文件下载
    * @Author maoZhan
    * @Date 2020/10/30 11:48
    * @param  * @param file
     * @param remote
     * @param binary
     * @param outputstream
    * @return void
    **/
    public void downloadFile(String file, String remote, boolean binary,
                             OutputStream outputstream) {
        initFtpClient();
        try {
            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(new String(file
                    .getBytes(LOCAL_CHARSET), "iso-8859-1"));
            ftpClient.enterLocalPassiveMode();
            if (binary) {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            }
            int n = -1;
            int bufferSize = 20;
//            int bufferSize = ftpClient.getBufferSize();
            byte[] buffer = new byte[bufferSize];
            InputStream fileInputStream = ftpClient
                    .retrieveFileStream(new String(remote
                            .getBytes(LOCAL_CHARSET), "iso-8859-1"));
            while ((n = fileInputStream.read(buffer)) != -1) {
                outputstream.write(buffer, 0, n);
            }
            fileInputStream.close();
            outputstream.flush();
            outputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
    * @Description //校验目录下的文件
    * @Author maoZhan
    * @Date 2020/10/28 16:12
    * @param  * @param path
    * @return boolean
    **/
    public boolean verifyFile(String path){
        initFtpClient();
        OutputStream os=null;
        String realName = path.substring(path.lastIndexOf("/") + 1);
        String rootName = path.substring(0, path.lastIndexOf("/"));
        try {
            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(new String(rootName.getBytes(LOCAL_CHARSET), "iso-8859-1"));
            if (changeWorkingDirectory) {
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for(FTPFile file : ftpFiles){
                    File localFile = new File(rootpath+"/"+rootName + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    os.close();
                }
                ftpClient.logout();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;

    }
    /**
    * @Description //下载中选通知书
    * @Author maoZhan
    * @Date 2020/11/14 16:10
    * @param  * @param filename
     * @param os
     * @param outFile
    * @return void
    **/
    public void downloadZxtzs(OutputStream os, File outFile) {
        try {
            FileInputStream fin = new FileInputStream(outFile);
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                os.write(buffer, 0, bytesToRead);
            }
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    public void downloadZxtzs2(Map<String,Object> dataMap, String filename, OutputStream os, File outFile) throws IOException, TemplateException {
        initFtpClient();
        try {
            ftpClient.printWorkingDirectory();
            Configuration configuration = new Configuration(new Version("2.3.0"));
            configuration.setDefaultEncoding("utf-8");
            /*if (ftpClient.changeWorkingDirectory(new String((rootpath).getBytes(LOCAL_CHARSET), "iso-8859-1"))){
                if (ftpClient.changeWorkingDirectory(new String((rootpath).getBytes(LOCAL_CHARSET), "iso-8859-1"))){
                    configuration.setDirectoryForTemplateLoading(new File(rootpath+filename));
                }
            }*/
            configuration.setDirectoryForTemplateLoading(new File(rootpath+filename));
            Template template = configuration.getTemplate("中选通知书.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
            template.process(dataMap, out);


            FileInputStream fin = new FileInputStream(outFile);
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                os.write(buffer, 0, bytesToRead);
            }
            os.flush();
            os.close();
            out.close();
        }catch (Exception e ){
            e.printStackTrace();
        }

    }

    /**
    * @Description //TODO
    * @Author maoZhan
    * @Date 2020/12/21 10:48
    * @param  * @param downloadPath
     * @param downloadName  ftl的模板文件名
     * @param dataMap   参数设置
     * @param temporaryDocFile  临时的doc文件
     * @param templateFile     ftl的模板文件
    * @return void
    **/
    public void createTemplateFile(String downloadPath,String downloadName,Map<String, Object> dataMap,File temporaryDocFile,File templateFile) throws IOException, TemplateException {
        initFtpClient();
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        /*
            将其ftp模板下载成流，转为file对象
            指定ftp模板文件和目录
            生成动态模板文件file2.doc
            将该doc文件转为pdf
         */
        boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(new String(downloadPath.getBytes(LOCAL_CHARSET), "iso-8859-1"));
        ftpClient.enterLocalPassiveMode();
        InputStream is = ftpClient.retrieveFileStream(new String(downloadName.getBytes(LOCAL_CHARSET), "iso-8859-1"));
        FileUtils.copyInputStreamToFile(is, templateFile);          //流转为文件（ftl）
        configuration.setDirectoryForTemplateLoading(new File(templateFile.getParent()));
        Template template = configuration.getTemplate(downloadName, "utf-8");
        FileOutputStream docOs  = new FileOutputStream(temporaryDocFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(docOs, "utf-8"), 10240);
        template.process(dataMap, out);

        docOs.close();
//        删除对应的临时文件
        templateFile.delete();
//        docFile.delete();
    }

    public boolean createDir(String filepath) {
        initFtpClient();
        boolean success = false;
        String[] pathArray=filepath.split("/");
        try{
            for (int j = 0; j < pathArray.length; j++) {
                if(!ftpClient.changeWorkingDirectory(new String(pathArray[j].getBytes(LOCAL_CHARSET),"iso-8859-1"))) {
                    if(ftpClient.makeDirectory(new String(pathArray[j].getBytes(LOCAL_CHARSET),"iso-8859-1"))){
                        ftpClient.changeWorkingDirectory((new String(pathArray[j].getBytes(LOCAL_CHARSET),"iso-8859-1")));
                    };
                };
            };
            success = true;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return success;
    }

}
