package bll.thread;

import java.io.PrintWriter;
import java.io.StringWriter;

import mod.CmdS.ELastInfo;
import util.Config;
import util.Log;

public class LastId_Sync implements Runnable
{

	private final String NAME = "同步Mysql_LastId";

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(Config.SyncTime() * 1000);
				Long Result = bll.fun.LastIdInfo_Bll.getValue(ELastInfo.Sync, "");
				if (Result == -1)
				{
					Log.Error(NAME + "_运行出错");

				}

			}
			catch (Exception ex)
			{

				Log.Error(NAME + "_运行错误：" + ErrInfo(ex));
			}

		}

	}

	private String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

}
