package dal.fun;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import util.Log;

public class LastIdInfo_Dal
{

	public static void Insert_Batch(String tableName, Map<String, Long> List)
	{

		Map<String, Long> ListCopy = null;
		try
		{

			ListCopy = new HashMap<String, Long>();
			ListCopy.putAll(List);

			Connection conn = util.MysqlConn.GetConnect();
			// String sql_Test = "INSERT INTO mongodbcurlid
			// (BID,LID,Createtime)VALUES(?,?,?) ON DUPLICATE KEY UPDATE
			// LID=?,Createtime=?";
			String sql = "INSERT INTO " + tableName
					+ " (BID,LID,Createtime)VALUES(?,?,?) ON DUPLICATE KEY UPDATE LID=?,Createtime=?";
			PreparedStatement par = conn.prepareStatement(sql);

			java.sql.Timestamp timestamp = util.ComUtil.GetData();

			for (Map.Entry<String, Long> entry : ListCopy.entrySet())
			{

				par.setString(1, entry.getKey());
				par.setLong(2, entry.getValue());
				par.setTimestamp(3, timestamp);
				par.setLong(4, entry.getValue());
				par.setTimestamp(5, timestamp);
				par.addBatch();

			}
			par.executeBatch();
			par.close();
			ListCopy = null;

		}
		catch (Exception ex)
		{

			Log.Error("同步LastId错误：" + ErrInfo(ex));
			ListCopy = null;

		}

	}

	private static String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

	// private static String Insert_BL(Map<String, Long> List) throws Exception
	// {
	// Connection conn = null;
	// try
	// {
	//
	// conn = util.MysqlConn.GetConnect();
	// conn.setAutoCommit(false);// 设置为手动提交事务
	//
	// String sql = "INSERT INTO mongodbcurlid_New(BID,LID,
	// Createtime)VALUES(?,?,?)";
	//
	// PreparedStatement par = null;
	//
	// for (Map.Entry<String, Long> entry : List.entrySet())
	// {
	//
	// par = conn.prepareStatement(sql); // 会抛出异常
	// par.setString(1, entry.getKey());
	// par.setLong(2, entry.getValue());
	// par.setTimestamp(3, util.ComUtil.GetData());
	// par.executeUpdate();
	//
	// }
	//
	// conn.commit(); // 如果所有sql语句成功，则提交事务
	//
	// par.close();
	//
	// conn.setAutoCommit(true);
	//
	// return "0";
	//
	// }
	// catch (ClassNotFoundException ex)
	// {
	// conn.setAutoCommit(true);
	// return ex.getMessage();
	//
	// }
	// catch (SQLException ex)
	// {
	//
	// try
	// {
	// conn.rollback();// 只要有一个sql语句出现错误，则将事务回滚
	// conn.setAutoCommit(true);
	// return ex.getMessage();
	//
	// }
	// catch (SQLException eq)
	// {
	// conn.setAutoCommit(true);
	// return eq.getMessage();
	// }
	// }
	//
	// }

	// private static int GetRows()
	// {
	//
	// try
	// {
	// Connection conn = util.MysqlConn.GetConnect();
	// PreparedStatement pst = conn.prepareStatement("SELECT COUNT(1) FROM
	// mongodbcurlid_New");
	// ResultSet retsult = pst.executeQuery();// 执行语句，得到结果集
	//
	// while (retsult.next())
	// {
	// return retsult.getInt("COUNT(1)");
	// }
	// return 0;
	// }
	// catch (Exception ex)
	// {
	//
	// ex.printStackTrace();
	// return -1;
	// }
	//
	// }

	// private static boolean Delete()
	// {
	//
	// try
	// {
	// Connection conn = util.MysqlConn.GetConnect();
	// PreparedStatement pst = conn.prepareStatement("DELETE FROM
	// mongodbcurlid_New");
	// return pst.executeUpdate() > 0;// 执行语句，得到结果集
	// }
	// catch (Exception ex)
	// {
	//
	// ex.printStackTrace();
	// return false;
	//
	// }
	//
	// }

	// private static boolean Insert(LastIdInfo_Mod info)
	// {
	//
	// try
	// {
	// // Connection conn = DBPool.getConnection();
	// Connection conn = util.MysqlConn.GetConnect();
	// String sql = "INSERT INTO mongodbcurlid_New(BID,LID,
	// Createtime)VALUES(?,?,?)";
	// PreparedStatement par = conn.prepareStatement(sql); // 会抛出异常
	//
	// par.setString(1, info.getSpeId()); // 设置SQL语句第一个“？”的值
	// par.setLong(2, info.getLid()); // 设置SQL语句第二个“？”的值
	// par.setTimestamp(3, info.getCreateTime());
	//
	// return par.executeUpdate() > 0; // 执行插入数据操作，返回影响的行数
	// }
	// catch (Exception ex)
	// {
	//
	// ex.printStackTrace();
	// return false;
	// }
	//
	// }

}
