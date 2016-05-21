package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.flamerestaurant.ifsay.hue.HueManager;
import com.flamerestaurant.ifsay.realm.Ifsay;

import io.realm.Realm;

public class QuestionActivity extends Activity {

    private static int PAGE_SIZE = 5;

    private Realm realm;

    private ViewPager pager;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        realm = Realm.getDefaultInstance();

        pager = (ViewPager) findViewById(R.id.today_pager);
        pager.setAdapter(new Adapter());

        edit = (EditText) findViewById(R.id.today_write_text);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        HueManager.fadeOut();
    }

    public void onClickWite(View view) {
        realm.beginTransaction();

        Ifsay ifsay = realm.createObject(Ifsay.class);
        ifsay.setQuestionId(pager.getCurrentItem());
        ifsay.setContent(edit.getText().toString());

        realm.commitTransaction();

        Intent intent = new Intent(this, IfsayActivity.class);
        intent.putExtra("QustionId", pager.getCurrentItem());
        HueManager.twinkle(3);
        startActivity(intent);
    }

    private class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return PAGE_SIZE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_today_page, container, false);

            TextView title = (TextView) view.findViewById(R.id.today_title);
            TextView body = (TextView) view.findViewById(R.id.today_body);

            title.setText(String.format("제목 %d", position));
            body.setText(String.format("내용 %d", position));

            container.addView(view);
            return view;
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
}
