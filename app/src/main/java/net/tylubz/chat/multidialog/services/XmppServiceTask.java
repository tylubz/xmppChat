package net.tylubz.chat.multidialog.services;

import android.os.AsyncTask;

import net.tylubz.chat.configuration.Property;
import net.tylubz.chat.multidialog.MessageListener;
import net.tylubz.chat.multidialog.ResultListener;
import net.tylubz.chat.shared.model.JidContact;
import net.tylubz.chat.shared.utils.ModelUtils;

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
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

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

    private MultiUserChat muc;

    private ResultListener resultListener;

//    private TextView view;

    public XmppServiceTask() {}

    public XmppServiceTask(MessageListener messageListener, ResultListener resultListener) {
        this.messageListener = messageListener;
        this.resultListener = resultListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
//        TODO extend exception processing
            establishConnection();
            return null;
//        return getContactList();
    }

    @Override
    protected void onPostExecute(Void result) {
//        TODO close connection
        super.onPostExecute(result);
//        resultListener.pushResult(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        publishProgress();
    }

    /**
     * Creates a connection to a server
     *
     * @throws XMPPException if problem occurs with processing connection
     * @throws IOException if problem occurs with processing domain name
     * @throws InterruptedException if problem occurs with processing connection
     * @throws SmackException if problem occurs with processing connection
     */
    public void establishConnection()  {
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(Property.USER_NAME, Property.PASSWORD)
                    .setHost(Property.HOST_NAME)
                    .setXmppDomain(JidCreate.domainBareFrom(Property.XMPP_DOMAIN_NAME))
                    .setPort(Property.PORT_NUMBER)
//                    TODO adjust to secure way
                    .setKeystoreType(null)
//                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // Do not disable TLS except for test purposes!
                    .setDebuggerEnabled(true)
                    .build();

//        add SCRAM-SHA-1 mechanism for interaction
            SASLAuthentication.registerSASLMechanism(new SCRAMSHA1Mechanism());

            connection = new XMPPTCPConnection(config);
            connection.connect().login();
        }catch (Exception e) {
            e.printStackTrace();
            //Do nothing
        }


//        ChatManager.getInstanceFor(connection)
//                .addIncomingListener(new IncomingChatMessageListener() {
//                    @Override
//                    public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
//                        messageListener.push(new net.tylubz.chat.shared.model.Message(message.getBody()));
//                    }
//                });
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
        try {
            muc.sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        final String jid = "golub578@jabber.ru";
//        try {
//            EntityBareJid bareJid = JidCreate.entityBareFrom(jid);
//            ChatManager.getInstanceFor(connection)
//                    .chatWith(bareJid)
//                    .send(message);
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (XmppStringprepException e) {
//            e.printStackTrace();
//        }
    }

    public void createGroupChat(List<JidContact> participantList) {
        // Create the nickname.
        Resourcepart nickname = null;
        try {
            nickname = Resourcepart.from("suslik578@jabber.ru");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        // Create the XMPP address (JID) of the MUC.
        MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
        final String jid = "myroom@conference.jabber.ru";

        EntityBareJid bareJid = null;
        try {
            bareJid = JidCreate.entityBareFrom(jid);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        // Create a MultiUserChat using an XMPPConnection for a room
        muc = manager.getMultiUserChat(bareJid);

        try {
//            muc.create(nickname).makeInstant();
            muc.createOrJoin(nickname);
//            .makeInstant();
//            muc.createOrJoin(nickname);
            for(JidContact jidContact : participantList) {
                muc.invite(JidCreate.entityBareFrom(jidContact.getJid()), "Please join to chat" + nickname);
            }
            muc.addMessageListener(new org.jivesoftware.smack.MessageListener() {
                @Override
                public void processMessage(Message message) {
                    messageListener.push(new net.tylubz.chat.shared.model.Message(message.getBody()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<JidContact> toContactList(Collection<RosterEntry> entries, Roster roster) {
        List<JidContact> jidContactList = new ArrayList<>();
        for (RosterEntry entry: entries) {
            Presence presence = roster.getPresence(entry.getJid());
            String userName = entry.getJid().getLocalpartOrNull() + "@" + entry.getJid().getDomain();
            jidContactList.add(new JidContact(userName, presence.getMode().toString()));
        }
        return jidContactList;
    }


    public AbstractXMPPConnection getConnection() {
        return connection;
    }

    public void setConnection(AbstractXMPPConnection connection) {
        this.connection = connection;
    }

    public void closeConnection() {connection.disconnect();}
}