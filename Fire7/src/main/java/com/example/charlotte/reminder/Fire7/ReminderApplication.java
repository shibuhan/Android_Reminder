package com.example.charlotte.reminder.Fire7;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 *
 */
public class ReminderApplication extends Application {

    /**
     * アクティビティクラスがインスタンス化される時に自動的に処理される
     */
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
