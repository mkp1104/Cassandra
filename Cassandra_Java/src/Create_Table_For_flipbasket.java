

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class Create_Table_For_flipbasket {

   public static void main(String args[]){

      //Query
      String query = "CREATE TABLE users(user_id int PRIMARY KEY, "
         + "password text, "
         + "first_name text, "
         + "last_name text, "
         + "address_1 text, "
         + "address_2 text, "
         + "city text, "
         + "phone text, "
         + "created_on timestamp, "
         + "modified_on timestamp );";
		
      //Creating Cluster object
      //Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
      Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
    		  .withCredentials("cassandra", "cassandra").build();
      //Creating Session object
      Session session = cluster.connect("flipbasket");
 
      //Executing the query
      session.execute(query);
 
      System.out.println("Table created");
   }
}