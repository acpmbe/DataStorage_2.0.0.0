package util;

import java.sql.*;

public class MysqlConn
{

	private static java.sql.Connection dataConnect;

	private MysqlConn()
	{

	}

	private static Object object = new Object();

	public static Connection GetConnect() throws Exception 
	{

		if (dataConnect == null)
		{

			synchronized (object)
			{
				if (dataConnect == null)
				{

					Class.forName("com.mysql.jdbc.Driver");
					String url = Config.MysqlUrl();
					String user = Config.MysqlUser();
					String Password = Config.MysqlPassWord();
					dataConnect = DriverManager.getConnection(url, user, Password);

				}

			}

		}
		return dataConnect;
	}
	

}
