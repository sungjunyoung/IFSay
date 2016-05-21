package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.flamerestaurant.ifsay.hue.HueManager;
import com.flamerestaurant.ifsay.realm.Comment;
import com.flamerestaurant.ifsay.realm.Ifsay;
import com.flamerestaurant.ifsay.realm.Question;

import io.realm.Realm;
import io.realm.RealmResults;

public class IfsayActivity extends Activity {

    private Realm realm;
    private RealmResults<Ifsay> results;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifsay);

        realm = Realm.getDefaultInstance();

        int questionId = getIntent().getIntExtra("QuestionId", 4);

        Question question = realm.where(Question.class).equalTo("questionId", questionId).findFirst();
        TextView questionTitle = (TextView) findViewById(R.id.question_title);
        questionTitle.setText(question.getContent());

        results = realm.where(Ifsay.class).equalTo("questionId", questionId).findAll();

        pager = (ViewPager) findViewById(R.id.ifsay_pager);
        pager.setAdapter(new Adapter());

        findViewById(R.id.main_go_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IfsayActivity.this, CircleListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        HueManager.fadeOut();
    }

    private class Adapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.page_ifsay, container, false);

            final Ifsay ifsay = results.get(position);

            TextView writer = (TextView) view.findViewById(R.id.ifsay_writer);
            writer.setText(ifsay.getWriter());

            TextView content = (TextView) view.findViewById(R.id.ifsay_content);
            content.setText(ifsay.getContent());

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ifsay_comment_list);
            final CommentAdapter commentAdapter = new CommentAdapter(ifsay.getIfsayId());
            recyclerView.setAdapter(commentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(IfsayActivity.this, LinearLayoutManager.VERTICAL, false));

            final EditText editText = (EditText) view.findViewById(R.id.ifsay_comment);

            View commentSay = view.findViewById(R.id.comment_say);
            commentSay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realm.beginTransaction();
                    Comment comment = realm.createObject(Comment.class);
                    comment.setIfsayId(ifsay.getIfsayId());
                    comment.setWriter("나");
                    comment.setContent(editText.getText().toString());
                    realm.commitTransaction();
                    commentAdapter.refresh();
                    notifyDataSetChanged();
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private RealmResults<Comment> results;
        private int ifsayId;

        public CommentAdapter(int ifsayId) {
            this.ifsayId = ifsayId;
            refresh();
        }

        public void refresh() {
            results = realm.where(Comment.class).equalTo("ifsayId", ifsayId).findAll();
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.layout_comment, parent, false);
            parent.addView(view);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            holder.comment.setText(results.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    private class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }
}
