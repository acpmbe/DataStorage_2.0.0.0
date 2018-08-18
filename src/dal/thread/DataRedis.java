package dal.thread;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import util.RedisConn;

public class DataRedis
{

	public static String GetValue(String collName)
	{

		Jedis dis = RedisConn.GetJedis();	
		
		return dis.rpop(collName);

	}
	


	public static List<String> GetList(String listName, int size)
	{
		Jedis dis = RedisConn.GetJedis();

		Transaction ts = dis.multi();
		ts.lrange(listName, 0, size - 1);
		ts.ltrim(listName, size, -1);

		// ts.lrange("Redis_Test",5, 7 );
		// ts.ltrim("Redis_Test",size, -1 );
		List<Object> list = ts.exec();
		return (ArrayList<String>) list.get(0);
	}

	public static Long GetSize(String collName)
	{

		Jedis dis = RedisConn.GetJedis();
		return dis.llen(collName);

	}

}
