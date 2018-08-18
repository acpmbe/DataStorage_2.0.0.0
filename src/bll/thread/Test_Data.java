package bll.thread;

import mod.EInfoStatis;
import util.Config;

public class Test_Data implements Runnable
{

	@Override
	public void run()
	{

		try
		{
			while (true)
			{

				Thread.sleep(10);
				bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, "命令_0007",1L);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
