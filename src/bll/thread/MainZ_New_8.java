package bll.thread;

import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import bll.fun.InfoStatis_Bll;
import dal.thread.DataRedis;
import mod.EInfoStatis;
import util.Config;
import util.Log;

public class MainZ_New_8 implements Runnable
{

	private final String NAME = "读取Redis数据";
	private List<String> List = new ArrayList<String>();
	private List<String> List_Temp = new ArrayList<String>();
	private List<String> List_Pack = new ArrayList<String>();
	private final String RedisListName = Config.RedisListName();
	private final int DataSize = Config.DataSize();
	private String content;

	private int YNum;
	private int DNum;

	private int NotDataNum;
	private int NotDataNum_C = Config.NotDataNum();

	@Override
	public void run()
	{

		util.Log.Info("开始取Redis数据");
		
		StringBuilder dd  =new  StringBuilder();
		
		

		while (true)
		{
			try
			{

				content = DataRedis.GetValue(RedisListName);

				// System.out.println("队列任务数" + Tpe.getQueue().size());

				if (content == null)
				{
					util.Log.Info("Redis已经无数据（暂停" + Config.RedisNoDataTime() + "秒）");
					Thread.sleep(Config.RedisNoDataTime() * 1000);

					continue;
				}

				if (content.equals(""))
				{
					continue;
				}

				List_Temp = Arrays.asList(content.split(","));

				content =null;
				new HandleData3().run(List_Temp);

			}
			catch (Exception e)
			{

				Log.Error(NAME + "_错误_暂停3秒" + ErrInfo(e));
				try
				{
					Thread.sleep(3 * 1000);
				}
				catch (Exception e2)
				{

				}

			}

		}
	}

	public String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

}
