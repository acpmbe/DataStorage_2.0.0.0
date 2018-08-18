
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import bll.fun.InfoStatis_Bll;
import util.*;

public class Main
{

	public static void main(String[] args)
	{

		try
		{

			String Result = Init();
			if (!Result.equals("0"))
			{

				Log.Warn("初始化出错：" + Result);
				return;

			}

			Log.Info("初始化成功");

			// String content
			// ="DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";
			// Data_Bll c =new Data_Bll();
			// c.SetData(content);
			// Redis_Test_New ssd =new Redis_Test_New("");
			// ssd.Insert();

			Thread t1 = new Thread(new bll.thread.MainZ_New_13());
			t1.start();

			if (Config.IsSyncTime())
			{
				Thread t3 = new Thread(new bll.thread.LastId_Sync());
				t3.start();
			}

			if (Config.IsPrintTime())
			{
				Thread t5 = new Thread(new bll.thread.InfoStatis_Print());
				t5.start();
			}

		}
		catch (Exception ex)
		{

			Log.Error("外层运行错误：" + ErrInfo(ex));

		}

	}

	private static String Init()
	{

		try
		{
			String Result;

			// String urlString = System.getProperty("user.dir");
			String urlString = ClassLoader.getSystemResource("").getPath();
			urlString = URLDecoder.decode(urlString, "UTF-8");

			// System.out.println("目录是：" + urlString);

			Result = util.Config.Init(urlString);
			if (!Result.equals("0"))
			{
				return Result;
			}

			Result = Log.Init(urlString);
			if (!Result.equals("0"))
			{
				return Result;
			}

			if (Config.IsSyncTime())
			{
				Result = bll.fun.LastIdInfo_Bll.Init();
				if (!Result.equals("0"))
				{
					return Result;
				}

			}

			if (Config.IsPrintTime())
			{
				InfoStatis_Bll.Init();
			}

			return "0";

		}
		catch (Exception e)
		{
			return e.getMessage();
		}

	}

	public static String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

}
