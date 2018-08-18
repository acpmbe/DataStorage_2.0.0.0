package util;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Config
{

	public static String MongoIP()
	{
		return mongoIP;
	}

	public static String MongoPort()
	{
		return mongoPort;
	}

	public static String MysqlPassWord()
	{
		return mysqlPassWord;
	}

	public static String MysqlUrl()
	{
		return mysqlUrl;
	}

	public static String MysqlUser()
	{
		return mysqlUser;
	}

	public static String RedisIP()
	{
		return redisIP;
	}

	public static int RedisPort()
	{
		return redisPort;
	}

	public static Long SyncTime()
	{
		return syncTime;
	}

	public static int DataSize()
	{
		return dataSize;
	}

	public static Long PrintTime()
	{
		return printTime;
	}

	public static Long RedisNoDataTime()
	{
		return redisNoDataTime;
	}

	public static String RedisPassWord()
	{
		return redisPassWord;
	}

	public static int RedisDb()
	{
		return redisDb;
	}

	public static String RedisListName()
	{
		return redisListName;
	}

	public static String MongoCollName()
	{
		return mongoCollName;
	}

	public static boolean IsSyncTime()
	{
		return isSyncTime;
	}

	public static boolean IsPrintTime()
	{
		return isPrintTime;
	}

	public static String MysqlSyncTable()
	{
		return mysqlSyncTable;
	}

	public static int NotDataNum()
	{
		return notDataNum;
	}

	// <MysqlUser>root</MysqlUser>
	// <MysqlPassWord>123456</MysqlPassWord>
	// <RedisIP>10.120.0.25</RedisIP>
	// <RedisPort>6380</RedisPort>
	// <MongoIP>10.120.0.25</MongoIP>
	// <MongoPort>2222</MongoPort>
	// <CodethreadPoolCount>10</CodethreadPoolCount>
	// <SyncTime>5</SyncTime>
	// <DataSize>1000</DataSize>
	// <PrintTime>5</PrintTime>
	// <ClearTime>60</ClearTime>
	// <RedisNoDataTime>1</RedisNoDataTime>

	private static String mongoIP;
	private static String mongoPort;
	private static String mysqlUrl;
	private static String mysqlUser;
	private static String mysqlPassWord;
	private static String redisIP;
	private static int redisPort;
	private static Long syncTime;
	private static int dataSize;
	private static Long printTime;
	private static Long redisNoDataTime;

	private static String redisPassWord;
	private static int redisDb;
	private static String redisListName;

	private static String mongoCollName;

	private static Boolean isSyncTime;
	private static Boolean isPrintTime;

	private static String mysqlSyncTable;

	private static int notDataNum;

	public static String Init(String path)
	{
		try
		{

			File f = new File(path + "/config.xml");

			if (!f.exists())
			{
				return "配置文件不存在";
			}
			SAXReader reader = new SAXReader();
			Document doc;
			doc = reader.read(f);
			Element root = doc.getRootElement();
			Element data;
			Iterator<?> itr = root.elementIterator("VALUE");
			data = (Element) itr.next();

			mongoIP = data.elementText("MongoIP").trim();
			mongoPort = data.elementText("MongoPort").trim();
			mysqlPassWord = data.elementText("MysqlPassWord").trim();
			mysqlUrl = data.elementText("MysqlUrl").trim();
			mysqlUser = data.elementText("MysqlUser").trim();
			redisIP = data.elementText("RedisIP").trim();
			redisPort = Integer.parseInt(data.elementText("RedisPort").trim());
			syncTime = Long.parseLong(data.elementText("SyncTime").trim());
			dataSize = Integer.parseInt(data.elementText("DataSize").trim());
			printTime = Long.parseLong(data.elementText("PrintTime").trim());
			redisNoDataTime = Long.parseLong(data.elementText("RedisNoDataTime").trim());

			redisPassWord = data.elementText("RedisPassWord").trim();
			redisDb = Integer.parseInt(data.elementText("RedisDb").trim());
			redisListName = data.elementText("RedisListName").trim();

			mongoCollName = data.elementText("MongoCollName").trim();

			isSyncTime = Boolean.parseBoolean(data.elementText("IsSyncTime").trim());
			isPrintTime = Boolean.parseBoolean(data.elementText("IsPrintTime").trim());

			mysqlSyncTable = data.elementText("MysqlSyncTable").trim();

			notDataNum = Integer.parseInt(data.elementText("NotDataNum").trim());

			return "0";

		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
	}

}
