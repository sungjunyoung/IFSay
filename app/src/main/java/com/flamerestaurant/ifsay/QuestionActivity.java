package com.flamerestaurant.ifsay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flamerestaurant.ifsay.hue.HueManager;
import com.flamerestaurant.ifsay.realm.Ifsay;
import com.flamerestaurant.ifsay.realm.Question;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class QuestionActivity extends Activity {

    private Realm realm;

    private TextToSpeech myTTS;
    private RealmResults<Question> results;
    private Adapter pagerAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HueManager.fadeIn();
        setContentView(R.layout.activity_question);

        realm = Realm.getDefaultInstance();
        results = realm.where(Question.class).findAllSorted("questionId");

        ViewPager pager = (ViewPager) findViewById(R.id.today_pager);
        pagerAdapter = new Adapter();
        pager.setAdapter(pagerAdapter);

        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    myTTS.setLanguage(Locale.KOREAN);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ttsGreater21("어린 시절에서, 단 하나만 바꿀 수 있다면, 무엇을 바꾸겠어요?");
                    } else {
                        ttsUnder20("어린 시절에서 단 하나만 바꿀 수 있다면 무엇을 바꾸겠어요?");
                    }
                }
            }
        });

    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        HueManager.fadeOut();
        myTTS.shutdown();
    }

    private class Adapter extends PagerAdapter {

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_today_page, container, false);
            Question question = results.get(position);
            final EditText edit = (EditText) view.findViewById(R.id.today_write_text);
            ImageView sendBtn = (ImageView) view.findViewById(R.id.today_write_button);

            final Ifsay myIfsay = realm.where(Ifsay.class)
                    .equalTo("writer", "나")
                    .equalTo("questionId", position)
                    .findFirst();

            if (myIfsay != null) {
                edit.setText(myIfsay.getContent());
                edit.setEnabled(false);
            }

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myIfsay == null) {
                        realm.beginTransaction();

                        Ifsay ifsay = realm.createObject(Ifsay.class);
                        ifsay.setQuestionId(position);
                        ifsay.setContent(edit.getText().toString());
                        ifsay.setWriter("나");
                        ifsay.setRgb("#ffffff");
                        ifsay.setDate(new Date(2016, 4, 22));
                        ifsay.setIfsayId(30);
                        ifsay.setIfsayCount(0);

                        realm.commitTransaction();
                    }
                    HueManager.twinkle(3);
                    Intent intent = new Intent(QuestionActivity.this, IfsayActivity.class);
                    intent.putExtra("questionId", position);
                    startActivity(intent);
                }
            });


            SimpleDateFormat sf = new SimpleDateFormat("MM월 dd일");
            TextView title = (TextView) view.findViewById(R.id.today_title);
            TextView body = (TextView) view.findViewById(R.id.today_body);
            title.setText(question.getContent());

            if ("05월 28일".equals(sf.format(question.getDate()).toString())) {
                body.setText("오늘의 질문");
            } else {
                body.setText(sf.format(question.getDate()).toString() + "의 질문");
            }

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
