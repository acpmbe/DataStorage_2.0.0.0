package bll.thread;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import bll.CmdS.ControlReply_Bll;
import bll.CmdS.ControlResultData_Bll;
import bll.CmdS.Control_Bll;
import bll.CmdS.Data_Bll;
import bll.CmdS.DevWayInOutNotify_Bll;
import bll.CmdS.ICmd;
import util.Log;

public class HandleData_New implements Runnable
{

	private List<String> List = new ArrayList<String>();

	public HandleData_New(List<String> list)
	{

		List.addAll(list);

	}

	@Override
	public void run()
	{
		long startTime = System.currentTimeMillis();

		Integer DevCmdInt = null;

		ICmd Data_Cmd = null;
		ICmd Control_Cmd = null;
		ICmd ControlReply_Cmd = null;
		ICmd ControlResultData_Cmd = null;
		ICmd DevWayInOutNotify_Cmd = null;
		for (String content : List)
		{
			try
			{
				if (content.length() <= (25 * 2))
				{
					continue;
				}

				String DevCmdStr= content.substring(28, 32);
				DevCmdInt = Integer.parseInt(DevCmdStr, 16);
				DevCmdStr=null;
				switch (DevCmdInt)
				{

				case 7: // 0007_数据

					if (Data_Cmd == null)
					{
						Data_Cmd = new Data_Bll();
					}
					Data_Cmd.SetData(content);
					break;
				case 31: // 001F_控制
					if (Control_Cmd == null)
					{
						Control_Cmd = new Control_Bll();
					}
					Control_Cmd.SetData(content);
					break;
				case 32: // 0020_控制应答
					if (ControlReply_Cmd == null)
					{
						ControlReply_Cmd = new ControlReply_Bll();
					}
					ControlReply_Cmd.SetData(content);
					break;
				case 43: // 002B_控制结果数据
					if (ControlResultData_Cmd == null)
					{
						ControlResultData_Cmd = new ControlResultData_Bll();
					}
					ControlResultData_Cmd.SetData(content);
					break;
				case 38: // 0026_设备通道上下线通知

					if (DevWayInOutNotify_Cmd == null)
					{
						DevWayInOutNotify_Cmd = new DevWayInOutNotify_Bll();
					}
					DevWayInOutNotify_Cmd.SetData(content);
					break;
				default:
					break;
				}

				content = null;
			}
			catch (Exception e)
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String sd = sw.toString();
				String info = "工厂命令_遍历错误" + sd;
				Log.Error(info);
				sw = null;
				pw = null;
				sd = null;
				info = null;

			}

		}

		if (Data_Cmd != null)
		{
			Data_Cmd.Execute();
			Data_Cmd = null;
		}

		if (Control_Cmd != null)
		{
			Control_Cmd.Execute();
			Control_Cmd = null;
		}

		if (ControlReply_Cmd != null)
		{
			ControlReply_Cmd.Execute();
			ControlReply_Cmd = null;
		}

		if (ControlResultData_Cmd != null)
		{
			ControlResultData_Cmd.Execute();
			ControlResultData_Cmd = null;
		}

		if (DevWayInOutNotify_Cmd != null)
		{
			DevWayInOutNotify_Cmd.Execute();
			DevWayInOutNotify_Cmd = null;
		}

		long endTime = System.currentTimeMillis();

		Log.Info("执行Redis数据" + List.size() + "条" + "  时间： " + (endTime - startTime) + "ms");

		startTime = 0;
		endTime = 0;
		DevCmdInt = null;
		//
		List = null;

	}

	private String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

}
