package com.kreva.englishsynopsis.sql;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.kreva.englishsynopsis.dao.WordDAO;
import com.kreva.englishsynopsis.entity.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "word";
    private static AppDatabase appDatabase;

    public synchronized static AppDatabase getAppDatabase(Context context){
                if(appDatabase == null){
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }

        return appDatabase;
    }

    public abstract WordDAO wordDAO();
}
