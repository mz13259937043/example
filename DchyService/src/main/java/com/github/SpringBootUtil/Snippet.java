package com.github.SpringBootUtil;

public  class Snippet {
	public static String delSpace(String str)  {
	
			if (str == null) {
				return null;
			}
	
			String regStartSpace = "^[　 ]*";
			String regEndSpace = "[　 ]*$";
	
			// 连续两个 replaceAll
			// 第一个是去掉前端的空格， 第二个是去掉后端的空格
			String strDelSpace = str.replaceAll(regStartSpace, "").replaceAll(regEndSpace, "");
	
			return strDelSpace;
		}
}

