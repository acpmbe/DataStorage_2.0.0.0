package bll.CmdS;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import mod.EInfoStatis;
import util.Config;
import util.Log;

public class ControlReply_Bll implements ICmd
{

	public final String NAME = "命令_0020_控制应答";
	public final String ErrNAME = "命令_0020_控制应答_错误";
	private final String WriteNAME = "写入MongoDb数据";

	private final boolean IsPrint = Config.IsPrintTime();

	public ControlReply_Bll()
	{

	}

	private List<Document> List = new ArrayList<Document>();

	@Override
	public void SetData(String content)
	{
		try
		{
			int length = content.length();
			if (length >= 43 * 2)
			{

				String SubContent = content.substring(23 * 2, length - 4);
				String Guid = SubContent.substring(0, 32);
				List.add(new Document("GUID", Guid));

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

				Document set = new Document("$set", new Document("RESULT", 1));
				dal.CmdS.ControlReply_Dal dal = new dal.CmdS.ControlReply_Dal();
				for (Document Select : List)
				{
					dal.Update("tendencyControl", Select, set);

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
