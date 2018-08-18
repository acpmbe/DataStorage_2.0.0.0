package util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Err
{
	private static String ErrInfo(Exception e)
	{

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

}
