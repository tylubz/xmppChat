package net.tylubz.chat.multidialog;

import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import net.tylubz.chat.R;
import net.tylubz.chat.activities.ChatActivity;
import net.tylubz.chat.activities.ChatMessage;
import net.tylubz.chat.contact_list.ContactListActivity;
import net.tylubz.chat.shared.model.JidContact;
import net.tylubz.chat.shared.model.Message;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultiDialogActivity extends AppCompatActivity implements MultiDialogContract.View{

    private static final String DELIMITER = "\n";

    private MultiDialogContract.Presenter dialogPresenter;

    //    ui components
    private ImageButton sendButton;
    private EditText editText;
//    private TextView textView;

    private ListView messagesContainer;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multichat_layout);
        editText = findViewById(R.id.editTypeText);
        sendButton = findViewById(R.id.floatingActionButton);
        messagesContainer = (ListView) findViewById(R.id.message_list);

        Bundle extras = getIntent().getExtras();
        List<JidContact> jidContactList = new ArrayList<>();
        if (extras != null) {
            List<String> list = (ArrayList<String>) extras.get(ContactListActivity.ITEM_LIST);
            for(int i=0; i<list.size(); i++) {
                jidContactList.add(new JidContact(list.get(i), "unknown"));
            }
        }
//        TODO REMOVE after moving to RXJava
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initControls(jidContactList);
        chatHistory = new ArrayList<>();
        adapter = new ChatAdapter(MultiDialogActivity.this, chatHistory);
        messagesContainer.setAdapter(adapter);
        dialogPresenter = new MultiDialogPresenter(this);
        dialogPresenter.createGroupChat(jidContactList);
    }

    private void initControls(List<JidContact> jidContactList) {
        sendButton.setOnClickListener(v -> {
            String messageText = editText.getText().toString();
            if (TextUtils.isEmpty(messageText)) {
                return;
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(131);//dummy
            chatMessage.setUserName("me");
            chatMessage.setMessage(messageText);
            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage.setMe(true);
            dialogPresenter.sendMessage(new Message(messageText));
            editText.setText("");
            displayMessage(chatMessage);
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
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
//        onButtonClick();
    }

    @Override
    public void onButtonClick() {
    }

    @Override
    public void onMessageReceive(Message message) {
        runOnUiThread(() -> {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(131);//dummy
            chatMessage.setUserName(message.getUserName());
            chatMessage.setMessage(message.getMessage());
            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage.setMe(false);
            chatHistory.add(chatMessage);
            adapter.notifyDataSetChanged();
        });
//        textView.append(message.getMessage() + DELIMITER);
    }

    @Override
    public void onContactListReceive(List<JidContact> jidContactList) {

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
