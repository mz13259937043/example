package com.github.DchyService.tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.DchyService.dao.DchyFbxxDao;
import com.github.DchyService.dao.DchyProcessDao;
import com.github.DchyService.entity.DchyProcess;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description 自建的转换工具类
* @Author maoZhan
* @Date 2020/8/28 14:40
* @param  * @param null
* @return
**/
@Component
public class ConvertyUtil {

	@Autowired
	private DchyFbxxDao dchyFbxxDao;
	@Autowired
	private DchyProcessDao dchyProcessDao;
	public static void main(String[] args) throws ParseException {
		/*String[]a= new String [3];
		a[0] = "1";
		System.out.println(Arrays.toString(a));
		System.out.println(a.length);*/
		/*String TimeStart = "2020-09-13T16:00:00.000Z".replace("Z", " UTC");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		Date da = format.parse(TimeStart);
		System.out.println(da);*/
	}
	/**
	* @Description //TODO字符串转时间
	* @Author maoZhan
	* @Date 2020/9/17 16:12
	* @param  * @param null
	* @return
	**/
	public static Date string2Date(String str) {
		String TimeStart = str.replace("Z", " UTC");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		Date date = new Date();
		try {
			date = format.parse(TimeStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	* @Description //生成日期型编号
	* @Author maoZhan
	* @Date 2020/11/14 17:35
	* @param  * @param
	* @return void
	**/
	public String createCode(String processId) {
		StringBuffer sb = new StringBuffer();
		DchyProcess process = dchyProcessDao.getById(processId);
		if (StringUtil.isEmpty(process.getPid())){
//			大阶段？
			sb.append(process.getSerialCode());
		}else{
			String code1 = process.getSerialCode();			//小的
			process  = dchyProcessDao.getById(process.getPid());
			sb.append(process.getSerialCode());
			sb.append(code1);
		}
		//		前缀		项目类型+项目阶段
		/*if (chjd.length>0&&chjd.length==1){
			process  = dchyProcessDao.getByAlias(chjd[0]);
			sb.append(process.getSerialCode());
		}else if (chjd.length>1){
			process  = dchyProcessDao.getByAlias(chjd[0]);
			sb.append(process.getSerialCode());
			process  = dchyProcessDao.getBy1and2(chjd[0],chjd[1]);
			sb.append(process.getSerialCode());
		}else{
			return "";
		}*/
//		年月
		LocalDateTime ldt =  LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");
		sb.append(ldt.format(df));
// 		序列号
		String seqCode = dchyFbxxDao.getSequence(process.getProcessSeqname());
//		补零
		switch (seqCode.length()) {//000一、00二0、0三00、四000、五.0000
			case 0:
				break;
			case 1:
				sb.append("000"+seqCode);
				break;
			case 2:
				sb.append("00"+seqCode);
				break;
			case 3:
				sb.append("0"+seqCode);
				break;
			case 4:
				sb.append(seqCode);
				break;
		}
		return sb.toString();
	}
	public String createCode(String []chjd) {
		StringBuffer sb = new StringBuffer();
		DchyProcess process = null;
		//		前缀		项目类型+项目阶段
		if (chjd.length>0&&chjd.length==1){
			process  = dchyProcessDao.getByAlias(chjd[0]);
			sb.append(process.getSerialCode());
		}else if (chjd.length>1){
			process  = dchyProcessDao.getByAlias(chjd[0]);
			sb.append(process.getSerialCode());
			process  = dchyProcessDao.getBy1and2(chjd[0],chjd[1]);
			sb.append(process.getSerialCode());
		}else{
			return "";
		}
//		年月
		LocalDateTime ldt =  LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");
		sb.append(ldt.format(df));
// 		序列号
		String seqCode = dchyFbxxDao.getSequence(process.getProcessSeqname());
//		补零
		switch (seqCode.length()) {//000一、00二0、0三00、四000、五.0000
			case 0:
				break;
			case 1:
				sb.append("000"+seqCode);
				break;
			case 2:
				sb.append("00"+seqCode);
				break;
			case 3:
				sb.append("0"+seqCode);
				break;
			case 4:
				sb.append(seqCode);
				break;
		}
		return sb.toString();
	}
	/**
	 * 对象转map,进行过了去空,time类型的设置
	 * @param jobj
	 * @return
	 */
	public static Map<String, Object> jobjToMap1(Map<String, Object> jobj) {
//		准备日期转换
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		LocalDateTime ldt;
		Instant instant;
		Timestamp ts;
		
		for (String key : jobj.keySet()) {
			if (jobj.get(key) == null || jobj.get(key).toString().equals("")) {
				continue;
			} else if ((key.endsWith("ime") || key.endsWith("ate")) && jobj.get(key) != null) {
//				进行日期转换
				ldt = LocalDateTime.parse(jobj.get(key).toString(), df);
				instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
				ts = Timestamp.from(instant);
				map.put(key, ts);
				continue;
			} else {
				map.put(key, jobj.get(key));
				continue;
			}
		}
		return map;
	}

	/**
	 * 实体对象转成Map
	 * 
	 * @param obj 实体对象
	 * @return
	 */
	public static Map<String, Object> entity2Map(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	* @Description 实体对象转map
	* @Author maoZhan
	* @Date 2020/10/15 18:52
	* @param  * @param object
	* @return java.util.Map<java.lang.String,java.lang.Object>
	**/
	public static Map<String,Object> object2Map(Object object){
		Map<String,Object> result=new HashMap<>();
		//获得类的的属性名 数组
		Field[]fields=object.getClass().getDeclaredFields();
		try {


			for (Field field : fields) {
				field.setAccessible(true);
				String name = new String(field.getName());
				result.put(name, field.get(object));

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Map转成Object
	 *
	 * @param map   实体对象包含属性
	 * @param clz 实体对象类型
	 * @return
	 */
	public static <T> T map2Entity(Map<String, Object> map, Class<T> clz) {
		if (map == null) {
			return null;
		}
		T t = null;
		try {
			t = clz.newInstance();
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				field.set(t, map.get(field.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * map转成实体对象
	 *
	 * @param map   实体对象包含属性
	 * @param clz 实体对象类型
	 * @return
	 */
	public static <T> T map2Entity2(Map<String, Object> map, Class<T> clz) {
		T t = JSON.parseObject(JSON.toJSONString(map), clz);
		return t;
	}

	/**
	 * 构建对象,去空 方法名：removeNull 创建人：14874 时间：2020年8月14日-下午2:59:02
	 * 
	 * @param map void
	 * @exception @since 1.0.0
	 */
	public static Map<String, Object> removeNull(Map<String, Object> map) {
		Iterator<String> iterator = map.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			if (map.get(key) == null) {
				iterator.remove();
			} else if (key.endsWith("ate") || key.endsWith("ime")) {

			}
		}
		return map;
	}

	public static <T> T obj2Entity(Object obj,Class<T> clz){
		ObjectMapper objectMapper = new ObjectMapper();
		T t = objectMapper.convertValue(obj, clz);
		return t;
	}
	public static boolean hasEmpty(Object object) throws Exception {
		Class<?> aClass = object.getClass();
		Field[] declaredFields = aClass.getDeclaredFields();
		for (Field field : declaredFields){
			String name = field.getName();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			String type = field.getGenericType().toString();
			Method method = aClass.getMethod("get" + name);
			if (type.equals("class java.lang.String")) {
				String value = (String) method.invoke(object);
				if (value==null) {
					return true;
				}
			}else if(type.equals("class java.lang.Integer")){
				Integer value = (Integer) method.invoke(object);
				if (value==null) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	* @Description 判断所有属性是否全为空
	* @Author maoZhan
	* @Date 2020/9/15 9:21
	* @param  * @param null
	* @return
	**/
	public static boolean checkNull(Object obj) throws IllegalAccessException {
		boolean isNull = false;
		Field[] fs = obj.getClass().getDeclaredFields();
		for (Field f:fs) {
			f.setAccessible(true);
			if ("".equals(f.get(obj))||f.get(obj)==null){
//				属性值为空则继续
				isNull = true;
			}else{
//				任一属性值非空则直接返回
				isNull = false;
				return isNull;
			}
		}
//		任一非空符合条件为为false，
		return isNull;
	}
//	手动分页
	public static List<Map<String,Object>> fenye (List<Map<String,Object>> list,int currPage,int pageSize){
		int start = (currPage-1)*pageSize;          //起始索引
		int end = list.size()-start<pageSize?list.size():start+pageSize;        //结束索引
		return list.subList(start,end);
	}
	/**
	* @Description //计算文件大小
	* @Author maoZhan
	* @Date 2020/11/18 11:54
	* @param  * @param
	* @return java.lang.String
	**/
	public static String calFileSize(File f) {
		String size = "";
		Double ava = (double)f.length();
		DecimalFormat df = new DecimalFormat("#.##");
		if((ava/1024)>1000&&(ava/1024)<1000000){
			size = df.format(ava/1024/1024)+"MB";
//            size =df.format((double)((double)is.available()/1024/1024))+"MB";
		}else if((ava/1024)>1000000){
			size = df.format(ava/1024/1024/1024)+"Gb";
//            size =df.format((double)((double)is.available()/1024))+"KB";
		}else{
			size = df.format(ava/1024)+"KB";
		}
		return size;
	}
}
