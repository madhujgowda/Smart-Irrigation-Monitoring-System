package com.madhujgowda.www.smartirrigationmonitoringsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
        String language =  sharedPreferences.getString("language","");

    }

    public void openMainActivity(View view)
    {
        this.finish();
    }

    public void changeLanguage(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
        String language =  sharedPreferences.getString("language","");

        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId())
        {
            case R.id.english_rb:
                if (checked)
                {
                    SharedPreferences sharedPreferences_eng = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_eng.edit();
                    editor.putString("language","english").apply();
                    this.finish();
                }
                break;
            case R.id.kannada_rb:
                if (checked)
                {
                    SharedPreferences sharedPreferences_kan = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_kan.edit();
                    editor.putString("language","kannada").apply();
                    this.finish();
                }
                break;
            case R.id.telugu_rb:
                if (checked)
                {
                    SharedPreferences sharedPreferences_kan = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_kan.edit();
                    editor.putString("language","telugu").apply();
                    this.finish();
                }
                break;
            case R.id.tamil_rb:
                if (checked)
                {
                    SharedPreferences sharedPreferences_hin = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_hin.edit();
                    editor.putString("language","tamil").apply();
                    this.finish();
                }
                break;
            case R.id.hindi_rb:
                if (checked)
                {
                    SharedPreferences sharedPreferences_hin = getSharedPreferences("language_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_hin.edit();
                    editor.putString("language","hindi").apply();
                    this.finish();
                }
                break;
        }
    }

}
