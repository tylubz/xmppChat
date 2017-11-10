package net.tylubz.chat.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.tylubz.chat.R;
import net.tylubz.chat.configuration.ConfigLoader;
import net.tylubz.chat.configuration.Property;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.core.SCRAMSHA1Mechanism;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    //    xmpp service
//    final ConfigLoader configLoader = ConfigLoader.getInstance();
//    final Properties properties = configLoader.getProperties();

    //    ui components
    private Button sendButton;
    private EditText editText;
    private TextView textView;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new MyTask().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }


    public void onButtonClick(View view) {
        textView.append(editText.getText());
    }



}

class MyTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            establishConnection();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    private void establishConnection() throws XMPPException {

        XMPPTCPConnectionConfiguration config = null;
        try {
            config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(Property.USER_NAME, Property.PASSWORD)
                    .setHost("jabber.ru")
                    .setXmppDomain(JidCreate.domainBareFrom("jabber.ru"))
                    .setPort(5222)
//                    TODO adjust to secure way
                    .setKeystoreType(null)
//                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // Do not disable TLS except for test purposes!
                    .setDebuggerEnabled(true)
                    .build();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

//        add SCRAM-SHA-1 mechanism for interaction
        SASLAuthentication.registerSASLMechanism(new SCRAMSHA1Mechanism());

        AbstractXMPPConnection connection;
        connection = new XMPPTCPConnection(config);
        try {
            connection.connect().login();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ChatManager chatManager = ChatManager.getInstanceFor(connection);

        EntityBareJid jid = null;
        try {
            jid = JidCreate.entityBareFrom("golub578@jabber.ru");
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        Chat chat = chatManager.chatWith(jid); // pass XmppClient instance as listener for received messages.
        try {
            chat.send("test");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}





