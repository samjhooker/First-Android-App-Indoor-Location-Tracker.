package com.jocusinteractive.firstproject;

import android.content.Intent;
import android.os.AsyncTask;
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


import org.json.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;


import java.net.URL;

public class mainPage extends AppCompatActivity {


    static TextView label;
    private Button button;
    private ListView listView;
    public static ArrayList<Location> listOfLocations= new ArrayList<>();
    static TableViewAdapter adapter;
    static String json;
    static View theView;


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
                try {
                    getButtonPressed(view);
                } catch (Exception e) {
                    //I dont care
                }

            }
        });


        updateNumberOfItemsLabel();


        //creating some test objects


        updateNumberOfItemsLabel();




    }


    private static class getRestAPI extends AsyncTask<Void, Void, Void>{

        static String jsonStr;
        String data = "";


        @Override
        protected Void doInBackground(Void... params) {
            BufferedReader br = null;


            try {


                URL url;

                String urlStr = "http://192.168.10.40:8080/qpe/getTagPosition?version=2&humanReadable=true";

                url = new URL(urlStr);




                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter outputStreamWr = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWr.write(data);
                outputStreamWr.flush();



                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = br.readLine())!=null) {
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }

                jsonStr = sb.toString();


            } catch (Exception e){
                //Really dont care
            }
            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            json = jsonStr;
            loadApiData();
        }
    }


    static void updateList(){
        adapter.addAll(listOfLocations);
        updateNumberOfItemsLabel();
    }



     static void loadApiData(){
        try{
            listOfLocations.clear();


            JSONObject obj = new JSONObject(json);

            String done = obj.getString("status");

            if (done.equals("Ok")){

                JSONArray listOfTags = obj.getJSONArray("tags");

                for (int i = 0; i < listOfTags.length(); i++){



                    JSONObject tag = (JSONObject)listOfTags.get(i);

                    String name = tag.getString("name");
                    String color = tag.getString("color");

                    JSONArray listOfCo = tag.getJSONArray("position");

                    Double x = (Double)listOfCo.get(0);
                    Double y = (Double)listOfCo.get(1);
                    Double z = (Double)listOfCo.get(2);





                    Location location = new Location(name, x, y, z, color);
                    listOfLocations.add(location);





                }

                updateList();






            }else{
                Snackbar.make(theView, "Something went got broke :(", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }





        }catch (Exception e){
            //handle it later
        }








    }

    private void getButtonPressed(View view) throws Exception{

        Snackbar.make(view, "Gone Going Getting Locations", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        theView = view;

        new getRestAPI().execute();


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


    static void updateNumberOfItemsLabel(){
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
