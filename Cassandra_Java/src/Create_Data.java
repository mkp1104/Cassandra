
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class Create_Data {

   public static void main(String args[]){

      //queries
      String query1 = "INSERT INTO emp (emp_id, emp_name, emp_city, emp_phone,  emp_sal)"
		
         + " VALUES(1,'ram', 'Hyderabad', 9848022338, 50000);" ;
                             
      String query2 = "INSERT INTO emp (emp_id, emp_name, emp_city,"+
         "emp_phone, emp_sal)"
      
         + " VALUES(2,'robin', 'Hyderabad', 9848022339, 40000);" ;
                             
      String query3 = "INSERT INTO emp (emp_id, emp_name, emp_city, emp_phone, emp_sal)"
       
         + " VALUES(3,'rahman', 'Chennai', 9848022330, 45000);" ;

      //Creating Cluster object
     // Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
      Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
    		  .withCredentials("cassandra", "cassandra").build();
      //Creating Session object
      Session session = cluster.connect("tp");
       
      //Executing the query
      session.execute(query1);
        
      session.execute(query2);
        
      session.execute(query3);
        
      System.out.println("Data created");
   }
}