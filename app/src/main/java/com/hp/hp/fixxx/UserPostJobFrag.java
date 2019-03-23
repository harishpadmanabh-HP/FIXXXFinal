package com.hp.hp.fixxx;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserPostJobFrag extends Fragment {

    EditText jobtitle,jobdesc,jobdeadline,jobplace;
    Button postjob;

    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;



    public UserPostJobFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_post_job, container, false);

        jobtitle=view.findViewById(R.id.jobtitle);
        jobdesc=view.findViewById(R.id.jobdesc);
        jobdeadline=view.findViewById(R.id.jobdeadline);
        jobplace=view.findViewById(R.id.jobplace);

        postjob=view.findViewById(R.id.postjob);
        client = new AsyncHttpClient();
        params = new RequestParams();

        postjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=jobtitle.getText().toString();
                String desc=jobdesc.getText().toString();
                String deadline=jobdeadline.getText().toString();
                String place=jobplace.getText().toString();

                SharedPreferences sharedlogin = getActivity().getSharedPreferences("Pref",Context.MODE_PRIVATE);
             String uid=sharedlogin.getString("userid",null);
if(!title.isEmpty()&&!desc.isEmpty()&&!deadline.isEmpty()&&!place.isEmpty()) {
    params.put("jobtitle", title);
    params.put("jobdes", desc);
    params.put("deadline", deadline);
    params.put("plc", place);

    params.put("userid", uid);

    client.get("http://sicsglobal.co.in/Service_App/API/PostJob.aspx?", params, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);

            try {

                jobject = new JSONObject(content);

                String result = jobject.getString("Status");
                String jobid = jobject.getString("Job Id");

                if (result.equalsIgnoreCase("success")) {
                    Toast.makeText(getActivity(), "Job Posted Successfully ! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), " Something went wrong ! Please try again ", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
            }

        }
    });


}else {
    Toast.makeText(getActivity(), "Enter All fields", Toast.LENGTH_SHORT).show();
}


            }
        });




        return view;
    }

}
