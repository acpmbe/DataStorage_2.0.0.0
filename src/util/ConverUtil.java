package util;

import java.io.UnsupportedEncodingException;

public class ConverUtil
{
	
	static short[] GetArray(String content)
	{
		
		short[] sss =null;
		int count = content.length();
		if(count%2 ==0)
		{
			sss =new short[count/2];
			int cc=0;
			for(int i =0 ;i <count;i+=2)
			{
				sss[cc]=  Short.parseShort(content.substring(i, i+2), 16);
				cc++;

			}
		}
	
		
		return sss;
		
	}
	
	

	public static byte[] strToByteArray(String str)
	{
		if (str == null)
		{
			return null;
		}
		byte[] byteArray = str.getBytes();

		return byteArray;
	}

	public static byte[] hexStrToByteArray(String str)
	{
		if (str == null)
		{
			return null;
		}
		if (str.length() == 0)
		{
			return new byte[0];
		}
		byte[] byteArray = new byte[str.length() / 2];
		for (int i = 0; i < byteArray.length; i++)
		{
			String subStr = str.substring(2 * i, 2 * i + 2);
			byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
		}
		return byteArray;
	}

	public static byte[] hexToBytes(String hexString)
	{
		if (hexString == null || hexString.equals(""))
		{
			return null;
		}

		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] bytes = new byte[length];
		//String hexDigits = "0123456789abcdef";
		String hexDigits = "0123456789ABCDEF";
		for (int i = 0; i < length; i++)
		{
			int pos = i * 2; // 两个字符对应一个byte
			int h = hexDigits.indexOf(hexChars[pos]) << 4; // 注1
			int l = hexDigits.indexOf(hexChars[pos + 1]); // 注2
			if (h == -1 || l == -1)
			{ // 非16进制字符
				return null;
			}
			bytes[i] = (byte) (h | l);
		}
		return bytes;
	}

}
