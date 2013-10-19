package com.aws;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import android.os.AsyncTask;

public class AWSQueryFilterResults extends AsyncTask<Object, Void, QueryResult>{

	@Override
	protected QueryResult doInBackground(Object... params) {
		HashMap<String,Object> param = (HashMap<String, Object>) params[0];
		AmazonDynamoDBClient dynamoDB = (AmazonDynamoDBClient) param.get("dynamoDB");
		QueryRequest request = (QueryRequest) param.get("request");
		return dynamoDB.query(request);
	}

}
