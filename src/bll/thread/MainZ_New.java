package bll.thread;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import bll.fun.InfoStatis_Bll;
import dal.thread.DataRedis;
import mod.EInfoStatis;
import util.Config;
import util.Log;

public class MainZ_New implements Runnable
{

	private final String NAME = "读取Redis数据";
	private List<String> List = new ArrayList<String>();

	@Override
	public void run()
	{

		util.Log.Info("开始取Redis数据");

		ThreadPoolExecutor Tpe = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());

		while (true)
		{
			try
			{
				List = DataRedis.GetList("Redis_Test", Config.DataSize());

				if (List.size() != 0)
				{
					InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
					Tpe.execute(new HandleData(List));
					List.clear();
				}
				else
				{
					util.Log.Info("Redis已经无数据（暂停" + Config.RedisNoDataTime() + "秒）");
					Thread.sleep(Config.RedisNoDataTime() * 1000);
				}

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
