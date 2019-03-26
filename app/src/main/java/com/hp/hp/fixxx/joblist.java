package com.hp.hp.fixxx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class joblist extends AppCompatActivity {
    AsyncHttpClient client;
    RequestParams params;
    ListView listView;
    ArrayList<String> jobtitle;
    ArrayList<String> jobdes;
    ArrayList<String> deadline;
    ArrayList<String> jobid;
    String ph;

    String url="http://sicsglobal.co.in/Service_App/API/ViewJobAsPlace.aspx?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joblist);
        listView=findViewById(R.id.listview);
        client=new AsyncHttpClient();
        params=new RequestParams();
        jobtitle=new ArrayList<>();
        jobdes=new ArrayList<>();
        deadline=new ArrayList<>();
        jobid=new ArrayList<>();

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("pre",MODE_PRIVATE);
        String p=sharedPreferences.getString("key",null);
        params.put("place",p);

        client.get(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    JSONArray jsonArray=jsonObject.getJSONArray("Details");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        String title=object.getString("jobtitle");
                        jobtitle.add(title);
                        String des=object.getString("jobdes");
                        jobdes.add(des);
                        String dl=object.getString("deadline");
                        deadline.add(dl);
                        String id=object.getString("id");
                        jobid.add(id);


                    }
                    adapter adpt=new adapter();
                    listView.setAdapter(adpt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(joblist.this, ""+jobid.get(position), Toast.LENGTH_SHORT).show();

                SharedPreferences sharedlogin=getApplicationContext().getSharedPreferences("sharedlogin",MODE_PRIVATE);
String lbid=sharedlogin.getString("lbid",null);



                RequestParams params1;
                AsyncHttpClient client1;
                client1=new AsyncHttpClient();
                params1=new RequestParams();

                params1.put("labourid",lbid);
                params1.put("jobid",jobid.get(position));
                String url1="http://sicsglobal.co.in/Service_App/API/SelectJobByLabour.aspx?";

                client1.get(url1,params1,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);

                        try {
                            JSONObject a=new JSONObject(content);
                            String result=a.getString("Status");
                            String id=a.getString("Job Status Id");
                           // Toast.makeText(joblist.this, " "+result+" "+id, Toast.LENGTH_LONG).show();


                            Toast.makeText(joblist.this, "Job selected and waiting for approval form user", Toast.LENGTH_SHORT).show();

                            RequestParams params2;
                            AsyncHttpClient client2;

                            client2=new AsyncHttpClient();
                            params2=new RequestParams();

                            params2.put("jobid",jobid.get(position));

                            String url2="http://sicsglobal.co.in/Service_App/API/UserDetailsByJobId.aspx?";

                            client2.get(url2,params2,new AsyncHttpResponseHandler(){
                                @Override
                                public void onSuccess(String content) {
                                    super.onSuccess(content);
                                    try{
                                        final JSONObject mainobj=new JSONObject(content);
                                        if (mainobj.getString("Status").equals("Success")){
                                            Toast.makeText(joblist.this, "Success", Toast.LENGTH_SHORT).show();
                                            JSONArray jarray=mainobj.getJSONArray("Details");
                                            for (int i=0;i<jarray.length();i++){
                                                JSONObject detobj=jarray.getJSONObject(i);
                                                String usid=detobj.getString("userid");

                                                                          AsyncHttpClient client3;
                                                                          RequestParams params3;

                                                                          client3=new AsyncHttpClient();
                                                                          params3=new RequestParams();

                                                                          params3.put("userid",usid);

                                                                          String url3="http://sicsglobal.co.in/Service_App/API/UserDetails.aspx?";

                                                                          client3.get(url3,params3,new AsyncHttpResponseHandler(){
                                                                              @Override
                                                                              public void onSuccess(String content) {
                                                                                  super.onSuccess(content);
                                                                                  try{
                                                                                      JSONObject mobj=new JSONObject(content);
                                                                                      if (mobj.getString("Status").equals("Success")){
                                                                                          JSONArray jarray=mobj.getJSONArray("Details");
                                                                                          for (int j=0;j<jarray.length();j++){
                                                                                              JSONObject deobj=jarray.getJSONObject(j);
                                                                                              String nm=deobj.getString("name");
                                                                                               ph=deobj.getString("phn");
                                                                                             // Toast.makeText(joblist.this, ""+nm+ph, Toast.LENGTH_SHORT).show();
                                                                                              AlertDialog.Builder AB = new AlertDialog.Builder(joblist.this);
                                                                                              AB.setMessage("Do you really want to take this job ?").setCancelable(false).setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                                                                                                  @Override
                                                                                                  public void onClick(DialogInterface dialog, int which) {

                                                                                                      SmsManager smsManager = SmsManager.getDefault();
                                                                                                      smsManager.sendTextMessage(ph, null, "FIXZR = Job Selected from the sender of this message . Feel free to contact the labour back  for confirming other terms and condition . HAPPY FIXING !", null, null);


                                                                                                  }
                                                                                              }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                                                                  @Override
                                                                                                  public void onClick(DialogInterface dialog, int which) {
                                                                                                      dialog.cancel();
                                                                                                  }
                                                                                              });
                                                                                              AlertDialog A = AB.create();
                                                                                              A.setTitle("EXIT SCREEN");
                                                                                              A.show();

                                                                                          }

                                                                                      } else {
                                                                                          Toast.makeText(joblist.this, "failed", Toast.LENGTH_SHORT).show();
                                                                                      }


                                                                                  }catch(Exception e){

                                                                                  }
                                                                              }
                                                                          });




                                                Toast.makeText(joblist.this, "user id"+usid, Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(joblist.this, "failed", Toast.LENGTH_SHORT).show();

                                        }

                                    }catch (Exception e){

                                    }
                                }
                            });





                        }catch (Exception e)
                        {
                            Toast.makeText(joblist.this, ""+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                });







            }
        });
    }
    class adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return jobtitle.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.laboursparelistitem,null);

            TextView tit=convertView.findViewById(R.id.jobtitleget);
            TextView des=convertView.findViewById(R.id.jobdesget);
            TextView lasdat=convertView.findViewById(R.id.deadlineget);


            tit.setText(jobtitle.get(position));
            des.setText(jobdes.get(position));
            lasdat.setText(deadline.get(position));

            return convertView;
        }
    }
}