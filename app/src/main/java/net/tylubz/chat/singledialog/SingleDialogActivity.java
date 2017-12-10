package net.tylubz.chat.singledialog;

import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import net.tylubz.chat.R;
import net.tylubz.chat.contact_list.ContactListActivity;
import net.tylubz.chat.singledialog.adapters.MessageAdapter;
import net.tylubz.chat.singledialog.model.Message;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Represents main activity
 *
 * @author Sergei Lebedev
 */
public class SingleDialogActivity extends AppCompatActivity implements SingleDialogContract.View {

    private static final String DELIMITER = "\n";

    private SingleDialogContract.Presenter dialogPresenter;

    private List<Message> msgList;

    private ListView listView;
    private MessageAdapter messageAdapter;

    //    ui components
    private Button sendButton;
    private EditText editText;
    private EditText editTypeText;

    private String jid;

    public SingleDialogActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dialog);
        editTypeText = findViewById(R.id.editTypeText);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);

        //        TODO Remove after migrating to RXJava
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jid = (String) extras.get(ContactListActivity.USER_NAME);
            editText.setText(jid.replace("@jabber.ru", "") + " (online)");
        }

        listView = (ListView)findViewById(R.id.message_list);

        msgList = new ArrayList<>();
        msgList.add(new Message("Hello!", "golub", "23:55"));
        msgList.add(new Message("Hi!", "suslik", "23:56"));
        messageAdapter = new MessageAdapter(msgList, getApplicationContext());

        listView.setAdapter(messageAdapter);
        dialogPresenter = new SingleDialogPresenter(this);
    }

    @Override
    public void setPresenter(@NonNull SingleDialogContract.Presenter presenter) {
        dialogPresenter = presenter;
    }

    /**
     * Sends message and updates view information
     *
     * @param view current view
     */
    public void onButtonClick(View view) {
        onButtonClick();
    }


    public static File sendFile(Context context) throws IOException {
        File cacheFile = new File(context.getCacheDir(), "file.txt");
        try {
//            File file = context.getFileStreamPath("raw/file.txt");
//            InputStream inputStream = context.getAssets().open("raw/file.txt");
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
//                    byte[] buf = new byte[1024];
                    byte[] buf =  "TestText 123".getBytes(Charset.forName("UTF-8"));
//                    String.
                    int len;
//                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, buf.length);
//                    }
                } finally {
                    outputStream.close();
                }
            } finally {
//                inputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("Could not open robot png", e);
        }
        return cacheFile;
    }

    public void onFileButtonClick(View view) {
        try {
            File file = sendFile(view.getContext());
            InputStream inputStream = view.getContext().getResources().openRawResource(R.raw.file);
            int size = inputStream.available();
            dialogPresenter.sendFile(file, inputStream, size);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onButtonClick() {
        Editable editable = editTypeText.getText();
        msgList.add(new Message(editable.toString(), "me", new SimpleDateFormat("HH:mm:ss").format(new Date())));
        messageAdapter.notifyDataSetChanged();
//        TODO extend logic for catching errors
        dialogPresenter.sendMessage(jid, new Message(editable.toString()));
        editable.clear();
    }

    @Override
    public void onMessageReceive(Message message) {
        msgList.add(new Message(message.getMessage(), "from", new SimpleDateFormat("HH:mm:ss").format(new Date())));
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        dialogPresenter.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        dialogPresenter.stop();
        super.onDestroy();
    }
}





