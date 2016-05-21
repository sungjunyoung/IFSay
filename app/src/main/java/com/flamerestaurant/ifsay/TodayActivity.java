package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodayActivity extends Activity {

    private static int PAGE_SIZE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        ViewPager pager = (ViewPager) findViewById(R.id.today_pager);
        pager.setAdapter(new Adapter());
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
