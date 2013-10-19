package com.aws;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import android.os.AsyncTask;

public class AWSPutItem extends AsyncTask<Object, Void, PutItemResult>{

	@Override
	protected PutItemResult doInBackground(Object... params) {
		HashMap<String,Object> param = (HashMap<String, Object>) params[0];
		AmazonDynamoDBClient dynamoDB = (AmazonDynamoDBClient) param.get("dynamoDB");
		PutItemRequest req = (PutItemRequest) param.get("request");
		return dynamoDB.putItem(req);
	}

}
