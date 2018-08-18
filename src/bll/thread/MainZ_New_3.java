package bll.thread;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import bll.fun.InfoStatis_Bll;
import dal.thread.DataRedis;
import mod.EInfoStatis;
import util.Config;
import util.Log;

public class MainZ_New_3 implements Runnable
{

	private final String NAME = "读取Redis数据";
	private List<String> List = new ArrayList<String>();
	private List<String> List_New = new ArrayList<String>();

	private String[] ListData;

	@Override
	public void run()
	{

		util.Log.Info("开始取Redis数据");

//		 ThreadPoolExecutor Tpe = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
//		 TimeUnit.SECONDS,
//		 new SynchronousQueue<Runnable>());

//		ThreadPoolExecutor Tpe = new ThreadPoolExecutor
//				(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		
		 ThreadPoolExecutor Tpe = new ThreadPoolExecutor
				 (20, 23, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

		while (true)
		{
			try
			{
//				System.out.println("\n\n");
//				System.out.println("核心线程数" + Tpe.getCorePoolSize());
//				System.out.println("线程池数" + Tpe.getPoolSize());
//				System.out.println("队列任务数" + Tpe.getQueue().size());
				
				List = DataRedis.GetList(Config.RedisListName(), Config.DataSize());

				if (List.size() != 0)
				{

					for (String strA : List)
					{
						ListData = strA.split(",");
						for (String strC : ListData)
						{
							List_New.add(strC);
						}

					}

					InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List_New.size()));

					Tpe.execute(new HandleData(List_New));
					// List.clear();
					List_New.clear();
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
