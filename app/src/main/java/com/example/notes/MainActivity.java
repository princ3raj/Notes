package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.notes.Adapter.NoteRecyclerViewAdapter;
import com.example.notes.Adapter.NoteRecyclerViewAdapter.OnNoteListener;
import com.example.notes.Model.Notes;
import com.example.notes.persistence.NoteRepository;
import com.example.notes.util.VerticalSpacingitemDecorator;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity implements OnNoteListener , View.OnClickListener {

    private RecyclerView mRecyclerView;
    private static final String TAG = "MainActivity";

    private NoteRecyclerViewAdapter mNoteRecyclerViewAdapter;

    private ArrayList<Notes> mNotes = new ArrayList<>();

    private NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepository=new NoteRepository(this);


        InitRecyclerview();

        retrieveNotes();
        //insertfakeNotes();

        //Toolbar toolbar= (Toolbar)findViewById(R.id.notes_toolbar);
        //pass the toolbar into setSupportActionBar

        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");

    }

    private void retrieveNotes()
    {
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Notes>>()
        {
            @Override
            public void onChanged(@Nullable List<Notes> notes)
            {
                if(mNotes.size() > 0)
                {
                    mNotes.clear();
                }
                if(notes != null){
                    mNotes.addAll(notes);
                }
                mNoteRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }



    private void insertfakeNotes() {
        for (int i = 0; i < 1000; i++) {
            Notes notes = new Notes();
            notes.setTitle("list: " + i);
            notes.setContent("content: " + i);
            notes.setTimeStamps("Dec 25 2018");
            mNotes.add(notes);

        }
        mNoteRecyclerViewAdapter.notifyDataSetChanged();

    }


    private void InitRecyclerview()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingitemDecorator verticalSpacingitemDecorator = new VerticalSpacingitemDecorator(10);
        mRecyclerView.addItemDecoration(verticalSpacingitemDecorator);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mNoteRecyclerViewAdapter = new NoteRecyclerViewAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNoteRecyclerViewAdapter);


    }


    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: " + position);

        Intent intent = new Intent(this, NoteActivity.class);

        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }

    private void deleteNotes(Notes notes) {
        mNotes.remove(notes);
        mNoteRecyclerViewAdapter.notifyDataSetChanged();

        mNoteRepository.deleteNote(notes);


    }


    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT)
    {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
        {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
        {
            deleteNotes(mNotes.get(viewHolder.getAdapterPosition()));
        }

    } ;
}
