package bll.CmdS;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import mod.EInfoStatis;
import util.Config;
import util.Log;
import util.ProtocolUtils;

public class Control_Bll implements ICmd
{

	public final String NAME = "命令_001F_控制";
	public final String ErrNAME = "命令_001F_控制_错误";
	private final String WriteNAME = "写入MongoDb数据";

	private final boolean IsPrint = Config.IsPrintTime();

	/**
	 * 命令_控制
	 */
	public Control_Bll()
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

				Document doc = new Document();

				String SubContent = content.substring(23 * 2, length - 4);
				String Guid = SubContent.substring(0, 32);

				int SubCmdIdInt = ProtocolUtils.byteToInt(ProtocolUtils.hexStringToByte(SubContent.substring(32, 36)));
				String SubCmdIdStr = String.valueOf(SubCmdIdInt);

				String SubData = SubContent.substring(18 * 2, SubContent.length());

				doc.append("GUID", Guid);

				int DevId = ProtocolUtils.byte4int(ProtocolUtils.hexStringToByte(content.substring(18, 26)));
				doc.append("DEVID", DevId);

				doc.append("COMMAND", String.valueOf(Integer.parseInt(content.substring(28, 32), 16)));
				doc.append("RESULT", 0);
				doc.append("CREATED", GetDate());
				doc.append("RESULTDATA", "");
				doc.append("CONTENT", SubData);
				doc.append("JSONDATA", "");
				doc.append("RESULTCOMMAND", SubCmdIdStr);
				doc.append("RESERVE", content.substring(32, 40));
				List.add(doc);

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

				dal.CmdS.Data_Dal dal = new dal.CmdS.Data_Dal();
				dal.Insert("tendencyControl", List);

				if (IsPrint)
				{
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, WriteNAME, new Long((long) List.size()));
				}

				Log.Info(NAME + "_批量插入Mongo 数量：" + List.size());

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
			Log.Error(NAME + "_批量插入Mongo错误：" + ErrInfo(ex));
			List = null;

		}

	}

	private Date GetDate()
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return ca.getTime();
	}

}
