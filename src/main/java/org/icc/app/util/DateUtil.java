package org.icc.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间处理函数
 * 
 */
public class DateUtil {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final String TIME_YEAR = "yyyy";
	
	public static final String TIME_MONEN = "MM";
	
	public static final String TIME_DAY = "dd";

	public static String getDate(String interval, Date starttime, String pattern) {
		Calendar temp = Calendar.getInstance(TimeZone.getDefault());
		temp.setTime(starttime);
		temp.add(Calendar.MONTH, Integer.parseInt(interval));
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(temp.getTime());
	}
	
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 将字符串类型转换为时间类型
	 * 
	 * @return
	 */
	public static Date str2Date(String str) {
		return str2Date(str, DEFAULT_PATTERN);
	}

	public static Date str2Date(String str, String pattern) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			d = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 将时间格式化
	 * 
	 * @return
	 */
	public static Date datePattern(Date date) {
		return datePattern(date, DEFAULT_PATTERN);
	}

	/**
	 * 将时间格式化
	 */
	public static Date datePattern(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			String dd = sdf.format(date);
			date = str2Date(dd, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String date2Str(Date date) {
		return date2Str(date, DEFAULT_PATTERN);
	}

	public static String date2Str(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取昨天
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDate(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -1);

		return str2Date(date2Str(calendar.getTime()));
	}
	/**
	 * 获取前几天
	 * @param date
	 * @return
	 */
	public static Date getBeforeDate(Date date,int dates) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -dates);

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 获取上周第一天（周一）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastWeekStart(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int startnum = 0;
		if (i == 0) {
			startnum = 7 + 6;
		} else {
			startnum = 7 + i - 1;
		}
		calendar.add(Calendar.DATE, -startnum);

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 获取上周最后一天（周末）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int endnum = 0;
		if (i == 0) {
			endnum = 7;
		} else {
			endnum = i;
		}
		calendar.add(Calendar.DATE, -(endnum - 1));

		return str2Date(date2Str(calendar.getTime()));
	}
	
	/**
	 * 根据年和月得到天数
	 * @param num 月
	 * @param year 年
	 * @return
	 */
	public static int getDay(int num, int year){
		if(num==1 || num==3 || num==5 || num==7 || num==8 || num==10 || num==12){
			return 31;
		}else if(num==2){
			//判断是否为闰年
			if(year%400==0 || (year%4==0 && year%100!=0)){
				return 29;
			}else{
				return 28;
			}
			
		}else{
			return 30;
		}
	}
	/*
	 * 计算当前日期距离下个月还有多少天
	 */
	public static int getDayMis(Date time){
		int year = Integer.parseInt(
				new SimpleDateFormat(TIME_YEAR).format(time));//年
		
		int mm = Integer.parseInt(
				new SimpleDateFormat(TIME_MONEN).format(time));//月
		
		int dd = Integer.parseInt(
				new SimpleDateFormat(TIME_DAY).format(time));//日
		
		
		//获取当前年月的总天数
		int sdd = getDay(mm,year);
		
		return sdd-dd;
		
		
	}
	/**
	 * 日期转秒数
	 * @param dateString
	 * @return
	 */
	public static long getTime(String dateString) {
	    long time = 0;
	    try {
		    Date ret = null;
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    ret = sdf.parse(dateString);
		    time = ret.getTime()/1000;
	    } catch (Exception e) {
		
	    }
	    return time;
    }
	
	
	/**
	 * 精确计算时间差，精确到日
	 * @param fistill 起始日期
	 * @param nowtime 结束日期
	 * @param type type为1返回年月日（如：2年3个月零5天） 否则返回总的天数
	 * @return
	 */
	public static String patienage(Date fistill,Date nowtime,Integer type){
		
		int fyear = Integer.parseInt(
				new SimpleDateFormat(TIME_YEAR).format(fistill));//起始年
		
		int fmm = Integer.parseInt(
				new SimpleDateFormat(TIME_MONEN).format(fistill));//起始月
		
		int fdd = Integer.parseInt(
				new SimpleDateFormat(TIME_DAY).format(fistill));//起始日
		
		
		int nyear = Integer.parseInt(
				new SimpleDateFormat(TIME_YEAR).format(nowtime));//结束年
		
		int nmm = Integer.parseInt(
				new SimpleDateFormat(TIME_MONEN).format(nowtime));//结束月
		
		int ndd = Integer.parseInt(
				new SimpleDateFormat(TIME_DAY).format(nowtime));//结束日
		
		int cyear = nyear - fyear;
		int cmm = nmm - fmm;
		int cdd = ndd - fdd;
		
		
		int zyear = 0;
		int zmm = 0;
		int zdd = 0;
		
		int countddd = 0;  //年月日累计天数
		
		if(cdd<0){
			if(cmm<0){
				zyear = cyear - 1;
				zmm = (cmm + 12)-1; 
				int dd = getDay(zmm,nyear-1);
				zdd = dd + cdd;
				
				
				countddd = zyear*365+zmm*30+zdd;
				
			}else if(cmm==0){
				zyear = cyear - 1;
				zmm = 12-1; 
				int dd = getDay(zmm,nyear-1);
				zdd = dd + cdd;
				
				countddd = zyear*365+zmm*30+zdd;
				
			}else{
				zyear = cyear;
				zmm = cmm - 1; 
				int dd = getDay(zmm,nyear);
				zdd = dd + cdd;
				
				countddd = zyear*365+zmm*30+zdd;
				
			}
		}else if(cdd==0){
			if(cmm<0){
				zyear = cyear - 1;
				zmm = cmm + 12; 
				zdd = 0;
				
				countddd = zyear*365+zmm*30;
				
			}else if(cmm==0){
				zyear = cyear;
				zmm = 0; 
				zdd = 0;
				
				countddd = zyear*365+zmm*30;
				
			}else{
				zyear = cyear;
				zmm = cmm; 
				zdd = 0;
				
				countddd = zyear*365+zmm*30;
			}
		}else{
			if(cmm<0){
				zyear = cyear - 1;
				zmm = cmm + 12; 
				zdd = cdd;
				
				countddd = zyear*365+zmm*30+zdd;
			}else if(cmm==0){
				zyear = cyear;
				zmm = 0; 
				zdd = cdd;
				
				countddd = zyear*365+zmm*30+zdd;
			}else{
				zyear = cyear;
				zmm = cmm; 
				zdd = cdd;
				
				countddd = zyear*365+zmm*30+zdd;
			}
		}
		String ptime = null;
		
		if(zdd!=0){
			if(zmm!=0){
				if(zyear!=0){
					ptime = zyear+"年"+zmm+"个月"+"零"+zdd+"天";
				}else{
					ptime = zmm+"个月"+"零"+zdd+"天";
				}
			}else{
				if(zyear!=0){
					ptime = zyear+"年"+"零"+zdd+"天";
				}else{
					ptime = zdd+"天";
				}
			}
		}else{
			if(zmm!=0){
				if(zyear!=0){
					ptime = zyear+"年"+zmm+"个月";
				}else{
					ptime = zmm+"个月";
				}
			}else{
				if(zyear!=0){
					ptime = zyear+"年";
				}else{
					ptime = null;
				}
			}
		}
		if(type==1){
			return ptime;   //返回年月日（如：2年3个月零5天）
		}else{
			return String.valueOf(countddd);  //返回总天数
		}
		
		
	}
	/**
	 * 得到月数
	 * @param year 年数差
	 * @param month 月数差
	 * @return
	 */
	public static int getCmm(Integer year,Integer month){
		int zmm = 0;
		if(month < 0){
			zmm = (month + 12)+(year-1)*12;
		}else if(month == 0){
			zmm = year*12;
		}else{
			zmm = year*12+month;
		}
		return zmm;
	}
	
	

	/**
	 * 改更现在时间
	 */
	public static Date changeDate(String type, int value) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		if (type.equals("month")) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);
		} else if (type.equals("date")) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
		}
		return calendar.getTime();
	}

	/**
	 * 更改时间
	 */
	public static Date changeDate(Date date, String type, int value) {
		if (date != null) {
			// Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			// Calendar calendar = Calendar.
			if (type.equals("month")) {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);
			} else if (type.equals("date")) {
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
			} else if (type.endsWith("year")) {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + value);
			}
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * haoxw 比较时间是否在这两个时间点之间
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean checkTime(String time1, String time2) {
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		Date date11 = DateUtil.str2Date(DateUtil.date2Str(date1, "yyyy-MM-dd") + " " + time1);// 起始时间

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Date date2 = c.getTime();
		Date date22 = DateUtil.str2Date(DateUtil.date2Str(date2, "yyyy-MM-dd") + " " + time2);// 终止时间

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * haoxw 比较时间是否在这两个时间点之间
	 * 
	 * @param date11
	 * @param date22
	 * @return
	 */
	public static boolean checkTime(Date date11, Date date22) {
		
		

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}

	}

	
	public static boolean checkDate(String date1, String date2) {

		Date date11 = DateUtil.str2Date(date1, "yyyy-MM-dd HH:mm:ss");// 起始时间

		Date date22 = DateUtil.str2Date(date2, "yyyy-MM-dd HH:mm:ss");// 终止时间

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		System.out.println(date11.toString());
		System.out.println(date22.toString());
		System.out.println(scalendar.toString());
		System.out.println(ecalendar.toString());
		System.out.println(calendarnow.toString());

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取interval天之前的日期
	 * 
	 * @param interval
	 * @param starttime
	 * @param pattern
	 * @return
	 */
	public static Date getIntervalDate(String interval, Date starttime, String pattern) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(starttime);
		temp.add(Calendar.DATE, Integer.parseInt(interval));
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String shijian = sdf.format(temp.getTime());
		return str2Date(shijian);
	}
    
	public static Date formatDate(Date date){
		Date date1 = null;
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bartDateFormat1 =new SimpleDateFormat("yyyy-MM-dd"); 			
		try {
			date1 = bartDateFormat1.parse(bartDateFormat.format(date));
		} catch (ParseException e) {				
			e.printStackTrace();
		} 
		
		return date1; 

	}
}