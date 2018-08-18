package bll.thread;

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

public class MainZ_New_11 implements Runnable
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
		NotDataNum = 0;

		ThreadPoolExecutor Tpe = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>());

		while (true)
		{
			try
			{

				if (Tpe.getQueue().size() > 2)
				{

					//System.out.println("队列超过：2  目前队列：" + Tpe.getQueue().size());
					Thread.sleep(1000);
					//System.gc();
					continue;
				}
				else
				{
					content = DataRedis.GetValue(RedisListName);

				}

				// System.out.println("队列任务数" + Tpe.getQueue().size());

				if (content == null)
				{
					util.Log.Info("Redis已经无数据（暂停" + Config.RedisNoDataTime() + "秒）");
					Thread.sleep(Config.RedisNoDataTime() * 1000);
					//System.gc();

					if (List.size() > 0)
					{
						NotDataNum++;
						if (NotDataNum >= NotDataNum_C)
						{
							InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
							Tpe.execute(new HandleData(List));
							List.clear();
							NotDataNum = 0;
						}
					}

					continue;
				}

				if (content.equals(""))
				{
					continue;
				}

				List_Temp = Arrays.asList(content.split(","));
				List.addAll(List_Temp);

				int List_Size = List.size();

				if (List_Size > DataSize)
				{

					YNum = List_Size % DataSize;
					DNum = List_Size / DataSize;

					for (int i = 0; i < DNum; i++)
					{

						List_Pack = List.subList(i * DataSize, DataSize + (i * DataSize));
						InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List_Pack.size()));
						Tpe.execute(new HandleData(List_Pack));

					}

					if (YNum != 0)
					{

						List_Pack = List.subList(DNum * DataSize, (DNum * DataSize) + YNum);
						InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List_Pack.size()));
						Tpe.execute(new HandleData(List_Pack));

					}

					List.clear();

				}
				else if (List_Size == DataSize)
				{

					InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
					Tpe.execute(new HandleData(List));
					List.clear();

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
