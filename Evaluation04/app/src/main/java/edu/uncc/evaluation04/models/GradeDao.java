package edu.uncc.evaluation04.models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GradeDao {

    @Query("SELECT * FROM Grade")
    List<Grade> getAll();

    @Insert
    void insertAll(Grade... grades);

    @Delete
    void delete(Grade grade);

    @Query("DELETE FROM grade")
    void deleteAll();

    @Update
    void update(Grade grade);

}
