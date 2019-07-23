package com.example.charlotte.reminder.Fire7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private ListView mListView;

    /**
     * アクティビティクラスがインスタンス化される時に自動的に処理される
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //overrideする時は親クラスの同名のメソッドを呼び出す
        setContentView(R.layout.activity_main);  //activity_main.xmlから実際のビューオブジェクトが生成される
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TaskEditActivity.class)); //画面遷移するためのメソッドstartActivity()
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class)); //画面遷移するためのメソッドstartActivity()
            }
        });

        mRealm = Realm.getDefaultInstance();

        mListView = findViewById(R.id.listView);
        RealmResults<Task> tasks = mRealm.where(Task.class).findAll().sort("date"); //日付順にソート
        TaskAdapter adapter = new TaskAdapter(tasks);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * リストビューの項目がタップされた時に呼ばれる、画面遷移するためのメソッドstartActivity()
             * @param parent タップされた項目を含むリストビューのインスタンス
             * @param view タップされた項目
             * @param position タップされた項目のリスト上の位置
             * @param id タップされた項目のID
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position); //リスト内の指定された位置に関するデータを取得する
                startActivity(new Intent(MainActivity.this, TaskEditActivity.class).putExtra("task_id", task.getId()));
            }
        });
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
