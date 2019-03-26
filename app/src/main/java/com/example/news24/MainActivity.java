package com.example.news24;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AsyncHttpClient client;
    RequestParams params;
    ListView listh;

    ArrayList<String> titlearr;
    ArrayList<String> contentarr;
    ArrayList<String> imgg;
    String url="https://thecity247.com/api/get_posts/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new AsyncHttpClient();
        params = new RequestParams();
        titlearr= new ArrayList<String>();
        contentarr= new ArrayList<String>();
        imgg=new ArrayList<String>();
        listh=findViewById(R.id.listxml);
        client.get(url,params,new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(String content) {

                super.onSuccess(content);
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    String s=jsonObject.getString("status");
                    JSONArray jsonArray=jsonObject.getJSONArray("posts");
                   for (int i=0;i<jsonArray.length();i++)
                   {
                   JSONObject jsonObject1=jsonArray.getJSONObject(i);
                       String t=jsonObject1.getString("title");
                        titlearr.add(t);
                       String c=jsonObject1.getString("content");
                       contentarr.add(c);
                       JSONArray jsonArray1=jsonObject1.getJSONArray("attachments");
                       for (int a=0;a<jsonArray1.length();a++)
                       {
                           JSONObject jsonObject2=jsonArray1.getJSONObject(a);
                           String im=jsonObject2.getString("url");
                           imgg.add(im);
                       }
                   }
                    adapter adpt=new adapter();
                    listh.setAdapter(adpt);

                }catch (Exception e){

                }

            }
        });
    }
    class adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titlearr.size();
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
            convertView=inflater.inflate(R.layout.listlay,null);
            TextView titleh=convertView.findViewById(R.id.titlexml);
            TextView contenth=convertView.findViewById(R.id.contentxml);
            ImageView imgh=convertView.findViewById(R.id.imagexml);
            titleh.setText(titlearr.get(position));
            contenth.setText(contentarr.get(position));
            Glide.with(MainActivity.this)
                    .asBitmap()
                    .load(imgg.get(position))
                    .into(imgh);
            return convertView;
        }
    }
}
