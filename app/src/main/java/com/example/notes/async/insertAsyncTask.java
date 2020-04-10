package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.Model.Notes;
import com.example.notes.persistence.NoteDao;

public class insertAsyncTask extends AsyncTask<Notes, Void, Void>

{

    private NoteDao mNoteDao;

    public insertAsyncTask(NoteDao noteDao)
    {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
