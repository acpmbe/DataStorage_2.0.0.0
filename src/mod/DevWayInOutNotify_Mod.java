package mod;

import java.util.Date;

public class DevWayInOutNotify_Mod
{


	
	private String RECID;
	private int DEVID;
	private String CHANNEL;
	private String IPENDPOINTS;
	private Date UP_TIME;
	private String STATUS;
	
	
	public String getRECID()
	{
		return RECID;
	}
	public void setRECID(String rECID)
	{
		RECID = rECID;
	}
	public int getDEVID()
	{
		return DEVID;
	}
	public void setDEVID(int dEVID)
	{
		DEVID = dEVID;
	}
	public String getCHANNEL()
	{
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL)
	{
		CHANNEL = cHANNEL;
	}
	public String getIPENDPOINTS()
	{
		return IPENDPOINTS;
	}
	public void setIPENDPOINTS(String iPENDPOINTS)
	{
		IPENDPOINTS = iPENDPOINTS;
	}
	public Date getUP_TIME()
	{
		return UP_TIME;
	}
	public void setUP_TIME(Date uP_TIME)
	{
		UP_TIME = uP_TIME;
	}
	public String getSTATUS()
	{
		return STATUS;
	}
	public void setSTATUS(String sTATUS)
	{
		STATUS = sTATUS;
	}


}
