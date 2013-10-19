package com.aws;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import android.os.AsyncTask;

public class AWSGetAllUsers extends AsyncTask<Object, Void, ScanResult> {

	@Override
	protected ScanResult doInBackground(Object... params) {
		@SuppressWarnings("unchecked")
		HashMap<String,Object> param = (HashMap<String, Object>) params[0];
		AmazonDynamoDBClient dynamoDB = (AmazonDynamoDBClient) param.get("dynamoDB");
		ScanRequest req = (ScanRequest) param.get("request");
		return (dynamoDB).scan(req);
	}

}
