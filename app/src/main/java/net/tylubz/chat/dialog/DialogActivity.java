package net.tylubz.chat.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.tylubz.chat.R;
import net.tylubz.chat.contact_list.ContactListActivity;
import net.tylubz.chat.dialog.model.Message;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Represents main activity
 *
 * @author Sergei Lebedev
 */
public class DialogActivity extends AppCompatActivity implements DialogContract.View {

    private static final String DELIMITER = "\n";

    private DialogContract.Presenter dialogPresenter;

    //    ui components
    private Button sendButton;
    private EditText editText;
    private EditText editTypeText;
    private TextView textView;

    private String jid;

    public DialogActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTypeText = findViewById(R.id.editTypeText);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jid = (String) extras.get(ContactListActivity.USER_NAME);
            editText.setText("Dialog with " + jid);
        }
        dialogPresenter = new DialogPresenter(this);
    }

    @Override
    public void setPresenter(@NonNull DialogContract.Presenter presenter) {
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
            dialogPresenter.sendFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onButtonClick() {
        Editable editable = editTypeText.getText();
//        TODO extend logic for catching errors
        dialogPresenter.sendMessage(jid, new Message(editable.toString()));
        textView.append(editTypeText.getText() + DELIMITER);
        editable.clear();
    }

    @Override
    public void onMessageReceive(Message message) {
        textView.append(message.getMessage() + DELIMITER);
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





