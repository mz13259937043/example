package com.github.DchyService.controller.FTP;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.dao.*;
import com.github.DchyService.entity.*;
import com.github.DchyService.service.DchyHtxxService;
import com.github.DchyService.service.DchyYwxxService;
import com.github.DchyService.service.FjService;
import com.github.DchyService.tools.*;
import com.github.DchyService.tools.files.PDFUtil;
import com.github.pagehelper.StringUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("/ftpService")
@CrossOrigin
public class FtpFilesController {

    @Autowired
    private FtpUtil ftpUtil;

    @Value("${ftp.path}")
    private String rootPath;
    @Value("${attition.dchyServiceUrl}")
    private String dchyServiceUrl;

    @Autowired
    private FjService fjService;
    @Autowired
    private DchyHtxxService dchyHtxxService;
    @Autowired
    private DchySqyxDao dchySqyxDao;
    @Autowired
    private DchyYwxxService dchyYwxxService;
    @Autowired
    private DchyYwxxDao dchyYwxxDao;
    @Autowired
    private DchyPersonalDao dchyPersonalDao;
    @Autowired
    private ConvertyUtil convertyUtil;

    /**
    * @Description //?????????????????????????????????
    * @Author maoZhan
    * @Date 2020/10/27 15:27
    * @param  * @param files
     * @param userid
     * @param userName
     * @param filepath
     * @param pid
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @PostMapping("/multFileUpload")
    @Transactional
    public Map<String,Object> FileUpload(@RequestParam("files") MultipartFile[]files,
                                         @RequestParam(value = "userid",required = false) String userid,
                                         @RequestParam(value = "userName",required = false) String userName,
                                         @RequestParam(value = "filepath",required = false) String filepath, //?????????
                                         @RequestParam(value = "pid",required = false) String pid
                                         ) throws IOException {
        Map<String,Object> result = new HashMap<>();
        File f;
        InputStream is ;
        String fileName = "";
        boolean success = false;
        DchyFjFolder folder = new DchyFjFolder();
        DchyFjRecord record = new DchyFjRecord();
        List<DchyFjRecord> res = new ArrayList<>();
//        ????????????????????????
        f= new File(rootPath+"/"+filepath);
        try{
            if(!f.exists()) {
//            ????????????
                folder.setName(filepath.substring(filepath.lastIndexOf("/")+1,filepath.length()));
                folder.setPath(filepath);
                folder.setUserid(userid);
                folder.setUsername(userName);
                folder = fjService.addFolder(folder);
            }else{
                folder = fjService.getFolderByPath(filepath);
                res = folder.getDchyFjRecords();
            }
//        ????????????
            for(MultipartFile file:files){
                fileName = file.getOriginalFilename();
                f = ftpUtil.MultipartFileToFile(file);
                is = new FileInputStream(f);
//                ?????????????????????????????????????????????
                success = ftpUtil.uploadFile(filepath , fileName, is);
//            success = ftpUtil.uploadfile(filepath , fileName, f);
                if(success){
//                ??????fj???
                    record.setFilename(fileName);
                    record.setFilepath(filepath);
                    record.setFilesize(file.getSize()+"");
                    record.setFolderid(folder.getId());
                    record.setUserid(userid);
                    record.setUsername(userName);
                    record = fjService.addRecord(record);
                    res.add(record);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//        folder = fjService.getFolderByKey(folder.getId());
        folder.setDchyFjRecords(res);
        result.put("data",folder);
//        ???????????????
//        ???????????????

        return result;
    }

    /**
    * @Description //????????????????????????????????????
    * @Author maoZhan
    * @Date 2020/10/30 11:49
    * @param  * @param filename
     * @param tag
     * @param request
     * @param response
    * @return void
    **/
    @PostMapping("/downloadfile")
    public void downloadfile(
            @RequestParam(value = "filename") String filename,
            @RequestParam(value = "tag",required = false) String tag ,
            HttpServletRequest request,
            HttpServletResponse response)throws Exception
    {
        String localpath=rootPath;
        filename = java.net.URLDecoder.decode(filename,"UTF-8");
        String realName =filename.substring(filename.lastIndexOf("/") + 1);
        String rootName =filename.substring(0,filename.lastIndexOf("/"));
        String fileName=realName;
        if(realName.lastIndexOf(".") != -1){
            realName = realName.substring(0, realName.lastIndexOf("."));
        }
        //????????????????????????
        response.setHeader("Access-Control-Allow-Origin", "*");
        OutputStream out = response.getOutputStream();

        String filesave="";
        if (tag == null || "".equals(tag)) {
            filesave = filename.indexOf(".") == -1 ? realName : filename.substring(filename.lastIndexOf("/") + 1);
        }else {
            filesave = tag;
        }
        //???????????????????????????
        filesave = filesave.replace(",","???");
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (!userAgent.contains("webkit")) {
            // IE
            filesave = URLEncoder.encode(filesave, "UTF-8");
        } else {
            // ???IE
            filesave = new String(filesave.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//        PrintWriter fw = response.getWriter();            //??????response??????????????????
        if(filename.indexOf(".") == -1){
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filesave+".zip", "UTF-8"));
            ftpUtil.downLoadDirectory(rootName, fileName, true,out);
        }else{
            response.setHeader("content-disposition", "attachment;filename=" + filesave);
            ftpUtil.downloadFile(rootName, fileName, true,out);
        }

    }
    /**
    * @Description //??????????????????
    * @Author maoZhan
    * @Date 2020/10/29 18:14
    * @param  * @param filepath
     * @param response
    * @return void
    **/
    @PostMapping("/verifyFile")
    public void verifyFile(@RequestBody String filepath, HttpServletResponse response) throws IOException{
        String path = URLDecoder.decode(filepath, "utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean verifyFile = ftpUtil.verifyFile(path);
        if(!verifyFile){

        }else{
        }
    }
    @RequestMapping("/deleteFiles")
    @Transactional
    public boolean delete1(HttpServletResponse response,
                           HttpServletRequest request,
                           @RequestParam(value = "filepath", required = true) String filepath,
                           @RequestParam(value = "filename", required = true) String filename,
                           @RequestParam(value = "fjid", required = true) String fjid){
        boolean suc = false;
        /*
        * ??????????????????????????????
        * ???????????????????????????????????????
        * ???filepath???
        *  */
        try {
            if (StringUtil.isNotEmpty(filename)){
//            ?????????
                suc = ftpUtil.deleteDoc(filepath+"/"+filename);
                if (suc){
                    fjService.deleteRecordByKey(fjid);
                }
            }else{
//            ????????????,????????????????????????????????????
                suc = ftpUtil.deleteDoc(filepath);
                if (suc){
                    suc = fjService.deleteDir(fjid);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return suc;
        /*if(isdir){
//            ???????????????????????????
            DchyFjFolder folder = fjService.getFolderByKey(fjid);
            if (folder!=null){
                suc = fjService.deleteFolderByKey(fjid);
                if (suc){
//                    getpath?????????????????????????????????????????????????????????
                    suc = ftpUtil.deleteDoc(rootPath + folder.getPath());
                }
            }
        }else {
//            ???????????????????????????????????????
            DchyFjRecord record = fjService.getRecordByKey(fjid);
            if (record!=null){
                suc = fjService.deleteRecordByKey(fjid);
                if (suc){
                    suc = ftpUtil.deleteDoc(rootPath + record.getFilepath());
                }
            }
        }*/
    }
    /**
    * @Description //????????????????????????
    * @Author maoZhan
    * @Date 2020/10/28 16:35
    * @param  * @param response
     * @param request
     * @param filename  ????????????????????????
     * @param filepath  ???????????????
     **/
    @RequestMapping("/deleteFiles1")
    public boolean delete1(
                       HttpServletResponse response,
                       HttpServletRequest request,
                       @RequestParam(value = "filename", required = true) String filename,
                       @RequestParam(value = "filepath", required = true) String filepath
                      )throws Exception{
        boolean suc = false;
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
//            ??????????????????????????????
            String fileNamereal = "";
            if (!StringUtil.isEmpty(filepath)&&filepath.equals("/")) {
                fileNamereal = filename;
            } else {
                fileNamereal = filepath + "/" + filename;
            }

            suc = ftpUtil.deleteDoc(fileNamereal);
        }catch (Exception e){
            e.printStackTrace();
        }
        return suc;
    }

    @GetMapping("/listFilesByPath")
    public Map<String,Object> listFiles(
            @RequestParam(value = "filepath", required=true) String filepath
    ){
        Map<String,Object> result = new HashMap<>(16);

        DchyFjFolder folder = fjService.getFolderByPath(filepath);

        result.put("data",folder);
        return result;
    }

    @GetMapping("/listFilesByFolderId")
    public Map<String,Object> listFilesByFolderId(
            @RequestParam(value = "id", required=true) String id
    ){
        Map<String,Object> result = new HashMap<>(16);
        DchyFjFolder folder = fjService.getFolderByKey(id);
        result.put("data",folder);
        return result;
    }

    @PostMapping("/createNoticeFile")
    @Transactional
    public void createNoticeFile(
            @RequestParam(value = "uploadPath", required=false) String uploadPath,
            @RequestParam (value = "downloadPath", required=false)String downloadPath,
            @RequestParam (value = "uploadName", required=false)String uploadName,
            @RequestParam (value = "downloadName", required=false)String downloadName,
            @RequestParam (value = "username", required=false)String username,
            @RequestParam (value = "userid", required=false)String userid,
            @RequestParam (value = "pid", required=false)String pid,
            @RequestParam (value = "ywxxid", required=false)String ywxxid,
            @RequestParam (value = "downloadType", required=false)String downloadType,
            @RequestParam (value = "withQrcode", required=false)Boolean withQrcode,
            HttpServletRequest request,
            HttpServletResponse response)throws Exception{
        String temptPath = request.getSession().getServletContext().getRealPath("/");
//        ???????????????????????????
        Map<String,Object> dataMap = new HashMap<String, Object>();
        OutputStream os = response.getOutputStream();
        LocalDateTime ldt = LocalDateTime.now();
        DchyFjFolder folder = null;
        DchyFjRecord record = null;
        Timestamp curr = null;
        String folderid = "";
        Boolean suc = false;
        DchyHtxx htxx = null;
        DchyYwxx ywxx = null;
        QRCodeUtil qr = new QRCodeUtil();

//        ????????????
        dataMap = toCreateArg(pid,ywxxid);
        if (withQrcode) {               //??????????????????
            //            ????????????????????????
            String temptImage = "qrcode.png";
            qr.generateQRCodeImage(dchyServiceUrl+"/"+"cgshym?id="+pid+"&ywxxid="+ywxxid,350,350,temptPath+"/"+temptImage);
            String img64 = qr.get_b64_pic(temptPath+"/"+temptImage);
            dataMap.put("image",img64);
        }
        switch (downloadType){
            case "??????":
                htxx = dchyHtxxService.getByPid(pid);
                if (htxx!=null){
                    folderid = htxx.getZxtzsid();
                    folder = fjService.getFolderByKey(folderid);
                }
                break;
            case "??????":
                ywxx = dchyYwxxDao.findById(ywxxid);
                if (ywxx!=null){
                    folderid = ywxx.getCgshjlsid();
                    folder = fjService.getFolderByKey(folderid);
                }
                break;
        }
        try {
//            ????????????????????????????
            if (folder!=null){
//                ????????????????????????
//                ftpUtil.downloadFile(folder.getPath(),uploadName,true,os);
            }else{
                curr = new Timestamp(System.currentTimeMillis());
//                folder = new DchyFjFolder();
//                record = new DchyFjRecord();
//                ?????????????????????????????????tomcat????????????
                File templateFile = new File(temptPath+downloadName);
                File temporaryDocFile = new File(temptPath+uploadName.replace(".pdf",".doc"));           //????????????????????????
                File temporaryPdfFile = new File(temptPath+uploadName);            //?????????ftp?????????

                ftpUtil.createTemplateFile(downloadPath,downloadName,dataMap,temporaryDocFile,templateFile);
                //        ???????????????doc???pdf
//                    DocumentUtil.word2PDF2(temporaryDocFile.getPath(),temporaryPdfFile.getPath());
                PDFUtil.word2PDF(temporaryDocFile.getPath(),temporaryPdfFile.getPath());
//                ???????????????????????????ftp???,??????????????????
                FileInputStream is = new FileInputStream(temporaryPdfFile);
                suc = ftpUtil.uploadFile(uploadPath,uploadName,is);
//                ??????fjrecord??????,??????
                /*folder.setName("?????????");
                folder.setPath(uploadPath);
                folder.setUserid(userid);
                folder.setUsername(username);
                folder.setCreattime(curr);*/
                folder = new DchyFjFolder("?????????",uploadPath,null,userid,username,curr,null,null);
                folder = fjService.addFolder(folder);
                record = new DchyFjRecord(uploadName,uploadPath+"/"+uploadName,convertyUtil.calFileSize(temporaryPdfFile),folder.getId(),userid,username,curr);
                /*record.setUsername(username);
                record.setUserid(userid);
                record.setFilesize(convertyUtil.calFileSize(temporaryPdfFile));      //??????????????????
                record.setFilename(uploadName);
                record.setFilepath(uploadPath+"/"+uploadName);
                record.setFolderid(folder.getId());           //?????????????????????
                record.setCreattime(curr);*/
                record = fjService.addRecord(record);
                folder = fjService.getFolderByKey(folder.getId());      //????????????
//                    ???fj?????????????????????
                if ("??????".equals(downloadType)){
                    htxx.setZxtzsid(folder.getId());
                    dchyHtxxService.update(htxx);
                }else{
                    ywxx.setCgshjlsid(folder.getId());
                    dchyYwxxService.update(ywxx);
                }
                temporaryPdfFile.delete();       //??????????????????
                temporaryDocFile.delete();
                File tempImage = new File(temptPath+"/qrcode.png");
                tempImage.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            os.flush();
            os.close();
        }
    }

    private Map<String,Object> toCreateArg(String pid,String ywxxid) {
        LocalDateTime ldt =  LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy???MM???dd???");
//    ????????????????????????????????????????????????????????????
//    ?????????????????????????????????????????????date???
//    ??????????????????????????????date??????????????????
        Map<String,Object> res = new HashMap<>();
//        HashMap<String,Object> ress = new HashMap<>();
        List<Map<String, Object>> result = dchySqyxDao.getZbGg(pid);            //??????
        Map<String,Object> map = result.get(0);
        res.put("xmmc",map.get("XMMC"));
        res.put("yzdw",map.get("WTDW"));
        res.put("xmdz",map.get("XMDZ"));
        res.put("xmbh",map.get("DCHYBH"));

        res.put("chdw",map.get("CHDWMC"));
//        ??????????????????????????????
        String zxje = (String) map.get("XMBJ");
        if (zxje!=null){
            BigDecimal numberOfMoney = new BigDecimal(zxje);
            String zxje2 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
            res.put("zxje",zxje2+"??????"+zxje+"???");
        }
        res.put("date",ldt.format(df));

        if (StringUtil.isNotEmpty(ywxxid)) {
//            res.put("shjg","??????");
//            res.put("shsj",ldt.format(df));
//            res.put("shfj",map.get("CHDWMC"));
            DchyYwxx ywxx = dchyYwxxDao.findById(ywxxid);
            res.put("xmbh",ywxx.getCgbh());
            res.put("shsj",ldt.format(df));
            if("?????????".equals(ywxx.getCgsfsh())){
                res.put("shjg","??????");
            }else{
                String reason = StringUtil.isEmpty(ywxx.getCgshyj())?"":ywxx.getCgshyj();
                res.put("shjg","?????????:??????"+reason);
            }
            String cgshrid = ywxx.getCgshrid();
            DchyPersonal person = dchyPersonalDao.queryById(cgshrid);
            if (person!=null){
                res.put("shfj",person.getSsbm()==null?"":person.getSsbm());
            }
        }
        return res;
    }

//        ????????????????????????????????????
//    ????????????????????????????????????????????????????????????
//    ??????????????????????????????
//    ?????????????????????????????????
    /**
    * @Description //??????
    * @Author maoZhan
    * @Date 2020/11/12 15:05
    * @param  * @param files
     * @param filepath
     * @param filename
     * @param folderid
     * @param parentid
    * @return void
    **/
    @PostMapping("/fileUpload")
    public Map<String,Object> fileUpload(
            @RequestParam (value = "files",required = false) MultipartFile file,     //????????????????????????
            @RequestParam (value = "filepath",required = false) String filepath,        //???????????????????????????
            @RequestParam (value = "filename",required = false) String filename,        //???????????????????????????????????????
            @RequestParam (value = "folderid",required = false) String folderid,        //??????????????????id
            @RequestParam (value = "parentid",required = false) String parentid,         //?????????????????????id
            @RequestParam (value = "userid",required = false) String userid,         //?????????id
            @RequestParam (value = "username",required = false) String username         //???????????????
    ) throws IOException {
        Map<String,Object> res = new HashMap<>(16);
        String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        DchyFjFolder folder = new DchyFjFolder();
        DchyFjRecord record = new DchyFjRecord();
        List<DchyFjRecord> records = new ArrayList<>();
        InputStream is = null;
        File f = null;
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        boolean success = false;
        String currentpath = "";           //?????????
        String fullpath = "";           //?????????

//        ???????????????
        if (StringUtil.isNotEmpty(parentid)){
            folder = this.fjService.getFolderByKey(parentid);
            currentpath = folder.getPath();
        }
        if (StringUtil.isNotEmpty(filename)){
            fullpath = currentpath+"/"+filepath;
//            ????????????
//            ??????????????????????????????????????????
            DchyFjFolder  folder2 =  fjService.getFolderByKey(parentid);
            boolean flag = false;
            String refid = "";
            if (folder2.getDchyFjFolders().size()>0){
                for (DchyFjFolder re:folder2.getDchyFjFolders()) {
                    if (StringUtil.isNotEmpty(re.getPath())&&re.getPath().equals(filepath)){
                        refid = re.getId();
                        flag = true;
                        break;
                    }
                }
            }

            if (flag){ // ???????????????????????????  ????????????????????????????????????
                record.setFolderid(refid);
            } else {
                // ???????????????
                folder = new DchyFjFolder();
                folder.setPath(filepath);
                folder.setParentid(parentid);
                folder.setCreattime(curr);
                folder.setUserid(userid);
                folder.setUsername(username);
                DchyFjFolder  fjf = this.fjService.addFolder(folder);
                record.setFolderid(fjf.getId());
            }

//            ftp????????????
            f = ftpUtil.MultipartFileToFile(file);
            is = new FileInputStream(f);
            success = ftpUtil.uploadFile(fullpath,filename,is);
            record.setFilepath(filepath);
            record.setFilename(filename);
            record.setCreattime(curr);
            record.setUserid(userid);
            record.setUsername(username);
            record.setFilesize(convertyUtil.calFileSize(f));
            DchyFjRecord fr =  this.fjService.addRecord(record);
            res.put("data",fr);
            res.put("msg","");
            res.put("state",success?"????????????":"????????????");
        }else {
//            ???????????????
//            ??????????????????
            folder = this.fjService.getFolderByPath(filepath);
            if (folder!=null){
                res.put("msg","??????????????????????????????????????????");
                res.put("data",folder);
                res.put("state",success?"????????????":"????????????");
                return res;
            }
            fullpath += filepath;
//            ftp???????????????
            success = ftpUtil.createDir(fullpath);
            String folderName = "";
            String[] pathArr = fullpath.split("/");
//            ??????uuid??????????????????uuid?????????????????????
//            ???????????????uuid???????????????????????????????????????????????????
            int count=0;
            for (String str:pathArr){
                if(str.matches(regex)){
                    count++;
                }
            }
            if(pathArr[pathArr.length-1].matches(regex)&&count==1){
                folderName = "?????????";
            }else if (pathArr[pathArr.length-1].matches(regex)&&count>1){
                folderName = pathArr[pathArr.length-2]+"?????????";
            }else {
//                ?????????????????????
                folderName = pathArr[pathArr.length-1];
            }
            folder = new DchyFjFolder();
            folder.setPath(filepath);
            folder.setName(folderName);
            folder.setParentid(parentid);
            folder.setCreattime(curr);
            folder.setUsername(username);
            folder.setUserid(userid);
            this.fjService.addFolder(folder);
            res.put("data",folder);
            res.put("msg","");
            res.put("state",success?"????????????":"????????????");
        }
        return res;
    }
    @PostMapping("/fileUpload1")
    public Map<String,Object> fileUpload1(
            @RequestParam (value = "files",required = false) MultipartFile file,     //????????????????????????
            @RequestParam (value = "filepath",required = false) String filepath,        //???????????????????????????
            @RequestParam (value = "filename",required = false) String filename,        //???????????????????????????????????????
            @RequestParam (value = "folderid",required = false) String folderid,        //??????????????????id
            @RequestParam (value = "parentid",required = false) String parentid         //?????????????????????id
    ) throws IOException {
        Map<String,Object> res = new HashMap<>(16);
        String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        DchyFjFolder folder = new DchyFjFolder();
        DchyFjRecord record = new DchyFjRecord();
        List<DchyFjRecord> records = new ArrayList<>();
        InputStream is = null;
        File f = null;
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        boolean success = false;

        if (StringUtil.isNotEmpty(filename)){
//            ????????????
//            ??????????????????????????????????????????
            records = this.fjService.getRecordByFolderId(folderid);
            if (records.size()>0){
                for (DchyFjRecord re:records) {
                    if (StringUtil.isNotEmpty(re.getFilename())&&re.getFilename()==filename){
                        res.put("data",re);
                        res.put("msg","??????????????????????????????");
                        res.put("state",success?"????????????":"????????????");
                        System.out.println("??????????????????????????????");
                        break;
                    }
                }
                return res;
            }
//            ftp????????????
            f = ftpUtil.MultipartFileToFile(file);
            is = new FileInputStream(f);
            success = ftpUtil.uploadFile(filepath,filename,is);
            record.setFilepath(filepath+"/"+filename);
            record.setFolderid(folderid);           //??????????????????id????????????
            record.setFilename(filename);           //?????????????????????????????????
            record.setCreattime(curr);
            this.fjService.addRecord(record);
            res.put("data",record);
            res.put("msg","");
            res.put("state",success?"????????????":"????????????");
        }else {
//            ???????????????
//            ??????????????????
            folder = this.fjService.getFolderByPath(filepath);
            if (folder!=null){
                res.put("msg","??????????????????????????????????????????");
                res.put("data",folder);
                res.put("state",success?"????????????":"????????????");
                return res;
            }
//            ftp???????????????
            success = ftpUtil.createDir(filepath);
            String folderName = "";
            String[] pathArr = filepath.split("/");
//            ??????uuid??????????????????uuid?????????????????????
//            ???????????????uuid???????????????????????????????????????????????????
            int count=0;
            for (String str:pathArr){
                if(str.matches(regex)){
                    count++;
                }
            }
            if(pathArr[pathArr.length-1].matches(regex)&&count==1){
                folderName = "?????????";
            }else if (pathArr[pathArr.length-1].matches(regex)&&count>1){
                folderName = pathArr[pathArr.length-2]+"?????????";
            }else {
//                ?????????????????????
                folderName = pathArr[pathArr.length-1];
            }
            folder = new DchyFjFolder();
            folder.setPath(filepath);
            folder.setName(folderName);
            folder.setParentid(parentid);
            folder.setCreattime(curr);
            this.fjService.addFolder(folder);
            res.put("data",folder);
            res.put("msg","");
            res.put("state",success?"????????????":"????????????");
        }
        return res;
    }
    /*
        ????????????path??????id?????????????????????????????????????????????????????????,????????????????????????
        ????????????????????????????????????????????????????????????????????????????????????        ????????????
         */
    @PostMapping("/listFile2")
    public DchyFjFolder listFiles2(
            @RequestParam(value = "filepath",required = false) String filepath,
            @RequestParam(value = "folderid",required = false) String folderid
    ){
//        ??????????????????????????????????????????
        DchyFjFolder folder = new DchyFjFolder();
        folder =  fjService.getFolderByKey(folderid);
        return folder;
    }
    @PostMapping("/listFile1")
    public void listFiles1(){

    }
}
