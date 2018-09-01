package bll.CmdS;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import mod.EInfoStatis;
import mod.CmdS.ELastInfo;
import util.Config;
import util.Log;
import util.ProtocolUtils;

/**
 * 处理设备到前置机的数据。
 *
 */
public class Data_Bll implements ICmd
{

	public final String NAME = "命令_0007_数据";
	public final String ErrNAME = "命令_0007_数据_错误";
	private final String WriteNAME = "写入MongoDb数据";

	private final String CollName = Config.MongoCollName();
	private final boolean IsPrint = Config.IsPrintTime();

	// private Document doc = null;
	private List<Document> List = new ArrayList<Document>();

	private Calendar calendar = Calendar.getInstance();

	// tendencydata

	/**
	 * 处理设备到前置机的数据
	 * 
	 * @param content
	 *            = 数据内容
	 */
	public Data_Bll()
	{

	}

	/*
	 * 设置数据。
	 */
	public void SetData(String content)
	{

		String SubContent = null;
		try
		{

			SubContent = content.substring(23 * 2, (content.length()) - 4);

			if (!SubContent.equals(""))
			{

				Document doc = new Document();

				String Speid = SubContent.substring(0, 8);
				Speid = String.valueOf(ProtocolUtils.byte4int(ProtocolUtils.hexStringToByte(Speid)));

				Long LastId = bll.fun.LastIdInfo_Bll.getValue(ELastInfo.Get, Speid);

				doc.append("LID", LastId);

				Date Temp_date = new Date();
				calendar.setTime(Temp_date);
				Date date = calendar.getTime();

				doc.append("CREATED", date);

				Temp_date = null;
				date = null;

				String RN = content.substring(12, 16);
				doc.append("RN", RN);
				RN = null;

				String devIdStr = content.substring(18, 26);
				int devId = (ProtocolUtils.byte4int(ProtocolUtils.hexStringToByte(devIdStr)));
				doc.append("DEVID", devId);
				devIdStr = null;
				devId = 0;

				String Reserve = content.substring(32, 40);
				doc.append("RESERVE", Reserve);

				String SUBDEVTYPE = Reserve.substring(4, 8);
				doc.append("SUBDEVTYPE", SUBDEVTYPE);
				Reserve = null;
				SUBDEVTYPE = null;

				doc.append("SPEID", Speid);
				Speid = null;

				String Ver = SubContent.substring(8, 12);
				String Version = String.valueOf(ProtocolUtils.byteToInt(ProtocolUtils.hexStringToByte(Ver)));
				doc.append("VERSION", Version);
				Ver = null;
				Version = null;

				String subCmd = SubContent.substring(12, 16);
				String subCmdStr = String.valueOf(ProtocolUtils.byteToInt(ProtocolUtils.hexStringToByte(subCmd)));
				doc.append("CMDID", subCmdStr);

				subCmd = null;
				subCmdStr = null;

				String Comment = SubContent.substring(16, SubContent.length());
				doc.append("COMTENT", Comment);
				Comment = null;

				if (LastId != -1)
				{
					List.add(doc);
					doc = null;
					LastId = null;

				}
				else
				{

					if (IsPrint)
					{
						bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, ErrNAME, 1L);
					}

					Log.Error(ErrNAME + "：LastId为-1");
				}

			}
			SubContent = null;

		}
		catch (Exception ex)
		{

			if (IsPrint)
			{
				bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, ErrNAME, 1L);
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sd = sw.toString();
			String info = ErrNAME + "：" + sd;
			Log.Error(info);
			sw = null;
			pw = null;
			sd = null;
			info = null;
			SubContent = null;

		}

	}

	/*
	 * 处理数据。
	 */
	public void Execute()
	{

		try
		{
			if (List.size() > 0)
			{

				// dal.CmdS.Data_Dal dal = new dal.CmdS.Data_Dal();
				// dal.Insert(CollName, List);

				if (IsPrint)
				{
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, WriteNAME, new Long((long) List.size()));
				}

				String info = NAME + "_批量插入Mongo 数量：" + List.size();
				Log.Info(info);
				info = null;

				//dal = null;
				List = null;
				calendar = null;

			}

		}
		catch (Exception ex)
		{

			if (IsPrint)
			{
				bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, ErrNAME, new Long((long) List.size()));
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sd = sw.toString();
			String info = NAME + "_批量插入Mongo错误：" + sd;
			Log.Error(info);
			sw = null;
			pw = null;
			sd = null;
			info = null;

			List = null;
			calendar = null;

		}

	}

}
