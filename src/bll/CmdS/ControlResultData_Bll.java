package bll.CmdS;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import mod.ControlResultData_Mod;
import mod.EInfoStatis;
import util.Config;
import util.Log;

public class ControlResultData_Bll implements ICmd
{

	public final String NAME = "命令_002B_控制结果数据";
	public final String ErrNAME = "命令_002B_控制结果数据_错误";
	private final String WriteNAME = "写入MongoDb数据";

	private final boolean IsPrint = Config.IsPrintTime();

	public ControlResultData_Bll()
	{

	}

	private List<ControlResultData_Mod> List = new ArrayList<ControlResultData_Mod>();

	@Override
	public void SetData(String content)
	{

		try
		{
			int length = content.length();
			if (length >= 43 * 2)
			{

				String SubContent = content.substring(23 * 2, length - 4);

				ControlResultData_Mod info = new ControlResultData_Mod();
				info.setGuid(SubContent.substring(0, 32));
				info.setResultData(SubContent.substring(36, SubContent.length()));
				// info.setResultDataJson("");
				List.add(info);

			}
		}
		catch (Exception ex)
		{

			if (IsPrint)
			{
				bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, ErrNAME, 1L);
			}
			Log.Error(ErrNAME + "：" + ErrInfo(ex));

		}

	}

	private String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

	@Override
	public void Execute()
	{

		try
		{
			if (List.size() > 0)
			{
				dal.CmdS.ControlResultData_Dal dal = new dal.CmdS.ControlResultData_Dal();

				org.bson.Document find;
				org.bson.Document set;

				for (ControlResultData_Mod info : List)
				{
					find = new org.bson.Document("GUID", info.getGuid());
					set = new org.bson.Document();
					set.append("$set", new org.bson.Document("RESULTDATA", info.getResultData()));
					dal.Update("tendencyControl", find, set);

				}

				if (IsPrint)
				{
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, WriteNAME, new Long((long) List.size()));
				}

				Log.Info(NAME + "：批量更新Mongo 数量：" + List.size());

				dal = null;
				List = null;

			}
		}
		catch (Exception ex)
		{

			if (IsPrint)
			{
				bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, ErrNAME, new Long((long) List.size()));
			}
			Log.Error(NAME + "_批量更新Mongo错误：" + ErrInfo(ex));
			List = null;

		}

	}

}
