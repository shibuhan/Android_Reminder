package com.example.charlotte.reminder.Fire7;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * タスク編集Activity
 */
public class TaskEditActivity extends AppCompatActivity {

    private Realm mRealm;
    EditText mDateEdit;
    EditText mTitleEdit;
    EditText mDetailEdit;
    Button mDelete;

    /**
     * アクティビティクラスがインスタンス化される時に自動的に処理される
     * @param savedInstanceState savedInstanceState(Activityの復元用)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        mRealm = Realm.getDefaultInstance();
        mDateEdit = findViewById(R.id.dateEdit);
        mTitleEdit = findViewById(R.id.titleEdit);
        mDetailEdit = findViewById(R.id.detailEdit);
        mDelete = findViewById(R.id.delete);

        //mDateEditにリスナーをつける
        mDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TaskEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得
                                mDateEdit.setText(String.format("%d/%02d/%02d", year, month+1, dayOfMonth));
                            }
                        },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );

                //dialogを表示
                datePickerDialog.show();

            }
        });

        long taskId = getIntent().getLongExtra("task_id", -1); //新規作成か項目をタップしたか判断
        if(taskId != -1) {
            //リストビューの項目をタップした時
            RealmResults<Task> results = mRealm.where(Task.class).equalTo("id", taskId).findAll();
            Task task = results.first();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdf.format(task.getDate());
            mDateEdit.setText(date);
            mTitleEdit.setText(task.getTitle());
            mDetailEdit.setText(task.getDetail());
            mDelete.setVisibility(View.VISIBLE); //「削除」ボタン表示
        } else {
            //新規作成時
            mDelete.setVisibility(View.INVISIBLE); //「削除」ボタン非表示
        }
    }

    /**
     *「保存」ボタンが押された時の動作
     * @param view ビュー
     */
    public void onSaveTapped(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dateParse = new Date();
        try {
            dateParse = sdf.parse(mDateEdit.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Date date = dateParse;

        long taskId = getIntent().getLongExtra("task_id", -1);
        if(taskId != -1) {
            final RealmResults<Task> results = mRealm.where(Task.class).equalTo("id", taskId).findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Task task = results.first();
                    task.setDate(date);
                    task.setTitle(mTitleEdit.getText().toString());
                    task.setDetail(mDetailEdit.getText().toString());
                }
            });

            Snackbar.make(findViewById(android.R.id.content), "更新しました", Snackbar.LENGTH_LONG).setAction("戻る", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }).setActionTextColor(Color.YELLOW).show();

        } else {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(Task.class).max("id");
                    long nextId = 0;
                    if (maxId != null) {
                        nextId = maxId.longValue() + 1;
                    }

                    Task task = realm.createObject(Task.class, new Long(nextId));
                    task.setDate(date);
                    task.setTitle(mTitleEdit.getText().toString());
                    task.setDetail(mDetailEdit.getText().toString());
                }
            });

            Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     *「削除」ボタンが押された時の動作
     * @param view ビュー
     */
    public void onDeleteTapped(View view){
        final long taskId = getIntent().getLongExtra("task_id", -1);
        if(taskId != -1) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
                    task.deleteFromRealm();
                }
            });

            Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show();
            finish();


        }
    }
}
