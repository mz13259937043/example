package com.github.DchyService.tools.QRCodeutils;/**
 * @title: ExtractImages
 * @projectName DchyService
 * @description: TODO
 * @author 14874
 * @date 2020/12/18 17:17
 */

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.io.IOException;

/**
 *@ClassName 图片提取
 *@Description TODO
 *@Author maoZhan
 *@Date DATE{TIME}
 */
public class ExtractImages{
        /**
 * PDF to extract images from
 */
        //pdf路径
        public static final String SOURCE_PDF = "./src/test/resources/scanner.pdf";

        /**
         * Parses a PDF and extracts all the images.
         * 提取二维码图片
         * @param filename the source PDF
         * @param destination the directory to save images
         */
        public void extractImages(String filename, String destination)
                throws IOException, DocumentException {
            System.out.println("Processing PDF at " + filename);
            System.out.println("Saving images to " + destination);

            PdfReader reader = new PdfReader(filename);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            ImageRenderListener listener = new ImageRenderListener(destination + "/Img");
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                parser.processContent(i, listener);
            }
            reader.close();
        }

        /**
         * Main method.
         *
         * @param args no arguments needed
         * @throws DocumentException
         * @throws IOException
         */
        public static void main(String[] args) throws IOException, DocumentException {
            String sourcePDF = SOURCE_PDF;//读取图片地址
            String destination = "target";//图片输出地址
            if (args.length > 0) {
                sourcePDF = args[0];
                if (args.length > 1) {
                    destination = args[1];
                }
            }

            new ExtractImages().extractImages(sourcePDF, destination);
        }
}
