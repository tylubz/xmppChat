package net.tylubz.chat.activities;

import android.os.Bundle;
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
import net.tylubz.chat.shared.model.Message;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements SingleDialogContract.View {

    private SingleDialogContract.Presenter dialogPresenter;

    private List<Message> msgList;

    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        dialogPresenter = new SingleDialogPresenter(this);
        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.message_list);
        messageET = (EditText) findViewById(R.id.editTypeText);
        sendBtn = (ImageButton) findViewById(R.id.floatingActionButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("My Buddy");// Hard Coded
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                dialogPresenter.sendMessage("golub578@jabber.ru", new Message(messageText));
                messageET.setText("");

                displayMessage(chatMessage);
            }
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

    private void loadDummyHistory() {

        chatHistory = new ArrayList<>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatActivity.this, chatHistory);
        messagesContainer.setAdapter(adapter);

//        for(int i=0; i<chatHistory.size(); i++) {
//            ChatMessage message = chatHistory.get(i);
//            displayMessage(message);
//        }
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
            chatMessage.setId(122);//dummy
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