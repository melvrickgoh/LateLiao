package com.example.nav2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

//delete this if you want the other http client to run

//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.client.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<MotionEvent> extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Button button = (Button)findViewById(R.id.send);
		button.setOnClickListener(mListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	public void setupUI(View view) {

	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, MainActivity event) {
	                hideSoftKeyboard(event);
	                return false;
	            }

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean onTouch(View arg0, android.view.MotionEvent arg1) {
					// TODO Auto-generated method stub
					return false;
				}

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView);
	        }
	    }
	}
	
	private OnClickListener mListener = new OnClickListener() {
	    @Override
		public void onClick(View v) {
	    	
	    	
	      // do something when the button is clicked
	    	EditText uName = (EditText) findViewById(R.id.uName);
	    	EditText pw = (EditText) findViewById(R.id.pw);
	    	uName.setVisibility(View.INVISIBLE);
	    	pw.setVisibility(View.INVISIBLE);
	    	
	    	//working json method. uncomment off to run with json
	    	String returnResult= null;
	    	JSONArray ja = new JSONArray();
	    	
	    	/*try {
				returnResult = connectToServerAndReadData(uName.getText().toString(),pw.getText().toString());
				//need to get return result
				 ja =  connectToServerAndReadData(uName.getText().toString(),pw.getText().toString());
				 String noOfAssignment = ja.get(0);
				 String assignmentDetails = ja.get(1);
				Log.v(returnResult, returnResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    	
	    	// testing without json. auto login
            if(uName.getText().toString().equals("abc"))  {
            	if (pw.getText().toString().equals("abc")) {
	    	//if( returnResult != null) 
            		//do something if it is "BYE"
            		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            		builder.setTitle("Welcome");
            		builder.setMessage("Hi "+ uName.getText().toString());
            		CharSequence text = "OK";
            		builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
            			
            			@Override
            			public void onClick(DialogInterface dialog, int which){
            				Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            				//sending retrieved information of user to next screen 
            				/*EditText editText = (EditText) findViewById(R.id.edit_message);
            				 String message = editText.getText().toString();
            				 Intent.putExtra(EXTRA_MESSAGE, message);*/
            				//user intent.putExtra(name,message) to pass message to next activity
            				startActivity(intent);
            			}

						
            		});
            		builder.show();
            		
            		//putting username and pw into json
            		/*DefaultHttpClient httpClient = new DefaultHttpClient();
            		ResponseHandler<String> responseHandler = new BasicResponseHandler();
            		HttpPost postMethod = new HttpPost("http://localhost");
            		try {
						postMethod.setEntity(new StringEntity("{\"username\" : uName,\"password\" : pw}"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		postMethod.setHeader("Content-Type","application/json");
            		String authorizationString = "Basic " + Base64.encodeToString(("authentication" + ":"+ "authentication").getBytes(),Base64.DEFAULT);
            		authorizationString.replace("\n", "");
            		postMethod.setHeader("Authorization", authorizationString);
            		try {
						httpClient.execute(postMethod,responseHandler);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
	    
            } } else {
                Context context = getApplicationContext();
            CharSequence text = "Wrong! :(";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, -50,10);
            toast.show(); 
            };
            uName.setVisibility(View.VISIBLE);
            pw.setVisibility(View.VISIBLE);
	    	
	    }
	};
	
	protected String sendJson(final String username, final String pw) {
		String finalAns = null;
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                	//put url here
                    HttpPost post = new HttpPost("http://192.168.0.10:8080/nav/login");
                    
                    json.put("username", username );
                    json.put("password", pw);
                    StringEntity se = new StringEntity( "?r=" + json.toString());  
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    //Checking response 
                    if(response!=null){
                    	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    	String jsonResult = reader.readLine();
                    	System.out.println(jsonResult);
                    	//JSONTokener tokener = new JSONTokener(jsonResult);
                    	//JSONArray finalResult = new JSONArray(tokener); //Get the data in the entity
                    	// might need to change here
                    	//finalAns = finalResult.getString(0);
                    	
                        
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                    Context context = getApplicationContext();
                    CharSequence text = "Error! cannot create connection!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, -50,10);
                    toast.show(); 
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();  
        return finalAns;
    }
	
	/*public String ygJson(String username, String pw) throws Exception {
	
		JSONObject json = new JSONObject();
		json.put("username", username );
        json.put("password", pw);
		String fullQuery = null;

		fullQuery = "?r=" + json.toString();
		
		// - Added by ONG Hong Seng
		// converting the query part into an encoded URL to take care of spaces
		// and
		// special characters. The constructor takes in
		// URI(String scheme, String authority, String path, String query,
		// String fragment)
		//
		String request = new java.net.URI("http", "192.168.0.10:8080",
				"/nav/login", fullQuery, "").toString();
		
	
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);
	
		// Send GET request
		int statusCode = client.executeMethod(method);
		
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusLine());
		}
		InputStream rstream = null;
		
		// Get the response body
		rstream = method.getResponseBodyAsStream();
			
			
		// Process the response from the service
		BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
		String line = br.readLine();
		Context context = getApplicationContext();
		 CharSequence text = line;
	        int duration = Toast.LENGTH_LONG;
	        Toast toast = Toast.makeText(context, text, duration);
	        toast.setGravity(Gravity.CENTER, -50,10);
	        toast.show(); 
		br.close();
		return line;
	}	*/
	/*
	private String connectToServerAndReadData(String username, String pw) {
		
		String returnResult = "";
	     HttpURLConnection conn;
	     boolean result = false;
	     JSONObject json = new JSONObject();
	    

	         try{
	        	 
	             // Enter any URL here you want to connect
	        	 json.put("username", username );
	   	      	json.put("password", pw);
	             URL url = new URL("http://192.168.0.10:8080/nav/login?r=" + json.toString());

	            // Open a HTTP connection to the URL

	             conn = (HttpURLConnection) url.openConnection();
	            // conn.connect();
	              BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	              returnResult = rd.readLine();
	
	            rd.close();
	           


	         }catch(MalformedURLException e){

	                 e.printStackTrace();
	         }
	         catch(IOException e){
	                 e.printStackTrace();               
	         }
	        catch(Exception e){
	                 e.printStackTrace();           
	         }



	         return returnResult;

	}
	*/
}
