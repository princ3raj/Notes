package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.Model.Notes;
import com.example.notes.persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Notes, Void, Void>

{

    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao noteDao)
    {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
