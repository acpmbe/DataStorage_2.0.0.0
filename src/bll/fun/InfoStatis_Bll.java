package bll.fun;


import java.util.Map;
import java.util.TreeMap;
import bll.CmdS.ControlReply_Bll;
import bll.CmdS.ControlResultData_Bll;
import bll.CmdS.Control_Bll;
import bll.CmdS.Data_Bll;
import bll.CmdS.DevWayInOutNotify_Bll;
import mod.EInfoStatis;

public class InfoStatis_Bll
{

	private final static String NAME = "命令信息统计";

	private static Map<String, Long> List = new TreeMap<String, Long>();

	private static boolean IsFirst = true;
	
	private static int BagNum = -1;

	public static void Init()
	{
		List.put("读取Redis数据", 0L);
	
		Data_Bll data_Bll =new Data_Bll();
		List.put(data_Bll.NAME, 0L);
		List.put(data_Bll.ErrNAME, 0L);
		data_Bll=null;
		
		Control_Bll control_Bll =new Control_Bll();
		List.put(control_Bll.NAME, 0L);
		List.put(control_Bll.ErrNAME, 0L);
		control_Bll=null;
	
		
		ControlReply_Bll controlReply_Bll =new  ControlReply_Bll();
		List.put(controlReply_Bll.NAME, 0L);
		List.put(controlReply_Bll.ErrNAME, 0L);
		controlReply_Bll=null;
	
		ControlResultData_Bll controlResultData_Bll =new  ControlResultData_Bll();
		List.put(controlResultData_Bll.NAME, 0L);
		List.put(controlResultData_Bll.ErrNAME, 0L);
		controlResultData_Bll=null;

		DevWayInOutNotify_Bll devWayInOutNotify_Bll =new  DevWayInOutNotify_Bll();
		List.put(devWayInOutNotify_Bll.NAME, 0L);
		List.put(devWayInOutNotify_Bll.ErrNAME, 0L);
		devWayInOutNotify_Bll=null;

		List.put("写入MongoDb数据", 0L);

		if (IsFirst)
		{
			List = ((TreeMap) List).descendingMap();
			IsFirst = false;
		}

	}

	public synchronized static void Handle(EInfoStatis type, String cmdName, Long addNum)
	{

		switch (type)
		{
		case Add:
			if (List.containsKey(cmdName))
			{
				List.put(cmdName, List.get(cmdName) + addNum);
			}
			else
			{
				List.put(cmdName, addNum);
			}
			break;
		case Show:
			Print();
			break;
		default:
			break;
		}

	}



	private static void Print()
	{

		if (List.size() == 0)
		{
			util.Log.Info("各命令数据为空");
			return;
		}

		BagNum++;

		String TempStr = "";
		TempStr = "\n\n" + "当前第" + BagNum + "包" + "\n";

		for (Map.Entry<String, Long> entry : List.entrySet())
		{

			TempStr += entry.getKey() + "   " + entry.getValue() + "\n";

		}
		util.Log.Info(TempStr);

		if (BagNum >= 9)
		{
			BagNum = -1;
			List.clear();
			Init();
			util.Log.Info("清空数据统计");
		}

	}

}
