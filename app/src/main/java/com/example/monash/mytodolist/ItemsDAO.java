package com.example.monash.mytodolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemsDAO {

    @Query("SELECT * FROM todoitems ORDER BY date DESC")
    List<ToDoItem> listAll();

    @Query("DELETE FROM todoitems")
    void deleteAll();

    @Insert
    void insert(ToDoItem toDoItem);
}
