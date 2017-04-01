package ca.bcit.comp4656.jms.pubsub.chatapp;
/**
 * Project: lab10_ChatApp
 * File: Publisher.java
 * Date: Mar 17, 2017
 * Time: 9:53:06 PM
 */

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.TopicPublisher;

import ca.bcit.comp4656.jms.admin.JmsUtil;
import ca.bcit.comp4656.jms.pubsub.chatapp.io.MessageReader;

/**
 * @author Siamak Pourian
 *
 * Publisher Class - Publishes the messages on the JMS topic
 */
public class Publisher implements Runnable {

	private JmsUtil util;
		
	private MessageReader reader;
	
	private String clientId, user;	
	
	/**
	 * Overloaded constructor
	 * 
	 * @param clientId the unique client id
	 * @param user the name of the user
	 * @param util the reference to the util class
	 */
	public Publisher(String clientId, String user, JmsUtil util) {

		reader = new MessageReader();
		
		this.util = util;
		this.clientId = clientId;
	    this.user = user;
	}
	
	/**
	 * Gets the typed message by the user
	 * 
	 * @return the message typed by the user
	 * @throws ExitFlag
	 */
	public String getTextMessage() throws ExitFlag {
		return reader.getTypedMessage();
	}

	/**
	 * Driver for the chat - Runs on a different thread and constantly publishes 
	 * the messages typed by each user to the JMS topic
	 * It also provides instructions for the chat appliaction
	 */
	@Override
	public void run() {
		String textMessage = "";
		
		try {
			TopicPublisher publisher = util.getTopicSession().createPublisher(util.getTopic());			
			
			MapMessage msg = util.getTopicSession().createMapMessage();
			System.out.println(">>>For instructions type '_help' and press Enter <<<\n");
			System.out.println(" Start your chat by saying hi to " + user + "!");
			do {
				try {
					System.out.print("\n[" + clientId + "] ");
					textMessage = getTextMessage();
					if(!textMessage.equals("")) {
						msg.setString("from", clientId);
						msg.setString("message", textMessage);
						msg.setStringProperty("user", user);
						publisher.publish(msg);	
						System.out.println("     sent!");
					}
				} catch (ExitFlag e) {
					System.out.println(e.getMessage());
					util.disconnect();
					System.exit(0);
		    	}	
			} while(true);
		} catch (JMSException e1) {
			e1.printStackTrace();
		} 	
	}
}
