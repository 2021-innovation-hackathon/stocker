package com.Hackathon;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryItemDao {
    @Query("SELECT * FROM DiaryItem")
    List<DiaryItem> getAll();

    @Insert
    void insert(DiaryItem diaryItem);

    @Update
    void update(DiaryItem diaryItem);

    @Delete
    void delete(DiaryItem diaryItem);
}
