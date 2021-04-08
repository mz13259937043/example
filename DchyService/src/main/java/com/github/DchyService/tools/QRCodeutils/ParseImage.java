package com.github.DchyService.tools.QRCodeutils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
* @Description //图片解析（识别二维码）
* @Author maoZhan
* @Date 2020/12/18 17:55
* @param  * @param null
* @return
**/
public class ParseImage {

    public static void main(String[] args) throws NotFoundException, IOException {
        String decode = decode("./target/Img-1.png");//识别图片路径
        System.out.println(decode);//输出到控制台
    }
    public static String decode(String filepath) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filepath));
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);
        HashMap<DecodeHintType, Object> decodeHints = new HashMap<DecodeHintType, Object>();
        decodeHints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(bitmap, decodeHints);
        return result.getText();
    }
}