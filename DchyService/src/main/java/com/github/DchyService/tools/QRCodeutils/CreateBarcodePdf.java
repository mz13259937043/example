package com.github.DchyService.tools.QRCodeutils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
* @Description //创建二维码，写入到pdf中
* @Author maoZhan
* @Date 2020/12/18 17:45
* @param  * @param null
* @return
**/
public class CreateBarcodePdf {

    public static void main(String... args) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("./src/test/resources/scanner.pdf"));
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode("http://memorynotfound.com", 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.scaleAbsolute(100, 100);

        document.add(codeQrImage);

        document.close();
    }
}