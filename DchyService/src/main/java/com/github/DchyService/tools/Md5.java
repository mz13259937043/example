package com.github.DchyService.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;

public class Md5 {
	
	//国家平台基本编码
	@Value("${pdf_gjptjbbm}")
	private static String gjptjbbm;
	//事项基本编码
	@Value("${pdf_sxjbbm}")
	private String sxjbbm;
	//受理单位统一社会信用代码
	@Value("${pdf_sldwtyshxydm}")
	private String sldwtyshxydm;
	//受理单位名称
	@Value("${pdf_sldwmc}")
	private String sldwmc;
	//事项所属区划
	@Value("${pdf_sxssqh}")
	private static String sxssqh;
	
	public static void main(String[] args) {
    /*	String a = Md5.tokenValue("YMnimcOUHi", "1585290712874", "1585290712874100111111", "OsxEPp2AP49a4skaTCqNSpI1MN7RA5hq");
    	System.out.println(a);*/
		Map<String,Object> map=new  HashMap<String,Object>();
		map.put("malenumber", "612523199210042718");
		map.put("malename", "刘想");
		map.put("femalenumber", "610326199201211440");
		map.put("femalename", "任甜甜");
    /*	String sxssqh="610100000000"; 
    	String gjptjbbm="FW12051000"; 
    	
    	SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
		int random=(int) ((Math.random()*9+1)*10000);
		String date1=sdf1.format(new Date());
		String xh=sxssqh.substring(0,6);
		String projid="61"+gjptjbbm+xh+date1+random+"4"; 
    	System.out.println(projid);*/
    	


	}
	public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }
	
	  /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
    /**
     * tokenValue值的生成
     */
    public static String tokenValue(String app_id,String timestamp,String trans_id,String app_secret){
    	String tokenValue = "";
    	StringBuffer bf = new StringBuffer();
    	bf.append("APP_ID"+app_id);
    	bf.append("TIMESTAMP"+timestamp);
    	bf.append("TRANS_ID"+trans_id);
    	bf.append(app_secret);
    	String s = bf.toString();
    	tokenValue = string2MD5(s);
    	return tokenValue;
    }
   /* // 测试主函数  
    public static void main(String args[]) throws IOException {  
    	String a = MD5Util.tokenValue("gu2CnMqzNg", "1544973747", "20181216232227823728802", "kJShM1e41AZlPeaw8ku8pgQIQQgoMn9h");
    	System.out.println(a);
    }  */

}
