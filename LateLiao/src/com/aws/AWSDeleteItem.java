package com.aws;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;

import android.os.AsyncTask;

public class AWSDeleteItem extends AsyncTask<Object, Void, DeleteItemResult>{

	@Override
	protected DeleteItemResult doInBackground(Object... params) {
		HashMap<String,Object> param = (HashMap<String, Object>) params[0];
		AmazonDynamoDBClient dynamoDB = (AmazonDynamoDBClient) param.get("dynamoDB");
		DeleteItemRequest req = (DeleteItemRequest) param.get("request");
		return dynamoDB.deleteItem(req);
	}

}
