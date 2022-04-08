package com.kreva.englishsynopsis.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.kreva.englishsynopsis.entity.Word;

import java.util.List;

@Dao
public interface WordDAO {
    @Query("SELECT * FROM word_list")
    List<Word> getAllWord();

    @Query("SELECT * FROM word_list")
    LiveData<List<Word>> getAllLiveWord();

    @Query("UPDATE word_list SET active = :active WHERE _id LIKE :uid")
    void editWord(int active,int uid);

    @Query("SELECT * FROM word_list WHERE _id LIKE :uid LIMIT 1")
    Word findById(int uid);

    @Insert
    void insertWord(Word... words);

    @Delete
    void delete(Word word);

}
