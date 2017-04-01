/**
 * Project: lab10_ChatApp
 * File: ChatApplication.java
 * Date: Mar 17, 2017
 * Time: 2:20:59 PM
 */
package ca.bcit.comp4656.jms.pubsub.chatapp;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TopicConnection;

import ca.bcit.comp4656.jms.admin.JmsUtil;

/**
 * @author Siamak Pourian
 *
 * ChatApplication Class - Subscriber for the JMS topic
 */
public class ChatApplication implements MessageListener {
	
	private JmsUtil util;
	
	private String clientId;
	private String user;
	private String carrier;
	
	/**
	 * Overloaded constructor
	 * 
	 * @param args the command line params typed by the user
	 */
    public ChatApplication(String[] args) {
    	try {	
    		getArgs(args);
    		util = new JmsUtil();
    		TopicConnection connection = util.getTopicConnection();
			connection.setClientID(clientId);
			util.setTopicConnection(connection);
			connection.start();
			util.setTopicSession(connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE));
			util.setTopic(util.getTopic(carrier));
			new Thread(new Publisher(clientId, user, util)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args, contains client ID, subscription name and text message
	 */
	public static void main(String[] args) {
		ChatApplication chat = new ChatApplication(args);
		chat.waitForMessage();
	}
	
	/**
	 * Sets the user input to the corresponding param
	 * 
	 * @param args the array containing the command line user inputs
	 */
	public void getArgs(String[] args) {
		this.clientId = args[0];
		this.user = args[1];
		if(null != args[2] && args[2].trim().equals("")) {
			this.carrier = "jms/topic/test";
		} else {
			this.carrier = args[2];	
		}
		System.out.println("clientId:" + clientId + " to:" + user + " topic:" + carrier);
	}
	
	/**
	 * Listens for the published messages on the topic infinitely
	 */
	public void waitForMessage() {
		try {
			util.setTopicSubscriber(util.getTopicSession().createSubscriber(util.getTopic(), "user='" + clientId + "'", true));
			util.getTopicSubscriber().setMessageListener(this);

			// Keeps listening for some minutes / ever
			for(int i = 0; i < i+1; i++) {
				Thread.sleep(1000);
			}
			System.out.println("\nDone Listening!");
			util.getTopicSubscriber().close();
		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes every time a message is published on the topic
	 * 
	 * @param message the MapMessage published on the topic
	 */
	@Override
	public void onMessage(Message message) {
		try {
			if(message != null && message instanceof MapMessage) {
        		MapMessage msg = (MapMessage) message;
        		System.out.println("\n[" + msg.getString("from") + "] " + msg.getString("message"));
        		System.out.print("\n[" + clientId + "] ");
        	}	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
