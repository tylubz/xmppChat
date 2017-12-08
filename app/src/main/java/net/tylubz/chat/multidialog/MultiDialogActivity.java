package net.tylubz.chat.multidialog;

import android.os.StrictMode;
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
import net.tylubz.chat.multidialog.model.Contact;
import net.tylubz.chat.multidialog.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MultiDialogActivity extends AppCompatActivity implements MultiDialogContract.View{

    private static final String DELIMITER = "\n";

    private MultiDialogContract.Presenter dialogPresenter;

    //    ui components
    private Button sendButton;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_dialog);
        editText = findViewById(R.id.editTypeText);
        sendButton = findViewById(R.id.sendButton);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            List<String> list = (ArrayList<String>) extras.get(ContactListActivity.ITEM_LIST);
            for(int i=0; i<list.size(); i++) {
                textView.append(list.get(i) + DELIMITER);
            }
        }
//        TODO REMOVE after moving to RXJava
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dialogPresenter = new MultiDialogPresenter(this);
    }

    @Override
    public void setPresenter(@NonNull MultiDialogContract.Presenter presenter) {
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

    @Override
    public void onButtonClick() {
        Editable editable = editText.getText();
//        TODO extend logic for catching errors
          dialogPresenter.createGroupChat();
//        dialogPresenter.sendMessage(new Message(editable.toString()));
//        textView.append(editText.getText() + DELIMITER);
//        editable.clear();
    }

    @Override
    public void onMessageReceive(Message message) {
        textView.append(message.getMessage() + DELIMITER);
    }

    @Override
    public void onContactListReceive(List<Contact> contactList) {

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
