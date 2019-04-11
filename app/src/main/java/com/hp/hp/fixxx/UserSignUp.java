package com.hp.hp.fixxx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserSignUp extends AppCompatActivity {

    EditText name,email,phone,password,place;
    FloatingActionButton signup;

    String regapi="http://sicsglobal.co.in/Service_App/API/UserRegistration.aspx?";

    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;

    String name_s,email_s,phone_s,password_s,add_s;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        connectwidgets();
        params=new RequestParams();
        client=new AsyncHttpClient();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 name_s=name.getText().toString();
                 email_s=email.getText().toString();
                 phone_s=phone.getText().toString();
                 password_s=password.getText().toString();
                 add_s=place.getText().toString();

              signupapi();


            }
        });
    }

    private void signupapi() {
        progress=new ProgressDialog(this);
        progress.setMessage("Signing up");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(20);
        progress.show();

        if(name_s.equalsIgnoreCase("")||email_s.equalsIgnoreCase("")||phone_s.equalsIgnoreCase("")||password_s.equalsIgnoreCase("")||add_s.equalsIgnoreCase(""))
        {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            params.put("name",name_s);
            params.put("email",email_s);
            params.put("phone",phone_s);
            params.put("password",password_s);
            params.put("adres",add_s);

            client.get(regapi,params,new AsyncHttpResponseHandler(){
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
                            Toast.makeText(getApplicationContext(), "Successfully registered ! Login now", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserSignUp.this,UserLOGIN.class));
                            progress.dismiss();

                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }


    }

    private void connectwidgets() {

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        place=findViewById(R.id.place);

        signup=findViewById(R.id.signup);




    }
}
