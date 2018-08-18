package bll.thread;

import mod.EInfoStatis;
import util.Config;

public class InfoStatis_Print implements Runnable
{

	@Override
	public void run()
	{

		try
		{
			while (true)
			{

				Thread.sleep(Config.PrintTime() * 1000);
				bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Show, "", 1L);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
