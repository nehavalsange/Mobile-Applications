package edu.uncc.assignment14.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BillDao {
    @Query("SELECT * FROM Bill")
    List<Bill> getAll();

    @Insert
    void insertAll(Bill... bills);

    @Delete
    void delete(Bill bill);

    @Query("DELETE FROM Bill")
    void deleteAll();

    @Update
    void update(Bill bill);

}
