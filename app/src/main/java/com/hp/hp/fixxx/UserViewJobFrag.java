package com.hp.hp.fixxx;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserViewJobFrag extends Fragment {

    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    ListView lv;

    ArrayList<String> jobtitleList;
    ArrayList<String>jobdescList;
    ArrayList<String> jobdeadlieList;
    ArrayList<String>jobplaceList,jobidlist;

    public UserViewJobFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_view_job, container, false);

        lv=view.findViewById(R.id.joblist);

        client = new AsyncHttpClient();
        params = new RequestParams();

        jobtitleList=new ArrayList<String>();
        jobdeadlieList=new ArrayList<String>();
        jobdescList=new ArrayList<String>();
        jobplaceList=new ArrayList<String>();
        jobidlist=new ArrayList<String>();



        SharedPreferences sharedlogin = getActivity().getSharedPreferences("Pref",Context.MODE_PRIVATE);
        String uid=sharedlogin.getString("userid",null);

        params.put("USERID",uid);

        client.get("http://sicsglobal.co.in/Service_App/API/ViewJob.aspx?",params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                try{

                    jobject=new JSONObject(content);


                    String s=jobject.getString("Status");
                    if(s.equalsIgnoreCase("success"))
                    {

                        jarray =jobject.getJSONArray("Job Details");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject obj = jarray.getJSONObject(i);

                            String id = obj.getString("id");
                            jobidlist.add("" + id);

                            String title = obj.getString("jobtitle");
                            jobtitleList.add("" + title);


                            String des = obj.getString("jobdes");
                            jobdescList.add("" + des);

                            String deadline = obj.getString("deadline");
                            jobdeadlieList.add("" + deadline);

                            String plc = obj.getString("plc");
                            jobplaceList.add("" + plc);









                        }




                        }
                    else
                    {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                    adapter adpt = new adapter();
                    lv.setAdapter(adpt);
                }catch (Exception e)
                {
                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }
    class adapter extends BaseAdapter {
        LayoutInflater Inflater;
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return jobtitleList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=Inflater.inflate(R.layout.viewjoblistitem,null);
            Viewholder holder=new Viewholder();

            holder.titletv=(TextView)convertView.findViewById(R.id.listtitle);
            holder.titletv.setText(jobidlist.get(position)+" . "+jobtitleList.get(position));

            holder.destv=(TextView)convertView.findViewById(R.id.listdesc);
            holder.destv.setText(jobdescList.get(position));

            holder.deadtv=(TextView)convertView.findViewById(R.id.listdeadline);
            holder.deadtv.setText(jobdeadlieList.get(position));

            holder.plctv=(TextView)convertView.findViewById(R.id.listplace);
            holder.plctv.setText(jobplaceList.get(position));




            return convertView;
        }
        class Viewholder{
            TextView titletv;
            TextView destv;
            TextView deadtv;
            TextView plctv;



        }
    }

}
