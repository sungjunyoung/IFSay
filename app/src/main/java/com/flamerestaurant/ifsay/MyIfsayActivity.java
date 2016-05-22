package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flamerestaurant.ifsay.realm.Comment;
import com.flamerestaurant.ifsay.realm.Ifsay;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyIfsayActivity extends Activity {

    private Realm realm;
    private RecyclerView ifsay_list;
    private RealmResults<Ifsay> results;
    public int countOfAllIfsay;
    private TextView allIfsayTextView;
    private RealmResults<Comment> comment_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ifsay);
        realm = Realm.getDefaultInstance();
        results = realm.where(Ifsay.class).findAll();
        comment_results = realm.where(Comment.class).findAll();


        ifsay_list = (RecyclerView) findViewById(R.id.my_ifsay_list);
        ifsay_list.setAdapter(new Adapter());
        ifsay_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        allIfsayTextView = (TextView) findViewById(R.id.white_ifsay_count);

        for (Ifsay ifsay : results) {
            countOfAllIfsay += ifsay.getIfsayCount();
        }
        allIfsayTextView.setText(String.valueOf(countOfAllIfsay));

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        double unitWidth = width / 5.0;
        ImageView treeBtn = (ImageView) findViewById(R.id.tree_btn);
        treeBtn.getLayoutParams().width = (int) unitWidth;
        treeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIfsayActivity.this, CircleListActivity.class);
                startActivity(intent);
            }
        });
        ImageView searchBtn = (ImageView) findViewById(R.id.search_btn);
        searchBtn.getLayoutParams().width = (int) unitWidth;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIfsayActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageView mainBtn = (ImageView) findViewById(R.id.main_btn);
        mainBtn.getLayoutParams().width = (int) unitWidth;
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIfsayActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
        ImageView alarmBtn = (ImageView) findViewById(R.id.alarm_btn);
        alarmBtn.getLayoutParams().width = (int) unitWidth;
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIfsayActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
        ImageView myPageBtn = (ImageView) findViewById(R.id.mypage_btn);
        myPageBtn.getLayoutParams().width = (int) unitWidth;
        myPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIfsayActivity.this, MyIfsayActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    class Adapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.activity_myifsaycolum, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            int count = 0;
            for (Comment comment : comment_results) {
                if (comment.getIfsayId() == position) count++;
            }
            Ifsay ifsay = results.get(position);
            holder.like_count.setText(String.valueOf(ifsay.getIfsayCount()));
            holder.ifsay_textview.setText(ifsay.getContent());
            holder.comment_count.setText(String.valueOf(count));
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView question_textview;
        public TextView ifsay_textview;
        public TextView like_count;
        public TextView comment_count;

        public Holder(View itemView) {
            super(itemView);
            question_textview = (TextView) itemView.findViewById(R.id.question_textview);
            ifsay_textview = (TextView) itemView.findViewById(R.id.ifsay_textview);
            like_count = (TextView) itemView.findViewById(R.id.like_count);
            comment_count = (TextView) itemView.findViewById(R.id.comment_count);
        }
    }
}
