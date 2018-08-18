package bll.fun;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import mod.CmdS.ELastInfo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import util.*;

public class LastIdInfo_Bll
{

	private static Map<String, Long> List = new TreeMap<String, Long>();
	private static Long TempLong;

	public static String Init()
	{

		try
		{
			Connection conn = MysqlConn.GetConnect();
			String sql = "SELECT * FROM " + Config.MysqlSyncTable();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet retsult = pst.executeQuery();// 执行语句，得到结果集

			while (retsult.next())
			{
				List.put(retsult.getString("BID"), retsult.getLong("LID"));

			}

			pst.close();
			retsult.close();
			return "0";

		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}

	}

	public synchronized static Long getValue(ELastInfo type, String key)
	{

		try
		{
			switch (type)
			{
			case Get:
				if (List.containsKey(key))
				{
					TempLong = List.get(key) + 1L;
					List.put(key, TempLong);
					return TempLong;
				}
				else
				{
					List.put(key, 1L);
					return 1L;
				}
			case Sync:
				Data_TB();
				return 1L;
			default:
				break;
			}
			return -1L;
		}
		catch (Exception e)
		{

			if (type == ELastInfo.Get)
			{
				Log.Error("调用取LastId错误：" + ErrInfo(e));
			}
			if (type == ELastInfo.Sync)
			{
				Log.Error("调用同步LastId错误：" + ErrInfo(e));
			}

			return -1L;
		}

	}

	private static String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

	// public static void Run_Sync() throws InterruptedException
	// {
	//
	// while (true)
	// {
	// Thread.sleep(Config.SyncTime() * 1000);
	// Long Result = getValue(ELastInfo.Sync, "");
	// if (Result == -1)
	// {
	// Log.Error("同步Mysql_LastId错误");
	// }
	//
	// }
	// }

	private static void Data_TB() throws SQLException
	{

		// Map<String, Long> ListCopy = GetList();

		if (List.size() != 0)
		{

			dal.fun.LastIdInfo_Dal.Insert_Batch(Config.MysqlSyncTable(), List);

		}

	}

}
