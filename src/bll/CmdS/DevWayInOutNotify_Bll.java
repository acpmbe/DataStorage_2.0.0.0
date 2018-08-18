package bll.CmdS;

import static org.junit.Assert.assertNotNull;

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

public class DevWayInOutNotify_Bll implements ICmd
{

	public final String NAME = "命令_0026_设备通道上下线通知";
	public final String ErrNAME = "命令_0026_设备通道上下线通知_错误";
	private final String WriteNAME = "写入MongoDb数据";

	private final boolean IsPrint = Config.IsPrintTime();

	public DevWayInOutNotify_Bll()
	{

	}

	private List<Document> List = new ArrayList<Document>();

	@Override
	public void SetData(String content)
	{

		try
		{
			String SubContent = content.substring(23 * 2, content.length() - 4);
			String SubContentZ = HexStrToStr(SubContent);
			String[] array = SubContentZ.split("/");
			if (array.length == 3)
			{

				Document doc = new Document();
				doc.append("RECID", GetGuid());

				int DevId = ProtocolUtils.byte4int(ProtocolUtils.hexStringToByte(content.substring(18, 26)));
				doc.append("DEVID", DevId);

				String CHANNEL = content.substring(12, 14);
				doc.append("CHANNEL", CHANNEL);

				doc.append("IPENDPOINTS", array[0] + ":" + array[1]);
				doc.append("UP_TIME", GetDate());
				doc.append("STATUS", array[2]);

				List.add(doc);

			}
			else
			{

				if (IsPrint)
				{
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, ErrNAME, 1L);
				}
				Log.Warn(NAME + "_源数据格式错误：原始数据：" + SubContentZ);
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

				dal.CmdS.DevWayInOutNotify_Dal dal = new dal.CmdS.DevWayInOutNotify_Dal();
				dal.Insert("tendencyDevWayInOutNotify", List);

				if (IsPrint)
				{
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, NAME, new Long((long) List.size()));
					bll.fun.InfoStatis_Bll.Handle(EInfoStatis.Add, WriteNAME, new Long((long) List.size()));
				}

				Log.Info(NAME + "：批量插入Mongo 数量：" + List.size());

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

	/**
	 * 16进制转换成为string类型字符串
	 * 
	 * @param s
	 * @return
	 */
	private String HexStrToStr(String s)
	{
		if (s == null || s.equals(""))
		{
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++)
		{
			try
			{
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			s = new String(baKeyword, "UTF-8");
			new String();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return s;
	}

	private String GetGuid()
	{
		return java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	private Date GetDate()
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return ca.getTime();
	}
}
