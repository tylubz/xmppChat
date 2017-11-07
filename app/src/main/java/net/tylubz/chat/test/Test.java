package net.tylubz.chat.test;

import net.tylubz.chat.configuration.ConfigLoader;
import net.tylubz.chat.configuration.Property;

import org.jivesoftware.smack.*;
//import org.jivesoftware.smack.AbstractXMPPConnection;
//import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;
//import org.jivesoftware.smack.tcp.XMPPTCPConnection;
//import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.Properties;

public class Test {

    public static void main(String[] args) throws IOException, XMPPException {

        final ConfigLoader configLoader = ConfigLoader.getInstance();
        final Properties properties = configLoader.getProperties();

        ConnectionConfiguration config = new ConnectionConfiguration(properties.getProperty(Property.HOST_NAME),
//                Integer.valueOf(properties.getProperty(Property.PORT_NUMBER)),
                Property.PORT_NUMBER,
                properties.getProperty(Property.SERVICE_NAME));

        // Very important string!
        SASLAuthentication.supportSASLMechanism("PLAIN");
        XMPPConnection connection = new XMPPConnection(config);
        connection.connect();
        connection.login(properties.getProperty(Property.USER_NAME), properties.getProperty(Property.PASSWORD));
        Chat chat = connection.getChatManager().createChat(properties.getProperty(Property.RECIPIENT_NAME), new MessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message: " + message);
            }
        });

        chat.sendMessage("Hello world");
    }
}
