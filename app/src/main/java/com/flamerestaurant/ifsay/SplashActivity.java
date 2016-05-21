package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.flamerestaurant.ifsay.realm.Comment;
import com.flamerestaurant.ifsay.realm.Ifsay;
import com.flamerestaurant.ifsay.realm.Question;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class SplashActivity extends Activity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        realm = Realm.getDefaultInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataInit();
                Intent intent = new Intent(SplashActivity.this, QuestionActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void dataInit() {
        realm.beginTransaction();

        realm.clear(Question.class);
        realm.clear(Ifsay.class);
        realm.clear(Comment.class);

        RealmList<Question> questionList = new RealmList<Question>();
        Question question1 = new Question(0,"어린 시절에서, 단 하나만 바꿀 수 있다면, 무엇을 바꾸겠는가?",new Date(2016,5,28));
        Question question2 = new Question(1,"어제 봤는데, 오늘도 보고싶고, 내일도 보고싶은 인생의 영화는?",new Date(2016,5,19));
        Question question3 = new Question(2,"당신의 자서전 첫 문장은 어떻게 시작할것인가?",new Date(2016,5,20));
        Question question4 = new Question(3,"오늘 10억원이 생겼다. 무엇을 하겠는가?",new Date(2016,5,21));
        Question question5 = new Question(4,"입원하기 직전으로 돌아간다면, 나는 무엇을 할까?",new Date(2016,5,22));
        questionList.add(question1); questionList.add(question2);
        questionList.add(question3); questionList.add(question4);
        questionList.add(question5);

        RealmList<Ifsay> ifsayList = new RealmList<Ifsay>();
        //ifsayId, questionId, writer, content, ifsayCount, date, rgb
        Ifsay ifsay1 = new Ifsay(0,5,"은범","피아노 학원을 끝까지 다닐거야. 어릴때는 왜 그렇게 도망갔을까? 여자친구에게 피아노 치며 노래불러주고 싶다. ",6,new Date(2016,5,28),"#ffffff");
        Ifsay ifsay2 = new Ifsay(1,1,"결","당연히 '이터널 선샤인'. 잊었다고 생각한 그 사람을 자꾸 떠오르게하지만, 어쩔수 없이 보게된다. 재개봉했다던데 ㅜㅜ",13,new Date(2016,5,19),"#ffffff");
        Ifsay ifsay3 = new Ifsay(2,1,"준영","비긴어게인이요! 생각난김에 다운받아 봐야겠어요..",10,new Date(2016,05,19),"#ffffff");
        Ifsay ifsay4 = new Ifsay(3,2,"준영","그 누구도 22년뒤 내가  병원에서 꼼짝없이 누워있을 것을 알지 못했다.",22,new Date(2016,5,20),"#ffffff");
        Ifsay ifsay5 = new Ifsay(4,3,"예진","람보르기니를 살래요 강남대로를 200키로로 달리고 싶어요",10,new Date(2016,5,21),"#ffffff");
        Ifsay ifsay6 = new Ifsay(5,4,"결","글쎄.. 너무 갑작스럽게 와서 경황이 없었다. 아, 지난주에 여자친구와 데이트하다 사소한일로 다투었는데.. 그때 고집부리지 말고 좋아한다고 말할래!", 50,new Date(2016,5,22),"#ffffff");
        Ifsay ifsay7 = new Ifsay(6,4,"은범","끊어놓고 세번 갔던 PT를 끝까지 갔을꺼다. 돈도 돈이지만, 그때 운동했다면 ㅜ.ㅜ 술 조금만 덜먹고 더 뛰었다면.. 퇴원하면 꼭 다시 운동 열심히 할거다.",77,new Date(2016,5,22),"#ffffff");
        Ifsay ifsay8 = new Ifsay(7,4,"예진","저는 소설쓰는걸 배울거에요. 병원에 와서 책을 많이 읽고 써보기도하는데, 어떻게 시작해야할지 모르겠어요. 소설쓰는 법을 배우고 여행을 많이 다니면서 좋은 책을 쓰고싶어요",21,new Date(2016,5,22),"#ffffff");
        Ifsay ifsay9 = new Ifsay(8,4,"준영","회사 영업왕 실적을 꼭 찍을거에요. 힘들다고 욕하는 회사지만 사실 정말 재미있게 다녔거든요.. 선배들한테 배운걸 써먹고, 제 한계를 시험하고 싶어요",45,new Date(2016,5,22),"#ffffff");
        Ifsay ifsay10 = new Ifsay(9,5,"예진","보약을 매일매일 챙겨먹을꺼야",6,new Date(2016,5,28),"#ffffff");
        Ifsay ifsay11 = new Ifsay(10,5,"결","짝사랑하던 단짝친구에게 고백할꺼야",13,new Date(2016,5,28),"#ffffff");
        Ifsay ifsay12 = new Ifsay(11,2,"은범","그러지 말았어야 했다.",27,new Date(2016,5,20),"#ffffff");
        ifsayList.add(ifsay1); ifsayList.add(ifsay2);
        ifsayList.add(ifsay3); ifsayList.add(ifsay4);
        ifsayList.add(ifsay5); ifsayList.add(ifsay6);
        ifsayList.add(ifsay7); ifsayList.add(ifsay8);
        ifsayList.add(ifsay9); ifsayList.add(ifsay10);
        ifsayList.add(ifsay11); ifsayList.add(ifsay12);

        RealmList<Comment> commentList = new RealmList<Comment>();
        Comment comment1 = new Comment(0,2,"저도 비긴어게인 정말 좋아하는데","병훈",new Date(2016,5,19));
        Comment comment2 = new Comment(1,4,"저도 지긋지긋한 휠체어 벗어나고 싶어요!","은범",new Date(2016,5,20));
        Comment comment3 = new Comment(2,6,"맞아요.. 운동 안한건 정말 후회되어요. 엄마랑 산책이라도 많이 갈껄...\n","결",new Date(2016,5,22));
        Comment comment4 = new Comment(3,6,"222 운동의 중요성은 정말.. 퇴원하면 누구보다 열심히 몸 관리할거에요","준영",new Date(2016,5,22));
        Comment comment5 = new Comment(4,6,"저도 조기축구회 회원비만 얼마 냈는지.. 돌아가면 꼭 갈거에요","다영",new Date(2016,5,22));
        Comment comment6 = new Comment(5,7,"와...! 진짜 멋있는 생각이에요 그 꿈 꼭 이루길 바랄게요!! ","예진",new Date(2016,5,22));
        Comment comment7 = new Comment(6,7,"오오 나중에 유명한 소설 쓰면 꼭 서울의료원에서 구상했다고 밝히셔야해요 ㅎㅎ","은범",new Date(2016,5,22));
        Comment comment8 = new Comment(7,7,"기대합니다!! 요즘은 어떤 것 읽으세요? 재미있는 책 추천해주세요! 너무 심심해요","결",new Date(2016,5,22));
        Comment comment9 = new Comment(8,7,"지금도 쓰고 계셔요? 분명히 좋은 글일거에요 :) 괜찮으시면 공유해주세요. 읽어보고싶어요.","준영",new Date(2016,5,22));
        Comment comment10 = new Comment(9,8,"영업왕이 목표라니! 너무 멋진 남편감이에요!! (신부감일수도!!)","예진",new Date(2016,5,22));
        Comment comment11 = new Comment(10,8,"자기 일에 자부심을 갖는 모습 너무 좋은것 같아요. 퇴원하고 꼭 해내실수 있을거에요 응원합니다!","예진",new Date(2016,5,22));
        Comment comment12 = new Comment(11,8,"응원해요! 도움이 필요하면 저희를 찾아주세요 ㅎㅎ","은범",new Date(2016,5,22));
        Comment comment13 = new Comment(12,4,"저는 자유로에서 새벽에 달리는게 소원인데","예진",new Date(2016,5,20));
        Comment comment14 = new Comment(13,1,"잊었다고 생각한 옛사람이 다시 떠오르는건 생각만해도 싫어요...","준영",new Date(2016,5,20));
        Comment comment15 = new Comment(14,2,"우와! 저도 비긴어게인 엄청좋아하는데!!","은범",new Date(2016,5,20));
        Comment comment16 = new Comment(15,3,"첫장부터 읽기 싫을거 같아요...","예진",new Date(2016,5,20));
        commentList.add(comment1); commentList.add(comment2);
        commentList.add(comment3); commentList.add(comment4);
        commentList.add(comment5); commentList.add(comment6);
        commentList.add(comment7); commentList.add(comment8);
        commentList.add(comment9); commentList.add(comment10);
        commentList.add(comment11); commentList.add(comment12);
        commentList.add(comment13); commentList.add(comment14);
        commentList.add(comment15); commentList.add(comment16);

        realm.copyToRealm(commentList);
        realm.copyToRealm(ifsayList);
        realm.copyToRealm(questionList);

        realm.commitTransaction();
    }
}
