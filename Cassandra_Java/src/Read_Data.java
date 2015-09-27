
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;

public class Read_Data {

   public static void main(String args[])throws Exception{
    
      //queries
      String query = "SELECT * FROM emp";

      //Creating Cluster object
      //Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
//      Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
//    		  .withCredentials("cassandra", "cassandra").build();
      Cluster cluster = Cluster.builder()
		.addContactPoints("127.0.0.1")
		//Retry if we get a read/write timeout; CL downgrade policy can be an issue
		.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
		//Wait exponentially 5 min (1000*60*5) ms base; max 1hr; trying to connect to a dead node
		.withReconnectionPolicy(new ExponentialReconnectionPolicy(300000, 3600000))
		//Do round robin in one DC only, inefficient if all nodes are considered
		//.withLoadBalancingPolicy(new DCAwareRoundRobinPolicy("DC1"))
		//Required if your cluster has authentication enabled
		.withCredentials("cassandra", "cassandra")
		//Compress the payload as supported by the binary protocol (LZ4, SNAPPY or NONE)
		//.withCompression(Compression.LZ4)
		.build();
      //Creating Session object
      Session session = cluster.connect("tp");
    
      //Getting the ResultSet
      ResultSet result = session.execute(query);
    
      System.out.println(result.all());
   }
}