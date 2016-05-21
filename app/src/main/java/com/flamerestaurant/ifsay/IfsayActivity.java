package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private class Adapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.page_ifsay, container, false);

            final Ifsay ifsay = results.get(position);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ifsay_comment_list);
            final CommentAdapter commentAdapter = new CommentAdapter(ifsay, recyclerView);
            recyclerView.setAdapter(commentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(IfsayActivity.this, LinearLayoutManager.VERTICAL, false));

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

    private class CommentAdapter extends RecyclerView.Adapter {

        private RealmResults<Comment> results;
        private Ifsay ifsay;
        private RecyclerView recyclerView;

        public CommentAdapter(Ifsay ifsayId, RecyclerView recyclerView) {
            this.ifsay = ifsayId;
            this.recyclerView = recyclerView;
            refresh();
        }

        public void refresh() {
            results = realm.where(Comment.class).equalTo("ifsayId", ifsay.getIfsayId()).findAll();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = getLayoutInflater().inflate(R.layout.page_ifsay_header, parent, false);
                    parent.addView(view);
                    return new HeaderViewHolder(view);
                case 2:
                    view = getLayoutInflater().inflate(R.layout.page_ifsay_footer, parent, false);
                    parent.addView(view);
                    return new FooterViewHolder(view);
                default:
                    view = getLayoutInflater().inflate(R.layout.layout_comment, parent, false);
                    parent.addView(view);
                    return new CommentViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);

            switch (viewType) {
                case 0:
                    ((HeaderViewHolder) holder).writer.setText(ifsay.getWriter());
                    ((HeaderViewHolder) holder).content.setText(ifsay.getContent());
                    break;
                case 2:
                    ((FooterViewHolder) holder).commentSay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            realm.beginTransaction();
                            Comment comment = realm.createObject(Comment.class);
                            comment.setIfsayId(ifsay.getIfsayId());
                            comment.setWriter("나");
                            comment.setContent(((FooterViewHolder) holder).editText.getText().toString());
                            realm.commitTransaction();
                            refresh();
                            notifyDataSetChanged();
                            recyclerView.scrollToPosition(getItemCount() - 1);
                        }
                    });
                    break;
                default:
                    ((CommentViewHolder) holder).comment.setText(results.get(position - 1).getContent());
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else if (position == getItemCount() - 1) {
                return 2;
            }
            return 1;
        }

        @Override
        public int getItemCount() {
            return results.size() + 2;
        }
    }

    private class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.comment_content);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView writer;
        public TextView content;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            writer = (TextView) itemView.findViewById(R.id.ifsay_writer);
            content = (TextView) itemView.findViewById(R.id.ifsay_content);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private final EditText editText;
        private final View commentSay;

        public FooterViewHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.ifsay_comment);
            commentSay = itemView.findViewById(R.id.comment_say);
        }
    }
}
