package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.Model.Notes;
import com.example.notes.persistence.NoteDatabase;
import com.example.notes.persistence.NoteRepository;
import com.example.notes.util.Utility;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnClickListener , TextWatcher {


    //static variables
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private static final String TAG = "NoteActivity";


    //UI componenets
    private TextView mViewTitle;
    private EditText mEditText;
    private LinedEditText mLinedEditText;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;


    //Variables
    private boolean mIsNewNote;
    private Notes mNoteInitial;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;
    private Notes mFinalNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = (LinedEditText) findViewById(R.id.note_text);
        mEditText = (EditText) findViewById(R.id.note_edit_title);
        mViewTitle = (TextView) findViewById(R.id.note_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.check_toolbar);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);


        mNoteRepository = new NoteRepository(this);

        setListeners();


        if (getIncomingIntent()) {
            // this is a new note (EDIT MODE)
            setNewNoteProperties();
            enableEditMode();

        } else {
            // this is not a new note (VIEW MODE)
            setNoteProperties();
            disableContentInteraction();
        }


    }

    private void saveChanges()
    {
        if (mIsNewNote)
        {
            saveNewNote();
        }
        else
            {

                updateNewNote();


        }
    }


    private void saveNewNote()
    {
        mNoteRepository.insertNoteTask(mFinalNote);

    }

    private  void updateNewNote()
    {
        mNoteRepository.updateNote(mFinalNote);
    }


    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService
                (Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if (view == null) {
            view = new View(this);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }


    private void enableEditMode() {
        mCheckContainer.setVisibility(View.VISIBLE);
        mBackArrowContainer.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);
        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();


    }

    private void disableEditMode() {
        mCheckContainer.setVisibility(View.GONE);
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);
        mMode = EDIT_MODE_DISABLED;


        disableContentInteraction();

        String temp = mLinedEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            mFinalNote.setTitle(mEditText.getText().toString());
            mFinalNote.setContent(mLinedEditText.getText().toString());
            String timestamp = Utility.getCurrentTimeStamps();
            mFinalNote.setTimeStamps(timestamp);


            // If the note was altered, save it.
            if (!mFinalNote.getContent().equals(mNoteInitial.getContent())
                    || !mFinalNote.getTitle().equals(mNoteInitial.getTitle())) {
                // mIsNewNote=true;
                saveChanges();
            }

        }
    }


    private void setListeners() {
        mGestureDetector = new GestureDetector(this, this);
        mLinedEditText.setOnTouchListener(this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditText.addTextChangedListener(this);
    }


    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            mNoteInitial = getIntent().getParcelableExtra("selected_note");

            mFinalNote= new Notes();
            mFinalNote.setId(mNoteInitial.getId());
            mFinalNote.setTitle(mNoteInitial.getTitle());
            mFinalNote.setTimeStamps(mNoteInitial.getTimeStamps());
            mFinalNote.setContent(mNoteInitial.getContent());



            mMode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void setNewNoteProperties() {
        mViewTitle.setText("Note Title");
        mEditText.setText("Note Title");
        mNoteInitial = new Notes();
        mFinalNote = new Notes();
        mNoteInitial.setTitle("Note Title");
        mFinalNote.setTitle("Note Title");


    }

    private void setNoteProperties() {
        mViewTitle.setText(mNoteInitial.getTitle());
        mEditText.setText(mNoteInitial.getTitle());
        mLinedEditText.setText(mNoteInitial.getContent());
    }

    private void disableContentInteraction() {
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableContentInteraction() {
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: double tap called");
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.check_toolbar: {
                hideSoftKeyBoard();
                disableEditMode();


                break;
            }
            case R.id.note_edit_title: {
                enableEditMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;
            }
            case R.id.toolbar_back_arrow: {
                finish();
                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED)
            onClick(mCheck);
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if (mMode == EDIT_MODE_ENABLED)
            enableEditMode();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());
    }


        @Override
        public void afterTextChanged (Editable s)
        {

        }

}
