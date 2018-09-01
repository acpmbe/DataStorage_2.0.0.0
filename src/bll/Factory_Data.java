package bll;

import bll.CmdS.ControlReply_Bll;
import bll.CmdS.ControlResultData_Bll;
import bll.CmdS.Control_Bll;
import bll.CmdS.Data_Bll;
import bll.CmdS.DevWayInOutNotify_Bll;
import bll.CmdS.ICmd;
import util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Factory_Data
{

	private List<String> List = new ArrayList<String>();

	public Factory_Data(List<String> list)
	{

		this.List = list;

	}

	public void Execute()
	{

		Integer DevCmdInt;

		ICmd Data_Cmd = new Data_Bll();
		ICmd Control_Cmd = new Control_Bll();
		ICmd ControlReply_Cmd = new ControlReply_Bll();
		ICmd ControlResultData_Cmd = new ControlResultData_Bll();
		ICmd DevWayInOutNotify_Cmd = new DevWayInOutNotify_Bll();
		for (String content : List)
		{
			try
			{
				if (content.length() <= (25 * 2))
				{
					continue;
				}

				DevCmdInt = Integer.parseInt(content.substring(28, 32), 16);

				switch (DevCmdInt)
				{

				case 7: // 0007_数据
					Data_Cmd.SetData(content);
					break;
				case 31: // 001F_控制
					Control_Cmd.SetData(content);
					break;
				case 32: // 0020_控制应答
					ControlReply_Cmd.SetData(content);
					break;
				case 43: // 002B_控制结果数据
					ControlResultData_Cmd.SetData(content);
					break;
				case 38: // 0026_设备通道上下线通知
					DevWayInOutNotify_Cmd.SetData(content);
					break;
				default:
					break;
				}
			}
			catch (Exception e)
			{

				String info = "工厂命令_遍历错误" + ErrInfo(e);

				Log.Error(info);

				info = null;

			}

		}

		Data_Cmd.Execute();
		Control_Cmd.Execute();
		ControlReply_Cmd.Execute();
		ControlResultData_Cmd.Execute();
		DevWayInOutNotify_Cmd.Execute();

	}

	private String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

}
