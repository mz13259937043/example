package com.github.DchyService.tools.QRCodeutils;


import com.itextpdf.text.DocumentException;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.automaticfields.PdfCompositeField;
import com.spire.pdf.automaticfields.PdfPageCountField;
import com.spire.pdf.automaticfields.PdfPageNumberField;
import com.spire.pdf.graphics.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 *
 * @author changsp
 * 2020年6月4日
 */
public class AddPdfPageNumbers {

    public static void main(String[] args) throws IOException, DocumentException {
        AddPdfPageNumbers a = new AddPdfPageNumbers();
        a.removeWatermark("D:/360驱动大师目录/cgshjls2.pdf");
    }
    public void removeWatermark(String url) throws IOException, DocumentException {
        //加载PDF文档
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(url);
        //添加一个空白页，目的为了删除jar包添加的水印，后面再移除这一页
        pdf.getPages().add();
        //创建字体
//        BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/simfang.ttf", BaseFont.IDENTITY_H,       //仿宋字体
//                BaseFont.NOT_EMBEDDED);//创建字体
//        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(bf, 20, com.itextpdf.text.Font.BOLD);//使用字体
        PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("宋体", Font.PLAIN, 10),true);

        //遍历文档中的页
        for (int i = 0; i < pdf.getPages().getCount(); i++) {
            Dimension2D pageSize = pdf.getPages().get(i).getSize();
            float y = (float) pageSize.getHeight() - 40;

            //初始化页码域
            PdfPageNumberField number = new PdfPageNumberField();

            //初始化总页数域
            PdfPageCountField count = new PdfPageCountField();

            //创建复合域
            PdfCompositeField compositeField = new PdfCompositeField(font, PdfBrushes.getBlack(), "第{0}页 共{1}页", number, count);

            //设置复合域内文字对齐方式
            compositeField.setStringFormat(new PdfStringFormat(PdfTextAlignment.Right, PdfVerticalAlignment.Top));

            //测量文字大小
            Dimension2D textSize = font.measureString(compositeField.getText());

            //设置复合域的在PDF页面上的位置及大小
            compositeField.setBounds(new Rectangle2D.Float(((float) pageSize.getWidth() - (float) textSize.getWidth())/2, y, (float) textSize.getWidth(), (float) textSize.getHeight()));

            //将复合域添加到PDF页面
            compositeField.draw(pdf.getPages().get(i).getCanvas());
        }
        //移除第一个页
        pdf.getPages().remove(pdf.getPages().get(pdf.getPages().getCount()-1));
        //保存为另外一个文档
        pdf.saveToFile(url);
        System.out.println("添加成功");
    }
}
