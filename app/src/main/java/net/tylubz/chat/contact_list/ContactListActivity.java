package net.tylubz.chat.contact_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.tylubz.chat.R;
import net.tylubz.chat.dialog.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
    List<DummyContent> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ListView countriesList = (ListView) findViewById(R.id.countriesList);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);

        // устанавливаем для списка адаптер
        countriesList.setAdapter(adapter);

    }
}
