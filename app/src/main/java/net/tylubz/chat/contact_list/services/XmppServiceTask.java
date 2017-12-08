package net.tylubz.chat.contact_list.services;

import android.os.AsyncTask;
import android.util.Log;

import net.tylubz.chat.configuration.Property;
import net.tylubz.chat.contact_list.model.Contact;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.sasl.core.SCRAMSHA1Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a service for interaction
 * via xmpp protocol in a separate thread
 *
 * @author Sergei Lebedev
 */
public class XmppServiceTask extends AsyncTask<Void, Void, Void> {

    private AbstractXMPPConnection connection;

    private MessageListener messageListener;

    public XmppServiceTask() {}

    public XmppServiceTask(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
//        TODO extend exception processing
        try {
            establishConnection();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
//        TODO close connection
        super.onPostExecute(result);
    }

    /**
     * Creates a connection to a server
     *
     * @throws XMPPException if problem occurs with processing connection
     * @throws IOException if problem occurs with processing domain name
     * @throws InterruptedException if problem occurs with processing connection
     * @throws SmackException if problem occurs with processing connection
     */
    public void establishConnection() throws XMPPException, IOException, InterruptedException, SmackException {
        final String hostName = "jabber.ru";
        final String xmppDomainName = "jabber.ru";
        final int portNumber = 5222;
//        String algorithm = null;
//        try {
//            String algorithm = KeyManagerFactory.getInstance("SunX509").getAlgorithm();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(Property.USER_NAME, Property.PASSWORD)
                .setHost(hostName)
                .setXmppDomain(JidCreate.domainBareFrom(xmppDomainName))
                .setPort(portNumber)
//                    TODO adjust to secure way
                .setKeystoreType(null)
//                    .setSecurityMode(ConnectionConfiguration.SecurityMode.required) // Do not disable TLS except for test purposes!
                .setDebuggerEnabled(true)
                .build();

//        add SCRAM-SHA-1 mechanism for interaction
        SASLAuthentication.registerSASLMechanism(new SCRAMSHA1Mechanism());

        connection = new XMPPTCPConnection(config);
        connection.connect().login();


        ChatManager.getInstanceFor(connection)
                .addIncomingListener(new IncomingChatMessageListener() {
                    @Override
                    public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                        messageListener.push(new net.tylubz.chat.contact_list.model.Message(message.getBody()));
                    }
                });
    }


    /**
     * Sends a message to specified user
     *
     * @param jid jid of the user
     * @param message message
     *
     * @throws XmppStringprepException if error occurs when performing a particular Stringprep
     * profile on a String
     * @throws SmackException.NotConnectedException if cannot establish connection
     * to a specified user
     * @throws InterruptedException if something goes wrong
     */
    public void sendMessage(String jid, String message) throws XmppStringprepException, SmackException.NotConnectedException, InterruptedException {
        EntityBareJid bareJid = JidCreate.entityBareFrom(jid);
        ChatManager.getInstanceFor(connection)
                .chatWith(bareJid)
                .send(message);
    }

    //    TODO remove after testing
    public void sendMessage(String message)  {
        final String jid = "golub578@jabber.ru";
        try {
            EntityBareJid bareJid = JidCreate.entityBareFrom(jid);
            ChatManager.getInstanceFor(connection)
                    .chatWith(bareJid)
                    .send(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file) {
        if (!file.exists()) {
            Log.e("EROR", "file doesn't exist");
        }
        final String jid = "golub578@jabber.ru";
        EntityFullJid entityFullJid = null;
        try {
            BareJid bareJid = JidCreate.bareFrom(jid);
            entityFullJid = JidCreate.fullFrom(bareJid.asEntityBareJidIfPossible(), Resourcepart.EMPTY);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        // Create the file transfer manager
        FileTransferManager manager = FileTransferManager.getInstanceFor(connection);
        // Create the outgoing file transfer
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(entityFullJid);
        // Send the file
        try {
            transfer.sendFile(file, "You won't believe this!");
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }


    public List<Contact> getContactList() {
        Roster roster = Roster.getInstanceFor(connection);
        if (!roster.isLoaded())
            try {
                roster.reloadAndWait();
            } catch (SmackException.NotLoggedInException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        Collection<RosterEntry> entries = roster.getEntries();
        return toContactList(entries, roster);
    }

    private List<Contact> toContactList(Collection<RosterEntry> entries, Roster roster) {
        List<Contact> contactList = new ArrayList<>();
        for (RosterEntry entry: entries) {
            Presence presence = roster.getPresence(entry.getJid());
            String userName = entry.getJid().getLocalpartOrNull() + "@" + entry.getJid().getDomain();
            contactList.add(new Contact(userName, presence.getMode().toString()));
        }
        return contactList;
    }

    public AbstractXMPPConnection getConnection() {
        return connection;
    }

    public void setConnection(AbstractXMPPConnection connection) {
        this.connection = connection;
    }

    public void closeConnection() {connection.disconnect();}
}