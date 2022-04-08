package com.kreva.englishsynopsis.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_list")
public class Word {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int uid;
    @ColumnInfo(name = "active")
    private int active;
    @ColumnInfo(name = "english")
    private String englishWord;
    @ColumnInfo(name = "russian")
    private String russianWord;

    public Word() {
    }

    @Ignore
    public Word(int active, String englishWord, String russianWord) {
        this.active = active;
        this.englishWord = englishWord;
        this.russianWord = russianWord;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }

    public void setRussianWord(String russianWord) {
        this.russianWord = russianWord;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Word{" +
                "uid=" + uid +
                ", active=" + active +
                ", englishWord='" + englishWord + '\'' +
                ", russianWord='" + russianWord + '\'' +
                '}';
    }
}
