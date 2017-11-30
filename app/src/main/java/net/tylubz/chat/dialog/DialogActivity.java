package net.tylubz.chat.dialog;

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
import net.tylubz.chat.dialog.model.Message;


import java.io.IOException;

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
    private TextView textView;

    public DialogActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

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

    @Override
    public void onButtonClick() {
        Editable editable = editText.getText();
//        TODO extend logic for catching errors
        dialogPresenter.sendMessage(new Message(editable.toString()));
        textView.append(editText.getText() + DELIMITER);
        editable.clear();
    }

    @Override
    public void onMessageReceive(Message message) {
        textView.append(message.getMessage() + DELIMITER);
    }
}





