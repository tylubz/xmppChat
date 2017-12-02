package net.tylubz.chat.contact_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.tylubz.chat.R;
import net.tylubz.chat.dialog.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
    private List<DummyContent> list = new ArrayList<>();

    private ListView countriesList;
    private Button createChatButton;
    private Button cancelChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        createChatButton = (Button) findViewById(R.id.createChatButton);
        cancelChatButton = (Button) findViewById(R.id.cancelChatButton);

        countriesList = (ListView) findViewById(R.id.countriesList);

        // create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);

        countriesList.setAdapter(adapter);

        // add listener
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TODO extend
                String selectedItem = countries[i];
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    public boolean addParticipants(MenuItem item) {
        Log.i("info", "Item has been selected");
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, countries);
        createChatButton.setVisibility(View.VISIBLE);
        cancelChatButton.setVisibility(View.VISIBLE);
        countriesList.setAdapter(adapter);
        return true;
    }

    public void onCancelButtonClick(View view) {
        Log.i("info", "Cancel button has been pressed");
        createChatButton.setVisibility(View.INVISIBLE);
        cancelChatButton.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);
        countriesList.setAdapter(adapter);
    }

    public void onCreateButtonClick(View view) {
        Log.i("info", "Create button has been pressed");
    }

}
