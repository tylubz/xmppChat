package net.tylubz.chat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.tylubz.chat.R;

import org.jivesoftware.smack.SmackException;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

/**
 * Represents main activity
 *
 * @author Sergei Lebedev
 */
public class MainActivity extends AppCompatActivity {

    //    xmpp service
//    final ConfigLoader configLoader = ConfigLoader.getInstance();
//    final Properties properties = configLoader.getProperties();

    private XmppServiceTask xmppService;

    //    ui components
    private Button sendButton;
    private EditText editText;
    private TextView textView;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        xmppService = new XmppServiceTask(textView);
        xmppService.execute();

//        textView.setLis
    }

    /**
     * Send message and updates view information
     *
     * @param view current view
     */
    public void onButtonClick(View view) {
        Editable editable = editText.getText();
        try {
            xmppService.sendMessage(editable.toString());
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView.append(editText.getText() + "\n");
        editable.clear();
    }
}





