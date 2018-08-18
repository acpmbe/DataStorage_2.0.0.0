package dal.CmdS;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import util.MongoConn;

public class ControlReply_Dal
{

	public void Update(String collName, Document find, Document set)
	{

		MongoDatabase database = MongoConn.GetConnect();
		MongoCollection<Document> collection = database.getCollection(collName);
		collection.updateMany(find, set);

	}
}
