package com.aws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

import com.aws.PropertyLoader;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.example.nav2.Event;
import com.example.nav2.Location;
import com.example.nav2.User;

public class AWSClientManager {
	static AmazonDynamoDBClient dynamoDB;
	
	public AWSClientManager (){
		validateCredentials();
	}
	
	public AmazonDynamoDBClient dynamoDB() {
        return dynamoDB;
    }
	
	public static AWSClientManager getInstance(){
		return new AWSClientManager();
	}
	
	public static void validateCredentials() {
        if ( dynamoDB == null ) {        
            Log.d( "AWSClientManager", "Creating New Clients." );
            
            Region region = Region.getRegion(Regions.AP_SOUTHEAST_1); 
        
            AWSCredentials credentials = new BasicAWSCredentials( PropertyLoader.getInstance().getAccessKey(), PropertyLoader.getInstance().getSecretKey() );
		    
            dynamoDB = new AmazonDynamoDBClient( credentials );
            dynamoDB.setRegion(region);
        }
    }
	
	public void clearClients() {
		dynamoDB = null; 
    }

    public boolean hasCredentials() {
        return PropertyLoader.getInstance().hasCredentials();
    }
    /*
    public static void main(String[] args) throws Exception {
        init();

        try {
            String tableName = "LateLiaoUser";
            
            // Add an item
            Map<String, AttributeValue> item = newItem("Bill & Ted's Excellent Adventure", 1989, "****", "James", "Sara");
            PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
            PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);

            // Add another item
            item = newItem("Airplane", 1980, "*****", "James", "Billy Bob");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);

            // Scan items for movies with a year attribute greater than 1985
            HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
            Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.GT.toString())
                .withAttributeValueList(new AttributeValue().withN("1985"));
            scanFilter.put("year", condition);
            ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            System.out.println("Result: " + scanResult);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
    */
    public void addNewLocation(Location l){
    	Map<String, AttributeValue> newLocationDetails = newLocation(l.getLocationName(),l.getLatitude(),l.getLongitude());
    	PutItemRequest putItemRequest = new PutItemRequest("LateLiaoLocation", newLocationDetails);
    	addItem(putItemRequest);
    }

	public void addNewUser(User u){    	
    	Map<String, AttributeValue> newUserDetails = newUser(u.getUsername(),u.getImageLocation(),u.getLevel(),u.getCurrentPoints(),u.getCurrentLocation().getLocationName());
    	PutItemRequest putItemRequest = new PutItemRequest("LateLiaoUser", newUserDetails);
    	addItem(putItemRequest);
    }
	
	public void addNewEvent(Event e){
		Map<String, AttributeValue> newEventDetails = newEvent(e.getEventName(),e.getEventDate(),e.getEventMonth(),e.getEventYear(),e.getEventTime(),e.getEventAttendees(),e.getEventLocation());
    	PutItemRequest putItemRequest = new PutItemRequest("LateLiaoEvent", newEventDetails);
    	addItem(putItemRequest);
	}
	
	private void addItem(PutItemRequest request){
		AWSPutItem awsPutItem = new AWSPutItem();
		HashMap<String,Object> param = new HashMap<String,Object>();
		PutItemResult result = new PutItemResult();
		
        param.put("dynamoDB", dynamoDB);
        param.put("request", request);
        
        awsPutItem.execute(param,null,result);
	}

	public User getUser(String username){
    	HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("Username", new AttributeValue().withS(username));
    
        GetItemRequest getItemRequest = new GetItemRequest().withTableName("LateLiaoUser").withKey(key);
        
        GetItemResult result = getItem(getItemRequest);
        Map<String, AttributeValue> map = result.getItem();
    
        return new User(map.get("Username").getS(),map.get("ImageLocation").getS(),Integer.valueOf(map.get("Level").getN()),Integer.valueOf(map.get("CurrentPoints").getN()),new Location("SIS",1.29757,103.84944));
    }
	
	public Location getLocation(String locationName){
    	HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("LocationName", new AttributeValue().withS(locationName));
    
        GetItemRequest getItemRequest = new GetItemRequest().withTableName("LateLiaoLocation").withKey(key);
        
        GetItemResult result = getItem(getItemRequest);
        Map<String, AttributeValue> map = result.getItem();
    
        return new Location(map.get("LocationName").getS(),Double.valueOf(map.get("Latitude").getN()),Double.valueOf(map.get("Longitude").getN()));
	}
	
