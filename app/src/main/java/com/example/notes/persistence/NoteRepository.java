package com.example.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notes.Model.Notes;
import com.example.notes.async.DeleteAsyncTask;
import com.example.notes.async.UpdateAsyncTask;
import com.example.notes.async.insertAsyncTask;

import java.util.List;

public class NoteRepository
{

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context)
    {
        mNoteDatabase=NoteDatabase.getInstance(context);

    }

    public void insertNoteTask(Notes notes)
    {
        new insertAsyncTask(mNoteDatabase.getNoteDao()).execute(notes);

    }

    public void updateNote(Notes notes)
    {
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(notes);

    }

    public LiveData<List<Notes>> retrieveNotesTask() {
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public  void  deleteNote(Notes notes)
    {
     new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(notes);
    }


}
