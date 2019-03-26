package com.hp.hp.fixxx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread background = new Thread()
        {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(3000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(Splash.this,Category_selection.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        };

        // start thread
        background.start();
    }
}
