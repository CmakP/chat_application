package ca.bcit.comp4656.jms.admin;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Siamak Pourian
 *
 * JmsUtil Class - Sets the properties file for the JMS provider
 */
public class JmsUtil {	

	//private static final String JBOSS_CONTEXT_FILE = "jboss-context.properties";
	public static final String CONNECTION_FACTORY_JNDI = "jms/RemoteConnectionFactory";
	private static String JBOSS_INITIAL_CONTEXT_FACTORY= "org.jboss.naming.remote.client.InitialContextFactory";
	private static String JBOSS_URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";
	
	private static String PROVIDER_URL = "http-remoting://localhost:8080";
	private static String USER_NAME = "comp4656";
	private static String PASSWORD = "java";
	public static final String TOPIC = "jms/topic/test";
	
	private ConnectionFactory factory;
	private Context ctx;
	
	private TopicConnection topicConnection;
	private TopicSession topicSession;
	private TopicSubscriber topicSubscriber;
    private Topic topic;
	
    /**
     * DC 
     * 
     * @throws NamingException
     */
	public JmsUtil() throws NamingException {
//		Properties contextProps = new Properties();
//		try {
//			contextProps.load(new FileInputStream("src/" + JBOSS_CONTEXT_FILE));
//			System.out.println("Context properties file loaded...");
//		} catch (FileNotFoundException e) {
//			System.err.println("Can't find " + JBOSS_CONTEXT_FILE);
//		} catch (IOException e) {
//			System.err.println( "Problem loading " + JBOSS_CONTEXT_FILE);
//		} catch ( Exception e ) {
//			System.err.println( "something went wrong!");
//		}	
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY , JBOSS_INITIAL_CONTEXT_FACTORY);
		props.setProperty(Context.URL_PKG_PREFIXES, JBOSS_URL_PKG_PREFIXES);
		props.setProperty(Context.PROVIDER_URL, PROVIDER_URL);
		props.setProperty(Context.SECURITY_PRINCIPAL, USER_NAME);
		props.setProperty(Context.SECURITY_CREDENTIALS, PASSWORD);
		
		ctx = new InitialContext(props);
		factory = (ConnectionFactory) ctx.lookup(CONNECTION_FACTORY_JNDI);	
//		factory = (ConnectionFactory) ctx.lookup(contextProps.getProperty("CONNECTION_FACTORY_JNDI"));	
	}
	
	/**
	 * @return The topic connection factory 
	 * @throws JMSException
	 */
	public TopicConnection getTopicConnection() throws JMSException {
		//return ((TopicConnectionFactory) factory).createTopicConnection(contextProps.getProperty("USER_NAME"), contextProps.getProperty("PASSWORD"));
		return ((TopicConnectionFactory) factory).createTopicConnection( USER_NAME, PASSWORD );
	}
	
	/**
	 * @param topicName the JNDI name of the topic
	 * @return the corresponding topic to the JNDI name
	 * @throws NamingException
	 */
	public Topic getTopic(String topicName) throws NamingException {
		return (Topic) ctx.lookup(topicName);
	}
	
	/**
	 * @return the queue connection factory
	 * @throws JMSException
	 */
	public QueueConnection getQueueConnection() throws JMSException {
	//	return ((QueueConnectionFactory) factory).createQueueConnection(contextProps.getProperty("USER_NAME"), contextProps.getProperty("PASSWORD"));
		return ((QueueConnectionFactory) factory).createQueueConnection( USER_NAME, PASSWORD );
	}
	
	/**
	 * @param queueName the JNDI name of the queue
	 * @return corresponding queue to the JNDI name
	 * @throws NamingException
	 */
	public Queue getQueue(String queueName) throws NamingException {
		return (Queue) ctx.lookup(queueName);
	}
	
	/**
	 * @return the topic session
	 */
	public TopicSession getTopicSession() {
		return topicSession;
	}

	/**
	 * @param topicSession the topic session to set
	 */
	public void setTopicSession(TopicSession topicSession) {
		this.topicSession = topicSession;
	}

	/**
	 * @return the topic subscriber
	 */
	public TopicSubscriber getTopicSubscriber() {
		return topicSubscriber;
	}

	/**
	 * @param topicSubscriber the topic subscriber to set
	 */
	public void setTopicSubscriber(TopicSubscriber topicSubscriber) {
		this.topicSubscriber = topicSubscriber;
	}

	/**
	 * @param topicConnection the topic connection to set
	 */
	public void setTopicConnection(TopicConnection topicConnection) {
		this.topicConnection = topicConnection;
	}
	
	/**
	 * @return the topic reference
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * @param topic sets the topic
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
    
	/**
	 * Closes the topic session and connection to the provider
	 * 
	 * @throws JMSException
	 */
	public void disconnect() throws JMSException {

		if (topicConnection != null) {
			topicConnection.stop();
		}
		if (topicSession != null) {
			topicSession.close();
		}
		if (topicConnection != null) {
			topicConnection.close();
		}
		if (topicConnection != null) {
			topicConnection.close();
		}
	}
	
	/**
	 * Closes the subscriber
	 */
	public void closeTopicSubscriber() {
		
		if (topicSubscriber != null) {
			try {
				topicSubscriber.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
