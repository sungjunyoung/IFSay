package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flamerestaurant.ifsay.hue.HueManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHLight;

import java.util.List;
import java.util.Random;

public class AlarmActivity extends Activity {

    private RecyclerView recyclerView;
    private List<PHLight> phLightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        recyclerView = (RecyclerView) findViewById(R.id.friendList);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        phLightList = PHHueSDK.getInstance().getSelectedBridge().getResourceCache().getAllLights();
        Log.d("HUE", phLightList + "");
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        double unitWidth = width / 5.0;
        ImageView treeBtn = (ImageView) findViewById(R.id.tree_btn);
        treeBtn.getLayoutParams().width = (int) unitWidth;
        treeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, CircleListActivity.class);
                startActivity(intent);
            }
        });
        ImageView searchBtn = (ImageView) findViewById(R.id.search_btn);
        searchBtn.getLayoutParams().width = (int) unitWidth;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageView mainBtn = (ImageView) findViewById(R.id.main_btn);
        mainBtn.getLayoutParams().width = (int) unitWidth;
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
        ImageView alarmBtn = (ImageView) findViewById(R.id.alarm_btn);
        alarmBtn.getLayoutParams().width = (int) unitWidth;
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
        ImageView myPageBtn = (ImageView) findViewById(R.id.mypage_btn);
        myPageBtn.getLayoutParams().width = (int) unitWidth;
        myPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, MyIfsayActivity.class);
                startActivity(intent);
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.activity_alarmcolumn, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            PHLight phLight = phLightList.get(position);
            holder.name.setText(phLight.getName());
            holder.lightId.setText(phLight.getIdentifier());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != 2) {
                        Toast.makeText(AlarmActivity.this, "Cannot call", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    HueManager.alert(new Random().nextInt(7));
                }
            });
        }

        @Override
        public int getItemCount() {
            return phLightList.size();
        }
    }


    class Holder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView lightId;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            lightId = (TextView) itemView.findViewById(R.id.light_id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
