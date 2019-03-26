package com.hp.hp.fixxx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class Category_selection extends AppCompatActivity {

    AsyncHttpClient client,client_labour_reg;
    RequestParams params,params_labour_reg;

    EditText lbphone,lbpassword,lbname,lbemail,labph,lbaddress,jbcat;

    Button submitbtn;




    String url="http://sicsglobal.co.in/Service_App/API/LabourLogin.aspx?";
    String url_labour_reg="http://sicsglobal.co.in/Service_App/API/LabourRegistration.aspx?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);


        client=new AsyncHttpClient();
        params=new RequestParams();

        client_labour_reg=new AsyncHttpClient();
        params_labour_reg=new RequestParams();






    }



    public void labour(View view) {
        LayoutInflater inflat=LayoutInflater.from(Category_selection.this);
        View cuslay=inflat.inflate(R.layout.labourloginalert,null);

        CardView cview=cuslay.findViewById(R.id.cdview);
        Button labourlginjava=cuslay.findViewById(R.id.buttonlabourlogin);
        Button labourregjava=cuslay.findViewById(R.id.labourregbtn);

        lbphone=cuslay.findViewById(R.id.labourphone);
        lbpassword=cuslay.findViewById(R.id.labourpassword);

        AlertDialog.Builder AB=new AlertDialog.Builder(Category_selection.this);
        AB.setView(cuslay);
        final AlertDialog A=AB.create();
        A.show();

//        String s=labourlginjava.getText().toString();
//        Toast.makeText(getApplicationContext(), ""+s, Toast.LENGTH_SHORT).show();



        labourlginjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lphone=lbphone.getText().toString();
                String lpass=lbpassword.getText().toString();

                if (!lphone.isEmpty()&&!lpass.isEmpty()){
                    params.put("phn",lphone);
                    params.put("Password",lpass);

                    client.get(url,params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            try{
                                JSONObject mainobj=new JSONObject(content);
                                if (mainobj.getString("Status").equals("Success")){
                                    String lbid=mainobj.getString("LabourId");
                                    String namelab=mainobj.getString("Name");

                                    SharedPreferences sharedlogin=getApplicationContext().getSharedPreferences("sharedlogin",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedlogin.edit();
                                    editor.putString("lbid",lbid);
                                    editor.putString("name",namelab);
                                    editor.apply();


                                    Toast.makeText(Category_selection.this, "success", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(Category_selection.this, "failed", Toast.LENGTH_SHORT).show();


                                }
                            }catch(Exception E){

                            }
                        }
                    });
                }else {
                    Toast.makeText(Category_selection.this, "enter all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        labourregjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflat=LayoutInflater.from(Category_selection.this);
                View cuslay=inflat.inflate(R.layout.labour_reg_alert,null);

                lbname=cuslay.findViewById(R.id.lname);
                lbemail=cuslay.findViewById(R.id.email);
                labph=cuslay.findViewById(R.id.lphone);
                lbaddress=cuslay.findViewById(R.id.laddress);
                jbcat=cuslay.findViewById(R.id.jobcat);
                submitbtn=cuslay.findViewById(R.id.sub);

                AlertDialog.Builder ABc=new AlertDialog.Builder(Category_selection.this);
                ABc.setView(cuslay);
                final AlertDialog A=ABc.create();
                A.show();

                submitbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lnam=lbname.getText().toString();
                        String leml=lbemail.getText().toString();
                        String lph=labph.getText().toString();
                        String lbadd=lbaddress.getText().toString();
                        String jbct=jbcat.getText().toString();

                        if (!lnam.isEmpty()&&!leml.isEmpty()&&!lph.isEmpty()&&!lbadd.isEmpty()&&!jbct.isEmpty()){

                            params_labour_reg.put("name",lnam);
                            params_labour_reg.put("email",leml);
                            params_labour_reg.put("phone",lph);
                            params_labour_reg.put("password",lbadd);
                            params_labour_reg.put("adres",lbadd);
                            params_labour_reg.put("jobcat",jbct);

                            client_labour_reg.get(url_labour_reg,params_labour_reg,new AsyncHttpResponseHandler(){

                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);

                                    try {
                                        JSONObject mainobj=new JSONObject(content);
                                        String s=mainobj.getString("Status");
                                        if (s.equals("AlreadyExist")){
                                            Toast.makeText(Category_selection.this, ""+s, Toast.LENGTH_SHORT).show();

//                                            String userid=mainobj.getString("labid");
//                                            SharedPreferences sharedreg=getApplicationContext().getSharedPreferences("spf",MODE_PRIVATE);
//                                            SharedPreferences.Editor edt=sharedreg.edit();
//                                            edt.putString("userid",userid);
//                                            edt.apply();
                                        }

                                    }
                                    catch (Exception e){

                                    }
                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(Category_selection.this, "enter all fields", Toast.LENGTH_SHORT).show();
                        }



                    }
                });





            }
        });











    }

    public void user(View view) {
        LayoutInflater inflat=LayoutInflater.from(Category_selection.this);
        View cuslay=inflat.inflate(R.layout.userloginalert,null);

        CardView cview=cuslay.findViewById(R.id.cdview);
        Button userlginjava=cuslay.findViewById(R.id.buttonuserlogin);
        Button userregjava=cuslay.findViewById(R.id.userregbtn);
        AlertDialog.Builder AB=new AlertDialog.Builder(Category_selection.this);
        AB.setView(cuslay);
        final AlertDialog A=AB.create();
        A.show();
    }
}
