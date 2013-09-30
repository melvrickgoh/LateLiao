/*
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

import android.util.Log;

/**
* This class is used to get clients to the various AWS services.  Before accessing a client 
* the credentials should be checked to ensure validity.
*/
public class AmazonClientManager {
    private static final String LOG_TAG = "AmazonClientManager";

    private AmazonSimpleDBClient sdbClient = null;
    
    public AmazonClientManager() {
    }

    public AmazonSimpleDBClient sdb() {
        validateCredentials();    
        return sdbClient;
    }
    
    public boolean hasCredentials() {
        return PropertyLoader.getInstance().hasCredentials();
    }
    
    public void validateCredentials() {
        if ( sdbClient == null ) {        
            Log.i( LOG_TAG, "Creating New Clients." );
            
            Region region = Region.getRegion(Regions.AP_SOUTHEAST_1); 
        
            AWSCredentials credentials = new BasicAWSCredentials( PropertyLoader.getInstance().getAccessKey(), PropertyLoader.getInstance().getSecretKey() );
		    
		    sdbClient = new AmazonSimpleDBClient( credentials );
		    sdbClient.setRegion(region);
        }
    }
    
    public void clearClients() {
        sdbClient = null; 
    }
}