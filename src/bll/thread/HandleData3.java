package bll.thread;

import java.util.*;

import bll.Factory_Data;
import util.Log;

public class HandleData3
{

	public void run(List<String> list)
	{

		long startTime = System.currentTimeMillis();

		Factory_Data data = new Factory_Data(list);
		data.Execute();

		long endTime = System.currentTimeMillis();

		Log.Info("执行Redis数据" + list.size() + "条" + "  时间： " + (endTime - startTime) + "ms");

	}

}
