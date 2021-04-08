package com.github.DchyService.tools.QRCodeutils;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 14874 图片渲染监听
 * @title: ImageRenderListener
 * @projectName DchyService
 * @description: TODO
 * @date 2020/12/18 17:33
 */
public class ImageRenderListener implements RenderListener {
    final String name;
    int counter = 100000;
    public ImageRenderListener(String name) {
        this.name = name;
    }

    public void beginTextBlock() {
    }

    public void renderText(TextRenderInfo renderInfo) {
    }

    public void endTextBlock() {
    }

    public void renderImage(ImageRenderInfo renderInfo) {
        try {
            PdfImageObject image = renderInfo.getImage();
            if (image == null) return;
            int number = renderInfo.getRef() != null ? renderInfo.getRef().getNumber() : counter++;
            String filename = String.format("%s-%s.%s", name, number, image.getFileType());
            FileOutputStream os = new FileOutputStream(filename);
            os.write(image.getImageAsBytes());
            os.flush();
            os.close();

            PdfDictionary imageDictionary = image.getDictionary();
            PRStream maskStream = (PRStream) imageDictionary.getAsStream(PdfName.SMASK);
            if (maskStream != null) {
                PdfImageObject maskImage = new PdfImageObject(maskStream);
                filename = String.format("%s-%s-mask.%s", name, number, maskImage.getFileType());
                os = new FileOutputStream(filename);
                os.write(maskImage.getImageAsBytes());
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
