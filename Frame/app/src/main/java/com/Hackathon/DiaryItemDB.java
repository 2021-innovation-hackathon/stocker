package com.Hackathon;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;

@Database(entities = {DiaryItem.class}, version=1)
public abstract class DiaryItemDB extends RoomDatabase {
    public static DiaryItemDB instance = null;

    public abstract DiaryItemDao diaryItemDao();

    public static DiaryItemDB getInstance(Context context) throws IOException, BiffException {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DiaryItemDB.class, "diary.db").build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
