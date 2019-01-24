package com.example.bestever.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class userListActivity extends AppCompatActivity {

    ListView userListView;

    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User List");


        userListView = (ListView)findViewById(R.id.userListView);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext() , ChatActivity.class);
                intent.putExtra("username" , users.get(i));

                startActivity(intent);
            }
        });

        users.clear();
        adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , users);

        userListView.setAdapter(adapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        for(ParseUser user : objects){
                            users.add(user.getUsername());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }
}
