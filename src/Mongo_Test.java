import org.bson.Document;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class Mongo_Test
{



    
	private static Date GetDate()
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return ca.getTime();
	}
	
	

    public void Run()
    {

   
    	
		List<Document> list = new ArrayList<Document>();

		Document doc;
		for (int i = 0; i < 5; i++)
		{
			doc = new Document();
			doc.append("LID", 16+i);
			doc.append("CREATED", GetDate());
			doc.append("RN", "0000");
			doc.append("DEVID", 406033);
			doc.append("RESERVE", "0000");
			doc.append("SUBDEVTYPE", "0000");
			doc.append("SPEID", "157");
			doc.append("VERSION", "1");
			doc.append("CMDID", "61331");
			doc.append("COMTENT", "123");
			list.add(doc);
		}

		dal.CmdS.Data_Dal dal = new dal.CmdS.Data_Dal();

		dal.Insert("tendencydata_New", list);
		
		
    }






 


}
