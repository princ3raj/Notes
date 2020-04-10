package com.example.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Model.Notes;

import java.util.List;

@Dao
public interface NoteDao {



    @Insert
    long[] insertNotes(Notes... notes);


    @Query("SELECT * FROM notes ")
    LiveData<List<Notes>> getNotes();

    @Query("SELECT * FROM notes WHERE Title LIKE :Title")
    List<Notes> getNoteWithCustomQuery(String Title);


    @Delete
    int delete(Notes... notes);



    @Update
    int update(Notes... notes);





}
