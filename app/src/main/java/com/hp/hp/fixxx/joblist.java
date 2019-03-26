package com.hp.hp.fixxx;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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


                    }
                    adapter adpt=new adapter();
                    listView.setAdapter(adpt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
            convertView =inflater.inflate(R.layout.joblistlview,null);

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