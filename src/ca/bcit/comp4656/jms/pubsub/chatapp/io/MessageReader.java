package ca.bcit.comp4656.jms.pubsub.chatapp.io;

import java.util.Scanner;

import ca.bcit.comp4656.jms.pubsub.chatapp.ExitFlag;

/**
 * @author Siamak Pourian
 * @version Mar 27, 2016
 * 
 * MessageReader Class - Reads the user input from command line
 *
 */
    public class MessageReader {

        private Scanner scanner;

        /**
         * Constructor for objects of type InputReader.
         */
        public MessageReader() {
            scanner = new Scanner(System.in);
        }

        /**
         * This method returns the text message typed by the user.
         * 
         * @return Test Message as String
         * @throws ExitFlag 
         */
        public String getTypedMessage() throws ExitFlag {
            String textMessage = scanner.nextLine().trim();
            
            if(textMessage.equalsIgnoreCase("_help")) {
                System.out.println("--> To send messages, type in your message and press Enter.");
                System.out.println("--> To end the chat, type 'Exit' and press Enter.");
                System.out.println("--> For a list of available commands type in '_commands' and press Enter.");
                System.out.println("--> For details on how to enter the commandline data, type '_format' and press Enter.");
            } else if(textMessage.equalsIgnoreCase("exit")) {
            	throw new ExitFlag("Leaving the chat for now : )\nCheers!");
            } else if(textMessage.trim().equalsIgnoreCase("_commands")) {
            	System.out.println("Chat-App Commands:");
            	System.out.println("    FORMAT\n    COMMANDS\n    HELP\n    EXIT\n");
            } else if(textMessage.equalsIgnoreCase("_format")) {
               	System.out.println("Commandline data-format:");
               	System.out.println("[CLIENT_ID] [NAME_OF_MESSAGE_RECEIVER] (OPTIONAL_QUEUE_NAME)");
            } else {
            	return textMessage;
            }
            return "";
        }
    }
