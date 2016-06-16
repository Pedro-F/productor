package producerid;

import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnectionFactory;

  class ProducerConnection /*implements Runnable*/{
	//public void run() {
    
	
	
	public static Connection getConnection(){
		Connection connection = null;
		try {
            
        	// Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.30.217.249:61616");

            // Create a Connection
            connection = connectionFactory.createConnection("user","password");
            connection.start();
            
            
		}
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
		return connection;
    }

	
}
