package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class CircleListActivity extends Activity {
    private final Handler mHideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_list);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        double unitWidth = width / 5.0;
        ImageView treeBtn = (ImageView) findViewById(R.id.tree_btn);
        treeBtn.getLayoutParams().width = (int) unitWidth;
        treeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleListActivity.this, IfsayActivity.class);
                startActivity(intent);
            }
        });
        ImageView searchBtn = (ImageView) findViewById(R.id.search_btn);
        searchBtn.getLayoutParams().width = (int) unitWidth;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageView mainBtn = (ImageView) findViewById(R.id.main_btn);
        mainBtn.getLayoutParams().width = (int) unitWidth;
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleListActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
        ImageView alarmBtn = (ImageView) findViewById(R.id.alarm_btn);
        alarmBtn.getLayoutParams().width = (int) unitWidth;
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleListActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
        ImageView myPageBtn = (ImageView) findViewById(R.id.mypage_btn);
        myPageBtn.getLayoutParams().width = (int) unitWidth;
        myPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleListActivity.this, MyIfsayActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickQuestion(View v) {
        Intent intent = new Intent(this, IfsayActivity.class);
        int questionId;
        switch (v.getId()) {
            case R.id.question0:
                questionId = 0;
                break;
            case R.id.question1:
                questionId = 1;
                break;
            case R.id.question2:
                questionId = 2;
                break;
            case R.id.question3:
                questionId = 3;
                break;
            default:
                questionId = 4;
        }
        intent.putExtra("questionId", questionId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
