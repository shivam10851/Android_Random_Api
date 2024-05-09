package com.example.randomapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageView imageView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        details=new ArrayList<>();
        details.add("Pqr");
        details.add("Abc");
        details.add("Male");
        details.add("City, Country");
        details.add("Age:- "+"40");
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,details);
        listView.setAdapter(arrayAdapter);
    }
    public void refreshBtn(View view){
        imageView=findViewById(R.id.imageView2);
        listView=findViewById(R.id.listView);
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://randomuser.me/api/";
        final String[] imgUrl = new String[1];
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray objArray = response.getJSONArray("results");
                    JSONObject obj = objArray.getJSONObject(0);
                    JSONObject objName = obj.getJSONObject("name");
                    JSONObject objLoc = obj.getJSONObject("location");
                    JSONObject objDob = obj.getJSONObject("dob");
                    JSONObject objPicture = obj.getJSONObject("picture");
                    details.clear();
                    details.add(objName.getString("first"));
                    details.add(objName.getString("last"));
                    details.add(obj.getString("gender"));
                    details.add(objLoc.getString("city") + ", " + objLoc.getString("country"));
                    details.add(objDob.getString("age"));
                    imgUrl[0] = objPicture.getString("large");
                    arrayAdapter.notifyDataSetChanged();
                    listView.setAdapter(arrayAdapter);
                    Picasso.with(getApplicationContext()).load(imgUrl[0]).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myKey","Okay there's an error :(");
            }
        });
        queue.add(jsonObjectRequest);
    }
}