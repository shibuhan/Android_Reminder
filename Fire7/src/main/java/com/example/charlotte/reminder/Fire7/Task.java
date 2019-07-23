package com.example.charlotte.reminder.Fire7;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * データを格納する「モデル」
 */
public class Task extends RealmObject {

    /**
     * id ID
     * 主キー
     */
    @PrimaryKey
    private long id;

    /**
     * date 期限
     */
    private Date date;

    /**
     * title タイトル
     */
    private String title;

    /**
     * detail 内容
     */
    private String detail;

    /**
     * IDを取得します
     * @return id ID
     */
    public long getId() {
        return id;
    }

    /**
     * IDをセットします
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 期限を取得します
     * @return date 実行日時
     */
    public Date getDate() {
        return date;
    }

    /**
     * 期限をセットします
     * @param date 実行日時
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * タイトルを取得します
     * @return title タイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * タイトルをセットします
     * @param title タイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 内容を取得します
     * @return detail 内容
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 内容をセットします
     * @param detail 内容
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}