	public Event getEvent (String eventName){
		HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("EventName", new AttributeValue().withS(eventName));
    
        GetItemRequest getItemRequest = new GetItemRequest().withTableName("LateLiaoEvent").withKey(key);
        
        GetItemResult result = getItem(getItemRequest);
        Map<String, AttributeValue> map = result.getItem();
    
        return new Event(map.get("EventName").getS(),Integer.valueOf(map.get("EventDate").getN()),Integer.valueOf(map.get("EventMonth").getN()),Integer.valueOf(map.get("EventYear").getN()),map.get("EventTime").getS(),new ArrayList<String>(map.get("EventAttendees").getSS()),getLocation(map.get("EventLocation").getS()));
	}
	
	private GetItemResult getItem (GetItemRequest request){
		AWSGetItem awsGetItem = new AWSGetItem();
		
		HashMap<String,Object> param = new HashMap<String,Object>();
		GetItemResult result = new GetItemResult();
		
        param.put("dynamoDB", dynamoDB);
        param.put("request", request);
        
        AsyncTask<Object,Void,GetItemResult> asyncResult = awsGetItem.execute(param,null,result);
        
        try {
        	result = asyncResult.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        return result;
	}
    
	public ArrayList<User> getAllUsers(){
    	ArrayList<User> users = new ArrayList<User>();
    	List<Map<String, AttributeValue>> rows = getAllItems("LateLiaoUser");
    	
    	for(Map<String, AttributeValue> map : rows){
            try{
            	users.add(new User(map.get("Username").getS(),map.get("ImageLocation").getS(),Integer.valueOf(map.get("Level").getN()),Integer.valueOf(map.get("CurrentPoints").getN()),getLocation(map.get("Location").getS())));
            } catch (NumberFormatException e){
                Log.d("AWSClientManager_getAllUsers",e.getMessage());
            }
        }
    	
    	return users;
	}
	
	public ArrayList<Location> getAllLocations(){
    	ArrayList<Location> locations = new ArrayList<Location>();
    	List<Map<String, AttributeValue>> rows = getAllItems("LateLiaoLocation");
    	
    	for(Map<String, AttributeValue> map : rows){
            try{
            	locations.add(new Location(map.get("LocationName").getS(),Double.valueOf(map.get("Latitude").getN()),Double.valueOf(map.get("Longitude").getN())));
            } catch (NumberFormatException e){
                Log.d("AWSClientManager_getAllLocations",e.getMessage());
            }
        }
    	
    	return locations;
	}
	
	public ArrayList<Event> getAllEvents(){
    	ArrayList<Event> events = new ArrayList<Event>();
    	List<Map<String, AttributeValue>> rows = getAllItems("LateLiaoEvent");
    	
    	for(Map<String, AttributeValue> map : rows){
            try{
            	events.add(new Event(map.get("EventName").getS(),Integer.valueOf(map.get("EventDate").getN()),Integer.valueOf(map.get("EventMonth").getN()),Integer.valueOf(map.get("EventYear").getN()),map.get("EventTime").getS(),new ArrayList<String>(map.get("EventAttendees").getSS()),getLocation(map.get("EventLocation").getS())));
            } catch (NumberFormatException e){
                Log.d("AWSClientManager_getAllEvents",e.getMessage());
            }
        }
    	
    	return events;
	}
	
	public List<Map<String, AttributeValue>> getAllItems(String tableName){
    	ScanResult result = null;
    	AsyncTask<Object, Void, ScanResult> asyncResult = null;

            ScanRequest req = new ScanRequest();
            req.setTableName(tableName);
            
            AWSGetAllItems awsAllItems = new AWSGetAllItems();
             
            HashMap<String,Object> param = new HashMap<String,Object>();
            param.put("dynamoDB", dynamoDB);
            param.put("request", req);
            asyncResult = awsAllItems.execute(param,null,result);
            try {
				result = asyncResult.get();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
     
            List<Map<String, AttributeValue>> rows = result.getItems();
            
         return rows;
    }
	
    public ArrayList<User> getUsers(){
    	ArrayList<User> users = new ArrayList<User>();
    	ScanResult result = null;
    	AsyncTask<Object, Void, ScanResult> asyncResult = null;
    	do{
            ScanRequest req = new ScanRequest();
            req.setTableName("LateLiaoUser");
     
            if(result != null){
                req.setExclusiveStartKey(result.getLastEvaluatedKey());
            }
            
            AWSGetAllItems awsAllUsers = new AWSGetAllItems();
             
            HashMap<String,Object> param = new HashMap<String,Object>();
            param.put("dynamoDB", dynamoDB);
            param.put("request", req);
            asyncResult = awsAllUsers.execute(param,null,result);
            try {
				result = asyncResult.get();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
     
            List<Map<String, AttributeValue>> rows = result.getItems();
     
            for(Map<String, AttributeValue> map : rows){
                try{
                    users.add(new User(map.get("Username").getS(),map.get("ImageLocation").getS(),Integer.valueOf(map.get("Level").getN()),Integer.valueOf(map.get("CurrentPoints").getN()),getLocation(map.get("EventLocation").getS())));
                } catch (NumberFormatException e){
                    Log.d("AWSClientManager_getAllUsers",e.getMessage());
                }
            }
        } while(result.getLastEvaluatedKey() != null);
    	
    	return users;
    }
    
    public boolean checkUserExists(String username){
    	return checkItemExists("LateLiaoUser","Username",username);
    }
    
    public boolean checkEventExists(String eventName){
    	return checkItemExists("LateLiaoEvent","EventName",eventName);
    }
    
    public boolean checkLocationExists(String locationName){
    	return checkItemExists("LateLiaoLocation","LocationName",locationName);
    }
    
    private boolean checkItemExists(String tableName,String hashKey, String hashKeyValue){
    	HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put(hashKey, new AttributeValue().withS(hashKeyValue));
    	
    	GetItemResult result =  getItem(new GetItemRequest().withTableName(tableName).withKey(key));
    		
    	return (result.getItem() != null);
    }
    
    private Map<String, AttributeValue> newUser(String username, String imageName, int level, int currentPoints, String locationName) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("Username", new AttributeValue().withS(username));
        item.put("ImageLocation", new AttributeValue().withS(imageName));
        item.put("Level", new AttributeValue().withN(String.valueOf(level)));
        item.put("CurrentPoints", new AttributeValue().withN(String.valueOf(currentPoints)));
        item.put("Location", new AttributeValue().withS(locationName));
        return item;
    }
    
    private Map<String, AttributeValue> newLocation(String locationName, double latitude, double longitude) {
    	Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
    	item.put("LocationName", new AttributeValue().withS(locationName));
    	item.put("Latitude", new AttributeValue().withN(String.valueOf(latitude)));
    	item.put("Longitude", new AttributeValue().withN(String.valueOf(longitude)));
    	return item;
	}
    
    private Map<String, AttributeValue> newEvent(String eventName, int eventDate, int eventMonth, int eventYear, String eventTime, ArrayList<String> eventAttendees, Location eventLocation) {
    	Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
    	item.put("EventName", new AttributeValue().withS(eventName));
    	item.put("EventDate", new AttributeValue().withN(String.valueOf(eventDate)));
    	item.put("EventMonth", new AttributeValue().withN(String.valueOf(eventMonth)));
    	item.put("EventYear", new AttributeValue().withN(String.valueOf(eventYear)));
    	item.put("EventTime", new AttributeValue().withS(eventTime));
    	item.put("EventAttendees", new AttributeValue().withSS(eventAttendees));
    	item.put("EventLocation", new AttributeValue().withS(eventLocation.getLocationName()));
    	return item;
	}
    
    @SuppressWarnings("unused")
	private void waitForTableToBecomeAvailable(String tableName) {
        System.out.println("Waiting for " + tableName + " to become ACTIVE...");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (10 * 60 * 1000);
        while (System.currentTimeMillis() < endTime) {
            try {Thread.sleep(1000 * 20);} catch (Exception e) {}
            try {
                DescribeTableRequest request = new DescribeTableRequest().withTableName(tableName);
                TableDescription tableDescription = dynamoDB.describeTable(request).getTable();
                String tableStatus = tableDescription.getTableStatus();
                System.out.println("  - current state: " + tableStatus);
                if (tableStatus.equals(TableStatus.ACTIVE.toString())) return;
            } catch (AmazonServiceException ase) {
                if (ase.getErrorCode().equalsIgnoreCase("ResourceNotFoundException") == false) throw ase;
            }
        }

        throw new RuntimeException("Table " + tableName + " never went active");
    }
    
}
