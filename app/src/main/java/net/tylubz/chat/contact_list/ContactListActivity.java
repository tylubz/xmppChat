package net.tylubz.chat.contact_list;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.tylubz.chat.R;
import net.tylubz.chat.activities.ChatActivity;
import net.tylubz.chat.contact_list.fragments.AddUserDialogFragment;
import net.tylubz.chat.contact_list.model.Message;
import net.tylubz.chat.multidialog.MultiDialogActivity;
import net.tylubz.chat.shared.model.JidContact;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

public class ContactListActivity extends AppCompatActivity implements ContactListContract.View, AddUserDialogFragment.OnCompleteListener {

    private ContactListContract.Presenter contactListPresenter;

    public static final String ITEM_LIST = "itemList";

    public static final String USER_NAME = "userName";

    private List<String> jidContactList = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    private ListView contactList;
    private Button createChatButton;
    private Button cancelChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        createChatButton = (Button) findViewById(R.id.createChatButton);
        cancelChatButton = (Button) findViewById(R.id.cancelChatButton);

        contactList = (ListView) findViewById(R.id.contactList);

//        TODO Remove after migrating to RXJava
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        contactListPresenter = new ContactListPresenter(this);
        List<JidContact> contactList = contactListPresenter.getContactList();

        for (JidContact contact : contactList) {
            jidContactList.add(contact.getJid());
        }

        // create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, jidContactList);

        this.contactList.setAdapter(adapter);

        // add listener
        this.contactList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(view.getContext(), ChatActivity.class);
            intent.putExtra(USER_NAME, jidContactList.get(i));
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    public boolean onMenuItemClick(MenuItem item) {
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, jidContactList);
        switch (item.getItemId()) {
            case R.id.create_group_chat:
                Log.i("info", "Item has been selected");
                createChatButton.setVisibility(View.VISIBLE);
                createChatButton.setText("Create");
                createChatButton.setOnClickListener((v) -> onCreateButtonClick());
                cancelChatButton.setVisibility(View.VISIBLE);
                contactList.setAdapter(adapter);
                contactList.setOnItemClickListener(null);
                return true;
            case R.id.add_user:
                AddUserDialogFragment dialogFragment = new AddUserDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "missiles");
                return true;
            case R.id.delete_user:
                createChatButton.setVisibility(View.VISIBLE);
                createChatButton.setText("Delete");
                createChatButton.setOnClickListener(v -> onDeleteUserButtonClick());
                cancelChatButton.setVisibility(View.VISIBLE);
                contactList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                contactList.setOnItemClickListener(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCancelButtonClick(View view) {
        Log.i("info", "Cancel button has been pressed");
        createChatButton.setVisibility(View.INVISIBLE);
        cancelChatButton.setVisibility(View.INVISIBLE);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, jidContactList);
        contactList.setAdapter(adapter);
        // add listener
        this.contactList.setOnItemClickListener((adapterView, itemView, i, l) -> {
            Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
            intent.putExtra(USER_NAME, jidContactList.get(i));
            startActivity(intent);
        });
    }

    @Override
    public void onCreateButtonClick() {
        Log.i("info", "Create button has been pressed");
        SparseBooleanArray sparseArray = contactList.getCheckedItemPositions();
        ArrayList<String> markedJids = new ArrayList<>();
        for (int i = 0; i < jidContactList.size(); i++) {
            if (sparseArray.get(i)) {
                Log.i("info", "Item " + jidContactList.get(i) + " has been selected");
                markedJids.add(jidContactList.get(i));
            }
        }
        Intent intent = new Intent(this, MultiDialogActivity.class);
        intent.putExtra(ITEM_LIST, markedJids);
        startActivity(intent);
//        TODO fix
        onCancelButtonClick(null);
    }


    @Override
    public void onDeleteUserButtonClick() {
        SparseBooleanArray sparseArray = contactList.getCheckedItemPositions();
        ArrayList<String> deleteUserList = new ArrayList<>();
        for (int i = 0; i < jidContactList.size(); i++) {
            if (sparseArray.get(i)) {
                Log.i("info", "Item " + jidContactList.get(i) + " has been selected");
                String jidContact = jidContactList.get(i);
                deleteUserList.add(jidContact);
            }
        }

        for(String jidContact : deleteUserList) {
            jidContactList.remove(jidContact);
        }
        contactListPresenter.deleteUser(deleteUserList);
        onCancelButtonClick(null);
    }

    @Override
    public void onMessageReceive(Message message) {
//        TODO add notification for incoming message
    }

    @Override
    public void onInvitationAccept(JidContact jidContact) {
        runOnUiThread(() -> {
            jidContactList.add(jidContact.getJid());
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void setPresenter(@NonNull ContactListContract.Presenter presenter) {
        contactListPresenter = presenter;
    }

    @Override
    public void onComplete(String result) {
        Log.i("mytag", "On complete method has been called. Result = " + result);
        contactListPresenter.addUser(result);
    }
}
