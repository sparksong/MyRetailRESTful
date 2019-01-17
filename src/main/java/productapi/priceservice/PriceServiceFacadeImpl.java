package productapi.priceservice;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import productapi.data.PriceData;

//Service Facade Implementation used to access MongoDB for Price information.
public class PriceServiceFacadeImpl implements PriceServiceFacade {

	public PriceData getProductPriceByID(String id) {
		PriceData priceData = new PriceData("", "");

		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB database = mongoClient.getDB("test");
			DBCollection priceCollection = database.getCollection("ProductPriceData");

			DBCursor cursor = priceCollection.find(new BasicDBObject("id", id.trim()));

			if (cursor.hasNext()) {
				DBObject object = cursor.next();
				priceData.setValue(object.get("value") != null ? object.get("value").toString() : "");
				priceData.setCurrencyCode(
						object.get("currencyCode") != null ? object.get("currencyCode").toString() : "");
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return priceData;
	}

	public PriceData updateProductPrice(String id, String value, String currencyCode) {
		// Compare old value and currencyCode to new values, if there is a non-blank, non-null change, update in the database. Otherwise leave it the same.
		PriceData priceData = getProductPriceByID(id);
		boolean hasBeenUpdated = false;
		
		if (value != null && !value.isEmpty() && value != priceData.getValue()) {
			priceData.setValue(value);
			hasBeenUpdated = true;
		}

		if (currencyCode != null && !currencyCode.isEmpty() && currencyCode != priceData.getCurrencyCode()) {
			priceData.setCurrencyCode(currencyCode);
			hasBeenUpdated = true;
		}
		
		//If data has not changed, database does not need to be updated.
		if(hasBeenUpdated) {
			try {
				MongoClient mongoClient = new MongoClient("localhost", 27017);
				DB database = mongoClient.getDB("test");
				DBCollection priceCollection = database.getCollection("ProductPriceData");

				BasicDBObject query = new BasicDBObject();
				query.put("id", id);

				BasicDBObject newPriceData = new BasicDBObject();
				newPriceData.put("value", priceData.getValue());
				newPriceData.put("currencyCode", priceData.getCurrencyCode());

				BasicDBObject updateObject = new BasicDBObject();
				updateObject.put("$set", newPriceData);

				priceCollection.update(query, updateObject);
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		return priceData;
	}
}
