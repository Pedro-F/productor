package producerid;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * Hello world!
 */
public class producer {
	
	
	 
	    public static void main(String[] args) throws Exception {
	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        Thread.sleep(1000);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        Thread.sleep(1000);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        Thread.sleep(1000);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldConsumer(), false);
//	        thread(new HelloWorldProducer(), false);
	    }
	 
	    public static void thread(Runnable runnable, boolean daemon) {
	        Thread brokerThread = new Thread(runnable);
	        brokerThread.setDaemon(daemon);
	        brokerThread.start();
	    }
	 
	    public static class HelloWorldProducer implements Runnable {
	        public void run() {
	            try {
	                // Create a ConnectionFactory
	                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("172.30.190.28");
	 
	                // Create a Connection
	                Connection connection = connectionFactory.createConnection();
	                connection.start();
	 
	                // Create a Session
	                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	 
	                // Create the destination (Topic or Queue)
	                Destination destination = session.createQueue("TEST.FOO");
	 
	                // Create a MessageProducer from the Session to the Topic or Queue
	                MessageProducer producer = session.createProducer(destination);
	                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	 
	                // Create a messages
	                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
	                TextMessage message = session.createTextMessage(text);
	 
	                // Tell the producer to send the message
	                System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
	                producer.send(message);
	 
	                // Clean up
	                session.close();
	                connection.close();
	            }
	            catch (Exception e) {
	                System.out.println("Caught: " + e);
	                e.printStackTrace();
	            }
	        }
	    }
	 
	   
}
