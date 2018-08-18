


import redis.clients.jedis.Jedis;
import util.RedisConn;

public class Redis_Test_New
{
	
	private String name;

	public Redis_Test_New(String Name)
	{
		this.name = Name;
	}


	
	
	public void Insert()
	{

		Jedis  dis = RedisConn.GetJedis();
		
		String Temp = "DC000000000000000011320600010007000000006400649D00000001009D0000000A0001F112011E0F000A143004244838A6FF434D43432D7156747100C0E8F97FD8E8F97F000000000000000001000000A0D39A0000000000000000000000000000000000000000000000000000000000000000000100000000003344";
		
		
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 500000; i++)
		{
			
			
			dis.lpush("RedisData", Temp);
		}

		long endTime = System.currentTimeMillis(); // 获取结束时间

		System.out.println("插入Redis时间： " + (endTime - startTime) + "ms");
	}
	
	
	private static Object object = new Object();
	
	public void Select()
	{
				
		Jedis  dis = RedisConn.GetJedis();
		
		String content="";	
		int num = 0;
		long startTime = System.currentTimeMillis();	
		while(true)
		{
			try
			{
				 content = dis.rpop("RedisData_New");
				 
				 
				// content = dis.lpop("RedisData_New");
				 
		
				 if(content==null)
				 {
					 break;
				 }	 
				 num++;
				
//				 if(num == 1000 || num ==2000)
//				 {
//					 System.out.println("已经读取"+num);
//				 }
//			
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		}

		long endTime = System.currentTimeMillis(); 
		
		System.out.println(name+"_读取Redis条数"+num+"  时间： " + (endTime - startTime) + "ms");
		
	
		
	}
	
	








	


	
	public void Count()
	{

		Jedis  dis = RedisConn.GetJedis();
		Long size = dis.llen("RedisData_New");

		System.out.println("列表行数：" + size);
	}


}
