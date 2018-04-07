package com.madhujgowda.www.smartirrigationmonitoringsystem;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference temperatureReference = databaseReference.child("values/temperature");
    DatabaseReference humidityReference = databaseReference.child("values/humidity");
    DatabaseReference soilmoistureReference = databaseReference.child("soil_moisture");

    DatabaseReference relayswitchReference = databaseReference.child("relay_switch");

    DatabaseReference sensorsReference = databaseReference.child("sensors");

    TextView temperature_tv;
    TextView humidity_tv;
    TextView soil_moisture_tv;
    TextView sensor_tv;

    TextView temperature_tv1;
    TextView humidity_tv1;
    TextView soil_moisture_tv1;
    TextView device1;
    TextView relay_on_rb;
    TextView relay_off_rb;
    TextView sensors_tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        temperature_tv = (TextView)findViewById(R.id.temperature_tv2);
        humidity_tv = (TextView)findViewById(R.id.humidity_tv2);
        soil_moisture_tv = (TextView)findViewById(R.id.soil_moisture_tv2);
        sensor_tv = (TextView)findViewById(R.id.sensors_tv2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        //check to see if the user is currently signed in.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        temperatureReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.getValue(String.class);
                temperature_tv.setText(temperature);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        humidityReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String humidity = dataSnapshot.getValue(String.class);
                humidity_tv.setText(humidity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        soilmoistureReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String soil_moisture = dataSnapshot.getValue(String.class);
                soil_moisture_tv.setText(soil_moisture);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        relayswitchReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String relayswitch = dataSnapshot.getValue(String.class);

                if (relayswitch.equals("ON"))
                {
                    RadioButton d1rb =(RadioButton)findViewById(R.id.relay_on_rb);
                    d1rb.setChecked(true);
                }
                else
                {
                    RadioButton d1rb =(RadioButton)findViewById(R.id.relay_off_rb);
                    d1rb.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sensorsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sensor = dataSnapshot.getValue(String.class);
                sensor_tv.setText(sensor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        SharedPreferences sharedPreferences1 = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
        String language = sharedPreferences1.getString("language","");

        temperature_tv1 = (TextView)findViewById(R.id.temperature_tv1);
        temperature_tv1.setText(language);

        humidity_tv1 = (TextView)findViewById(R.id.humidity_tv1);
        humidity_tv1.setText(language);

        soil_moisture_tv1 = (TextView)findViewById(R.id.soil_moisture_tv1);
        soil_moisture_tv1.setText(language);

        device1 = (TextView)findViewById(R.id.device1);
        device1.setText(language);

        relay_on_rb = (TextView)findViewById(R.id.relay_on_rb);
        relay_on_rb.setText(language);

        relay_off_rb = (TextView)findViewById(R.id.relay_off_rb);
        relay_off_rb.setText(language);

        sensors_tv1 = (TextView)findViewById(R.id.sensors_tv1);
        sensors_tv1.setText(language);

        if (language.equals("english"))
        {
            temperature_tv1 = (TextView)findViewById(R.id.temperature_tv1);
            temperature_tv1.setText(R.string.english_temperature);

            humidity_tv1 = (TextView)findViewById(R.id.humidity_tv1);
            humidity_tv1.setText(R.string.english_humidity);

            soil_moisture_tv1 = (TextView)findViewById(R.id.soil_moisture_tv1);
            soil_moisture_tv1.setText(R.string.english_soil_moisture);

            device1 = (TextView)findViewById(R.id.device1);
            device1.setText(R.string.english_waterpump);

            relay_on_rb = (TextView)findViewById(R.id.relay_on_rb);
            relay_on_rb.setText(R.string.english_on);

            relay_off_rb = (TextView)findViewById(R.id.relay_off_rb);
            relay_off_rb.setText(R.string.english_off);

            sensors_tv1 = (TextView)findViewById(R.id.sensors_tv1);
            sensors_tv1.setText(R.string.english_sensor);
        } else if (language.equals("kannada"))
        {
            temperature_tv1 = (TextView)findViewById(R.id.temperature_tv1);
            temperature_tv1.setText(R.string.kannada_temperature);

            humidity_tv1 = (TextView)findViewById(R.id.humidity_tv1);
            humidity_tv1.setText(R.string.kannada_humidity);

            soil_moisture_tv1 = (TextView)findViewById(R.id.soil_moisture_tv1);
            soil_moisture_tv1.setText(R.string.kannada_soil_moisture);

            device1 = (TextView)findViewById(R.id.device1);
            device1.setText(R.string.kannada_waterpump);

            relay_on_rb = (TextView)findViewById(R.id.relay_on_rb);
            relay_on_rb.setText(R.string.kannada_on);

            relay_off_rb = (TextView)findViewById(R.id.relay_off_rb);
            relay_off_rb.setText(R.string.kannada_off);

            sensors_tv1 = (TextView)findViewById(R.id.sensors_tv1);
            sensors_tv1.setText(R.string.kannada_sensor);
        }
        else if (language.equals("telugu"))
        {
            temperature_tv1 = (TextView)findViewById(R.id.temperature_tv1);
            temperature_tv1.setText(R.string.telugu_temperature);

            humidity_tv1 = (TextView)findViewById(R.id.humidity_tv1);
            humidity_tv1.setText(R.string.telugu_humidity);

            soil_moisture_tv1 = (TextView)findViewById(R.id.soil_moisture_tv1);
            soil_moisture_tv1.setText(R.string.telugu_soil_moisture);

            device1 = (TextView)findViewById(R.id.device1);
            device1.setText(R.string.telugu_waterpump);

            relay_on_rb = (TextView)findViewById(R.id.relay_on_rb);
            relay_on_rb.setText(R.string.telugu_on);

            relay_off_rb = (TextView)findViewById(R.id.relay_off_rb);
            relay_off_rb.setText(R.string.telugu_off);

            sensors_tv1 = (TextView)findViewById(R.id.sensors_tv1);
            sensors_tv1.setText(R.string.telugu_sensor);
        }else if (language.equals("hindi"))
        {
            temperature_tv1 = (TextView)findViewById(R.id.temperature_tv1);
            temperature_tv1.setText(R.string.hindi_temperature);

            humidity_tv1 = (TextView)findViewById(R.id.humidity_tv1);
            humidity_tv1.setText(R.string.hindi_humidity);

            soil_moisture_tv1 = (TextView)findViewById(R.id.soil_moisture_tv1);
            soil_moisture_tv1.setText(R.string.hindi_soil_moisture);

            device1 = (TextView)findViewById(R.id.device1);
            device1.setText(R.string.hindi_waterpump);

            relay_on_rb = (TextView)findViewById(R.id.relay_on_rb);
            relay_on_rb.setText(R.string.hindi_on);

            relay_off_rb = (TextView)findViewById(R.id.relay_off_rb);
            relay_off_rb.setText(R.string.hindi_off);

            sensors_tv1 = (TextView)findViewById(R.id.sensors_tv1);
            sensors_tv1.setText(R.string.hindi_sensor);
        }
        else if (language.equals("tamil"))
        {
            temperature_tv1 = (TextView)findViewById(R.id.temperature_tv1);
            temperature_tv1.setText(R.string.tamil_temperature);

            humidity_tv1 = (TextView)findViewById(R.id.humidity_tv1);
            humidity_tv1.setText(R.string.tamil_humidity);

            soil_moisture_tv1 = (TextView)findViewById(R.id.soil_moisture_tv1);
            soil_moisture_tv1.setText(R.string.tamil_soil_moisture);

            device1 = (TextView)findViewById(R.id.device1);
            device1.setText(R.string.tamil_waterpump);

            relay_on_rb = (TextView)findViewById(R.id.relay_on_rb);
            relay_on_rb.setText(R.string.tamil_on);

            relay_off_rb = (TextView)findViewById(R.id.relay_off_rb);
            relay_off_rb.setText(R.string.tamil_off);

            sensors_tv1 = (TextView)findViewById(R.id.sensors_tv1);
            sensors_tv1.setText(R.string.tamil_sensor);
        } else
        {
            /*textView = (TextView)findViewById(R.id.language_main_text);
            textView.setText("english");

            textView = (TextView)findViewById(R.id.text_view1);
            textView.setText(R.string.english_home);*/

            Intent intent = new Intent(this,LanguageActivity.class);
            startActivity(intent);
        }

        super.onStart();
    }

    public void updateDatabase(View view)
    {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId())
        {
            case R.id.relay_on_rb:
                if (checked)
                {
                    databaseReference.child("relay_switch").setValue("ON");
                }
                break;
            case R.id.relay_off_rb:
                if (checked)
                {
                    databaseReference.child("relay_switch").setValue("OFF");
                }
                break;
        }
    }

    public void updateUI(FirebaseUser user)
    {
        if (user!= null)
        {

        }
        else
        {
            Intent intent = new Intent(this, LoginEmailPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mAuth.signOut();
            updateUI(null);
        }
        else if (id == R.id.action_language) {
            Intent intent = new Intent(this,LanguageActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
