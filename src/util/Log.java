package util;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;

public class Log
{

	private static Logger Loginfo;

	public static String Init(String path)
	{

		// 读取使用Java的特性文件编写的配置文件
		// PropertyConfigurator.configure(path);

		try
		{
			
	
			DOMConfigurator.configure(path+"/log4j.xml");
			Loginfo = Logger.getLogger(Log.class);

			return "0";
		}
		catch (Exception e)
		{

			return e.getMessage();
		}

	}

	public static void Info(String content)
	{
		if (Loginfo.isInfoEnabled())
		{
			Loginfo.info(content);
		}
	}

	public static void Debug(String content)
	{
		if (Loginfo.isDebugEnabled())
		{
			Loginfo.debug(content);
		}
	}

	public static void Warn(String content)
	{

		Loginfo.warn(content);
	}

	public static void Error(String content)
	{
		Loginfo.error(content);
	}
}
