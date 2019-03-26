package com.hp.hp.fixxx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LabourHome extends AppCompatActivity {
    EditText placejava;
    ImageButton submitja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_home);
        submitja=findViewById(R.id.submit);
        placejava=findViewById(R.id.placexml);


    }

    public void jobsearch(View view) {
        String place=placejava.getText().toString();
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("pre",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("key",place);
        editor.apply();
        Intent intent=new Intent(getApplicationContext(),joblist.class);
        if (place.equals(""))
        {
            Toast.makeText(this, "invalid location", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(intent);
        }
    }
}
