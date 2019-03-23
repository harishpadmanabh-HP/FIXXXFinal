package com.hp.hp.fixxx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserLOGIN extends AppCompatActivity {

    EditText phone,password;
    CheckBox rememberme;
    ImageButton Signin;
    Button Signup;


    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;

    String userloginapi="http://sicsglobal.co.in/Service_App/API/UserLogin.aspx?";
    String apiphnoneparams="phn";
    String apipassparams="Password";

    String phone_s,password_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        connectwidgets();

        client = new AsyncHttpClient();
        params = new RequestParams();



        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLOGIN.this,UserSignUp.class));
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_s = phone.getText().toString();
                 password_s=password.getText().toString();

                signinapi();

            }
        });

    }

    private void signinapi() {
        if(phone_s.equalsIgnoreCase("")||password_s.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Enter all fields.", Toast.LENGTH_SHORT).show();
        }

        else
        {

            params.put(apiphnoneparams,phone_s);
            params.put(apipassparams,password_s);
            client.get(userloginapi,params,new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);

                    try{

                        jobject = new JSONObject(content);
                        Log.e(content,"content");

                        String s=jobject.getString("Status");

                        if(s.equalsIgnoreCase("success"))

                        {
                            String uid=jobject.getString("UserId");
                            String uname=jobject.getString("Name");

                            SharedPreferences sharedlogin = getApplicationContext().getSharedPreferences("Pref",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedlogin.edit();

                            editor.putString("userid",uid);
                            editor.putString("name",uname);

                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),UserHome.class));


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Login not successfull ", Toast.LENGTH_SHORT).show();

                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Screwed!!!! "+e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void connectwidgets() {

        phone=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Signin=findViewById(R.id.signin);
        Signup=findViewById(R.id.signup);

    }


}
