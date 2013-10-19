package com.aws;

import java.util.HashMap;

import android.os.AsyncTask;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

public class AWSGetItem extends AsyncTask<Object, Void, GetItemResult>{

	@Override
	protected GetItemResult doInBackground(Object... params) {
		@SuppressWarnings("unchecked")
		HashMap<String,Object> param = (HashMap<String, Object>) params[0];
		AmazonDynamoDBClient dynamoDB = (AmazonDynamoDBClient) param.get("dynamoDB");
		GetItemRequest req = (GetItemRequest) param.get("request");
		return dynamoDB.getItem(req);
	}

}
