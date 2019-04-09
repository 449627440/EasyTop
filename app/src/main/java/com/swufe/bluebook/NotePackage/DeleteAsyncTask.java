package com.swufe.bluebook.NotePackage;

import android.os.AsyncTask;

public class DeleteAsyncTask extends AsyncTask<Integer,Void,Void> {

    private NoteDB noteDB;

    public DeleteAsyncTask(NoteDB noteDB) {
        this.noteDB = noteDB;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        noteDB.deleteById(params[0]);
        return null;
    }

}
