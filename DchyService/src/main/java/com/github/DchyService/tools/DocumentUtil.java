package com.github.DchyService.tools;/**
 * @title: DocumentUtil
 * @projectName DchyService
 * @description: TODO
 * @author 14874
 * @date 2020/11/24 9:50
 */

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

/**
 *@ClassName DocumentUtil
 *@Description TODO
 *@Author maoZhan
 *@Date DATE{TIME}
 */
public class DocumentUtil {

    /* 转PDF格式值 */
    private static final int wdFormatPDF = 17;
    /**
     * Word文档转换
     *
     * @param inputFile
     * @param pdfFile
     */
    /*public static boolean word2PDF(String inputFile, String pdfFile) {
        ComThread.InitMTA(true);
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch doc = null;
        try {
            app = new ActiveXComponent("Word.Application");// 创建一个word对象
            app.setProperty("Visible", new Variant(false)); // 不可见打开word
            app.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch docs = app.getProperty("Documents").toDispatch();// 获取文挡属性

            System.out.println("打开文档 >>> " + inputFile);
            // Object[]第三个参数是表示“是否只读方式打开”
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            doc = Dispatch.call(docs, "Open", inputFile, false, true).toDispatch();
            System.out.println("转换文档 [" + inputFile + "] >>> [" + pdfFile + "]");
            // 调用Document对象的SaveAs方法，将文档保存为pdf格式
            // word保存为pdf格式宏，值为17
            Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF);// word保存为pdf格式宏，值为17

            long end = System.currentTimeMillis();

            System.out.println("用时：" + (end - start) + "ms.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========Error:文档转换失败：" + e.getMessage());
        } finally {
            Dispatch.call(doc, "Close", false);
            System.out.println("关闭文档");
            if (app != null)
                app.invoke("Quit", new Variant[] {});
            // 如果没有这句话,winword.exe进程将不会关闭
            ComThread.Release();
            ComThread.quitMainSTA();
        }
        return false;
    }*/

    public static void word2PDF2(String docFile,String pdfFile) {
        //加载word示例文档
        Document document = new Document();
        if (docFile.endsWith(".doc")){
            document.loadFromFile(docFile, com.spire.doc.FileFormat.Doc);
        }else if(docFile.endsWith(".docx")){
            document.loadFromFile(docFile, com.spire.doc.FileFormat.Docx);
        }
        //保存结果文件
        document.saveToFile(pdfFile, FileFormat.PDF);
        document.close();

        /*
        Document document = new Document();
        document.loadFromFile("Sample.docx");
        document.saveToFile("out/toPDF.pdf", FileFormat.PDF);
         */
    }
}
