package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.Model.Notes;
import com.example.notes.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Notes, Void, Void>

{

    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao noteDao)
    {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.update(notes);
        return null;
    }
}
