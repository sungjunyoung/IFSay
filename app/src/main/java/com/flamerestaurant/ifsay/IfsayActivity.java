package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flamerestaurant.ifsay.realm.Ifsay;

import io.realm.Realm;
import io.realm.RealmResults;

public class IfsayActivity extends Activity {

    private Realm realm;
    RealmResults<Ifsay> results;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifsay);

        realm = Realm.getDefaultInstance();

        int qustionId = getIntent().getIntExtra("QustionId", 4);
        results = realm.where(Ifsay.class).equalTo("questionId", qustionId).findAll();

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
            TextView writer = (TextView) view.findViewById(R.id.ifsay_writer);
            TextView content = (TextView) view.findViewById(R.id.ifsay_content);

            Ifsay ifsay = results.get(position);
            writer.setText(ifsay.getWriter());
            content.setText(ifsay.getContent());

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
}
