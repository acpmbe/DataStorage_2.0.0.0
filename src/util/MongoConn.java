package util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

public class MongoConn
{

	private MongoConn()
	{

	}

	private static MongoClientOptions.Builder buide;
	private static MongoClientOptions myOptions;

	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static Object object = new Object();

	public static MongoDatabase GetConnect()
	{

		if (database == null)
		{

			synchronized (object)
			{

				if (database == null)
				{

					buide = new MongoClientOptions.Builder();
					buide.connectionsPerHost(100);// 与目标数据库可以建立的最大链接数
					buide.connectTimeout(1000 * 60 * 20);// 与数据库建立链接的超时时间
					buide.maxWaitTime(100 * 60 * 5);// 一个线程成功获取到一个可用数据库之前的最大等待时间
					buide.threadsAllowedToBlockForConnectionMultiplier(100);

					// buide.socketTimeout(0);
					buide.socketKeepAlive(true);
					myOptions = buide.build();

					mongoClient = new MongoClient(Config.MongoIP() + ":" + Config.MongoPort(), myOptions);

					database = mongoClient.getDatabase("tendency");

					// mongoClient = new MongoClient(Config.MongoIP(),
					// Integer.parseInt(Config.MongoPort()));
					// database = mongoClient.getDatabase("tendency");
				}

			}

		}
		return database;

	}

}
