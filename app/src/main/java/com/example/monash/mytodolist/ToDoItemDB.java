package com.example.monash.mytodolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ToDoItem.class}, version = 1, exportSchema = false)
@TypeConverters({RoomCoverter.class})

public abstract class ToDoItemDB extends RoomDatabase {

    //variables
    private static final String Name = "todoitem_db";
    private static ToDoItemDB DBINSTANCE;

    public static ToDoItemDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (MainActivity.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ToDoItemDB.class, Name).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }

    public abstract ItemsDAO toDoItemDao();
}
