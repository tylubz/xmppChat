package net.tylubz.chat.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tylubz.chat.R;
import net.tylubz.chat.contact_list.ContactListActivity;
import net.tylubz.chat.shared.model.Message;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

public class ChatActivity extends AppCompatActivity implements SingleDialogContract.View {

    private SingleDialogContract.Presenter dialogPresenter;

    private List<Message> msgList;

    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    private String jid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        dialogPresenter = new SingleDialogPresenter(this);
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jid = (String) extras.get(ContactListActivity.USER_NAME);
        }
        initControls();
        chatHistory = new ArrayList<>();
        adapter = new ChatAdapter(ChatActivity.this, chatHistory);
        messagesContainer.setAdapter(adapter);
    }

    private void initControls() {
        setTitle(jid);
        messagesContainer = (ListView) findViewById(R.id.message_list);
        messageET = (EditText) findViewById(R.id.editTypeText);
        sendBtn = (ImageButton) findViewById(R.id.floatingActionButton);

        sendBtn.setOnClickListener(v -> {
            String messageText = messageET.getText().toString();
            if (TextUtils.isEmpty(messageText)) {
                return;
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(122);//dummy
            chatMessage.setMessage(messageText);
            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage.setMe(true);
            dialogPresenter.sendMessage(jid, new Message(messageText));
            messageET.setText("");
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

    public void obButtonClick(View view) {
        onButtonClick();
    }

    @Override
    public void onButtonClick() {

    }

    @Override
    public void onMessageReceive(Message message) {
        runOnUiThread(() -> {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(131);//dummy
            chatMessage.setMessage(message.getMessage());
            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage.setMe(false);
            chatHistory.add(chatMessage);
            adapter.notifyDataSetChanged();
        });
//        scroll();
    }

    @Override
    public void setPresenter(@NonNull SingleDialogContract.Presenter presenter) {
        dialogPresenter = presenter;
    }
}