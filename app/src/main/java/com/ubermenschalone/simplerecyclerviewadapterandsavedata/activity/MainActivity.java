package com.ubermenschalone.simplerecyclerviewadapterandsavedata.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ubermenschalone.simplerecyclerviewadapterandsavedata.R;
import com.ubermenschalone.simplerecyclerviewadapterandsavedata.adapter.SimpleAdapter;
import com.ubermenschalone.simplerecyclerviewadapterandsavedata.data.TinyDB;
import com.ubermenschalone.simplerecyclerviewadapterandsavedata.model.Person;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Person> persons;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    TinyDB tinyDB;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        persons = new ArrayList();
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        tinyDB = new TinyDB(this);


        //Get saved data
        if(tinyDB.getListObject("arrayPersons", Person.class) != null){
            ArrayList<Object> playerObjects = tinyDB.getListObject("arrayPersons", Person.class);
            for(Object objs : playerObjects){
                persons.add((Person) objs);
            }
        }



        simpleAdapter = new SimpleAdapter(this, persons);
        recyclerView.setAdapter(simpleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persons.add(new Person("Person " + simpleAdapter.getItemCount()));
                simpleAdapter.notifyItemInserted(simpleAdapter.getItemCount() - 1);
                recyclerView.scrollToPosition(simpleAdapter.getItemCount() - 1);

                //Save data
                ArrayList<Object> playerObjects = new ArrayList<>();
                for(Person a : persons){
                    playerObjects.add((Object)a);
                }
                tinyDB.putListObject("arrayPersons", playerObjects);
            }
        });



    }
}
