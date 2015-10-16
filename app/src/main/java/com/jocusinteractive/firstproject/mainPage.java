package com.jocusinteractive.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


import java.net.URL;

public class mainPage extends AppCompatActivity {


    private TextView label;
    private Button button;
    private ListView listView;
    public static ArrayList<Location> listOfLocations= new ArrayList<>();
    private TableViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        label = (TextView) findViewById(R.id.countLabel);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);

        adapter = new TableViewAdapter(getApplicationContext(), R.layout.table_view_cell);
        listView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.backButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapButtonPressed(view);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getButtonPressed(view);
                }catch (Exception e){
                    //I dont care
                }

            }
        });


        updateNumberOfItemsLabel();


        //creating some test objects

        listOfLocations.add(new Location("house", 0.0, 0.0, 0.0));
        listOfLocations.add(new Location("work", 53.0, 16.0, 12.0));
        listOfLocations.add(new Location("park", 19.0, 64.0, 10.0));
        listOfLocations.add(new Location("Zoo?", -5.0, -10.0, 12.0));

        adapter.addAll(listOfLocations);
        updateNumberOfItemsLabel();




    }




    private void getButtonPressed(View view) throws Exception{

        Snackbar.make(view, "ButtonPressed", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        String urlStr = "http://192.168.10.40:8080/qpe/getTagPosition?version=2&humanReadable=true&maxAge=5000";

        try {









        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, "Json Failed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


        }


    }

    private void mapButtonPressed(View view){
        if(listOfLocations.size() == 0){
            Snackbar.make(view, "Go get locations first!!!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{

            Intent intent = new Intent(this, MapsActivity.class);
            //intent.putExtra("listOfLocations", listOfLocations);
            startActivity(intent);

        }

    }


    private void updateNumberOfItemsLabel(){
        label.setText(listOfLocations.size() + " locations gone get got");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
