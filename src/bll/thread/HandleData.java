package bll.thread;

import java.util.*;

import bll.Factory_Data;
import util.Log;

public class HandleData implements Runnable
{

	private	List<String> List = new ArrayList<String>();
	
	public HandleData(List<String> list)
	{
	
		List.addAll(list);  	
		
	
	}
	
	
	@Override
	public void run()
	{
		

		long startTime = System.currentTimeMillis();
		
		Factory_Data data = new Factory_Data(this.List);
		data.Execute();
		
		long endTime = System.currentTimeMillis(); 
			
		Log.Info("执行Redis数据"+List.size()+"条"+"  时间： " + (endTime - startTime) + "ms");
		
		
	}

}
