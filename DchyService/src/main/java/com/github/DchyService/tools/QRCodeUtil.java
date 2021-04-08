package com.github.DchyService.tools;/**
 * @title: QRCode
 * @projectName DchyService
 * @description: TODO
 * @author 14874
 * @date 2020/12/21 10:03
 */

import com.github.DchyService.tools.QRCodeutils.ExtractImages;
import com.github.DchyService.tools.QRCodeutils.ImageRenderListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *@ClassName QRCode
 *@Description TODO
 *@Author maoZhan
 *@Date DATE{TIME}
 */
public class QRCodeUtil {

//    关于二维码的操作
    /*
    * 创建二维码、填入内容（调接口），
    * 根据模板生成word时，获取临时二维码图片的base64，替换word中原有的图片
    * 将word上传到ftp对应位置，删除临时文件（doc、pdf、png）
    * 从ftp部分下载该文件
    *
    *  */
    /**
    * @Description //直接在target生成二维码图片
    * @Author maoZhan
    * @Date 2020/12/22 9:43
    * @param  * @param text
     * @param width
     * @param height
     * @param filePath
    * @return void
    **/
    public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    /**
    * @Description //生成二维码
    * @Author maoZhan
    * @Date 2020/12/21 10:19
    * @param  * @param content 内容
     * @param tempFile  输出的临时文件pdf
    * @return void
    **/
    public void createQRCode(String content, String tempFile) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(tempFile));
        document.open();
//        PdfContentByte cb = writer.getDirectContent();
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(content, 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.scaleAbsolute(100, 100);
        document.add(codeQrImage);
        document.close();
    }
//    填充二维码内容
    public String setContent(String id) {
        String content = "asdfadsfas";



        return content;
    }

    /**
    * @Description //提取二维码图片
    * @Author maoZhan
    * @Date 2020/12/21 10:24
    * @param  * @param filename
     * @param templatePath
    * @return void
    **/
    public void extractImages(String pdffilename,String templatePath) throws IOException {
        PdfReader reader = new PdfReader(templatePath+"/"+pdffilename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        ImageRenderListener listener = new ImageRenderListener(templatePath + "/qrcode");
        parser.processContent(1, listener);
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            parser.processContent(i, listener);
        }
        reader.close();
    }
    /**
     * 获取图片的base64编码内容
     * param pic: 图片位置
     *  :return:
     */
    public String get_b64_pic(String imgUrl) {
        //imgFile = "C:/Users/Administrator/Desktop/12.png";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgUrl);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
}
