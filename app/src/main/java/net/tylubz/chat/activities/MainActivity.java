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

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLPlainMechanism;

import java.io.IOException;
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
        ConnectionConfiguration config = new ConnectionConfiguration(Property.HOST_NAME,
                5222,
                Property.SERVICE_NAME);

        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setSocketFactory(new NoSSLv3SocketFactory());
//        config.setSASLAuthenticationEnabled(false);
        config.setServiceName("jabber.ru");

        // Внимание! Следующая строчка очень важна!
//        SASLAuthentication.supportSASLMechanism("PLAIN");
        XMPPConnection connection = new XMPPConnection(config);

        connection.connect();
        connection.login(Property.USER_NAME, Property.PASSWORD); // т.е. не login@jabber.ru, а просто login

        Chat chat = connection.getChatManager().createChat(Property.RECIPIENT_NAME, new MessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message: " + message);
            }
        });
        chat.sendMessage("Hello world");
    }
}





