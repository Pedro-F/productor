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
import org.springframework.web.bind.annotation.RequestParam;
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
	Session session = null;
	MessageProducer producer = null;
	String consola = "Hello World! by PA --- <BR> MicroservicioA";
	//long contador = 0;
	
	@RequestMapping("/")
	String home(@RequestParam(value="pNumero", defaultValue="1") String pNumero) {
		
		try {
			if (conn == null){
				init();
			}

				sendMessage2Q(pNumero);
	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{		
		}
		
		return consola;
	}

	
	private void init() {
		// TODO Auto-generated method stub
		
		conn = ProducerConnection.getConnection();
		consola += "<BR>==> CONEXION ESTABLECIDA: " + conn.toString();
		
		try{
			// Create a Session
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.MECCANO");

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		 }
	    catch (Exception e) {
        System.out.println("Init Caught: " + e);
        e.printStackTrace();
 
    }
	}


	/*******************************************
	 * MAIN                                    *
	 * @param args                             *
	 * @throws Exception  
	 * @author pedro.alonso.garcia                     *
	 ******************************************/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Producer.class, args);
	}

	
	/**
	 * Método que envía el mensaje 
	 * @param idMessage
	 * @author pedro.alonso.garcia
	 */
    private void sendMessage2Q(String idMessage) {
        try {         
            // Create a messages
            String text = "Hello world! From: " + idMessage + " %%%%%%%%";
            TextMessage message = session.createTextMessage(text);
                
            String logMessage = "@@@ "+ idMessage + " @@@ -- Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName();
 
            // Tell the producer to send the message
            System.out.println(logMessage);
            consola += "<BR>==> START ENVIO: " + logMessage;
            
            producer.send(message);
             
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
            consola += "<BR>###>>ERROR ENVIO: @@@ "+ idMessage + " @@@ -- " + e.toString();
        }
    }
	    
	 
	   
}
