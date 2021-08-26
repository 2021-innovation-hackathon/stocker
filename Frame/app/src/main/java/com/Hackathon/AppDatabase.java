package com.Hackathon;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DiaryItem.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaryItemDao diaryItemDao();
}
