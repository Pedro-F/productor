package producerid;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
@EnableJms
/**
 * Clase que produce una entrada en la cola JBoss EAP AMQ
 * @author pedro.alonso.garcia
 *
 */
public class Producer {
	
	
	//Variables Globales
	Connection conn = null;
	String consola = "Hello World! by PA --- <BR> MicroservicioA";
	
	@RequestMapping("/")
	String home() {
		
		try {
			if (conn == null){
				conn = ProducerConnection.getConnection();
				consola += "<BR>==> CONEXION ESTABLECIDA: " + conn.toString();
			}
			else{
				sendMessage2Q();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return consola;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Producer.class, args);
	}

	
	 
//	    public static void main(String[] args) throws Exception {
//	        thread(new ProducerThread(), false);
//
//	    }
	 
//	    public static void thread(Runnable runnable, boolean daemon) {
//	        Thread brokerThread = new Thread(runnable);
//	        brokerThread.setDaemon(daemon);
//	        brokerThread.start();
//	    }
	 
    private void sendMessage2Q() {
        try {
	                
	            	
	 
            // Create a Session
                Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
                // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.HELLOW");
 
                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
                // Create a messages
            String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                TextMessage message = session.createTextMessage(text);
                
            String logMessage = "Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName();
 
                // Tell the producer to send the message
            System.out.println(logMessage);
            consola += "<BR>==> START ENVIO: " + logMessage;
            
            producer.send(message);
            
            consola += "<BR>==> **END ENVIO: " + logMessage;
 
                // Clean up
            session.close();
//	                connection.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
            consola += "<BR>###>>ERROR ENVIO: " + e.toString();
        }
    }
	    
	 
	   
}
