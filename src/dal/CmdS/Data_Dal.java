package dal.CmdS;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import util.MongoConn;

public class Data_Dal
{
	
	public void Insert(String collName,List<Document> list)
	{
		
		
		MongoDatabase database = MongoConn.GetConnect();
		MongoCollection<Document> collection = database.getCollection(collName);	
		collection.insertMany(list) ;
		

	}

}
