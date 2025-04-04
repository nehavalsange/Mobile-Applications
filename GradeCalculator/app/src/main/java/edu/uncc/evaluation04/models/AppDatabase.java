package edu.uncc.evaluation04.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Grade.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GradeDao gradeDao();
}
