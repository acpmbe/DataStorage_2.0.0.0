package util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ComUtil
{

	
	/**
	 * 得到日期（java.util.Date）
	 * @return
	 */
	public static java.util.Date GetDate()
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return ca.getTime();
	}
	
	
	
	
	
	/**
	 * 得到日期（java.sql.Timestamp）
	 * @return
	 */
	public static  java.sql.Timestamp GetData()
	
	{
		return new Timestamp(System.currentTimeMillis());
	}
	
	
	//java.sql.Timestamp sddddddss=  new Timestamp(System.currentTimeMillis());
	
	
}
