package com.example.charlotte.reminder.Fire7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity2 extends AppCompatActivity {

    private Realm mRealm;
    private ListView mListView;
    private CalendarView mCalendarView;

    /**
     * アクティビティクラスがインスタンス化される時に自動的に処理される
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //overrideする時は親クラスの同名のメソッドを呼び出す
        setContentView(R.layout.activity_main2);  //activity_main.xmlから実際のビューオブジェクトが生成される

        //mCalendar = findViewById(R.id.calendarView2);

        //fabを押した時の処理
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, TaskEditActivity.class));
            }
        });

        //fab3を押した時の処理
        FloatingActionButton fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
        });

        mCalendarView = findViewById(R.id.calendarView2);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                //別の日を選択した時(onSelectedDayChange())のリスト処理
                Date target = new Date(year+"/"+(month+1)+"/"+dayOfMonth);
                mRealm = Realm.getDefaultInstance();
                mListView = findViewById(R.id.listView2);
                final RealmResults<Task> tasks = mRealm.where(Task.class).between("date", target, target).findAll().sort("date");

                TaskAdapter adapter = new TaskAdapter(tasks);
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    /**
                     * リストビューの項目がタップされた時に呼ばれる、画面遷移するためのメソッド
                     * @param parent タップされた項目を含むリストビューのインスタンス
                     * @param view タップされた項目
                     * @param position タップされた項目のリスト上の位置
                     * @param id タップされた項目のID
                     */
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Task task = (Task) parent.getItemAtPosition(position); //リスト内の指定された位置に関するデータを取得する
                        startActivity(new Intent(MainActivity2.this, TaskEditActivity.class).putExtra("task_id", task.getId()));
                    }
                });
          }
        });

        //初回表示のリスト処理
        mRealm = Realm.getDefaultInstance();
        mListView = findViewById(R.id.listView2);

        Long date = mCalendarView.getDate();
        Date today = new Date(date);

        Calendar cal = Calendar.getInstance();//現在時刻のカレンダーを作成
        cal.setTime(today);//カレンダーをdateと同じ時間に設定
        //DateUtils.truncate() 使用不可
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date a = cal.getTime();


        final RealmResults<Task> tasks = mRealm.where(Task.class).between("date", a, a).findAll().sort("date");

        TaskAdapter adapter = new TaskAdapter(tasks);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * リストビューの項目がタップされた時に呼ばれる、画面遷移するためのメソッド
             * @param parent タップされた項目を含むリストビューのインスタンス
             * @param view タップされた項目
             * @param position タップされた項目のリスト上の位置
             * @param id タップされた項目のID
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position); //リスト内の指定された位置に関するデータを取得する
                startActivity(new Intent(MainActivity2.this, TaskEditActivity.class).putExtra("task_id", task.getId()));
            }
        });

    }


}
