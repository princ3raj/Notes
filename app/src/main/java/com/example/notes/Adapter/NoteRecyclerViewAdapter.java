package com.example.notes.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Model.Notes;
import com.example.notes.R;
import com.example.notes.util.Utility;

import java.util.ArrayList;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "NoteRecyclerViewAdapter";
    private ArrayList<Notes> mNotes= new ArrayList<>();
    private OnNoteListener mOnNoteListener;

    public NoteRecyclerViewAdapter(ArrayList<Notes> notes, OnNoteListener onNoteListener)
    {
        mNotes = notes;
        mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item_layout,parent,false);
        return new ViewHolder(view,mOnNoteListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        try{

            String month=mNotes.get(position).getTimeStamps().substring(0,2);
            month= Utility.getMonthFromNumber(month);
            String year=mNotes.get(position).getTimeStamps().substring(3);
            String timestamp= month + " " + year;
            holder.noteTitle.setText(mNotes.get(position).getTitle());
            holder.Timestamps.setText(timestamp);

        }catch (NullPointerException e)
        {
            Log.e(TAG, "onBindViewHolder: NullPointerExcception"+ e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView noteTitle,Timestamps;

        OnNoteListener mOnNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            noteTitle=itemView.findViewById(R.id.note_title);
            Timestamps=itemView.findViewById(R.id.note_timestamps);
            mOnNoteListener=onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

         mOnNoteListener.onNoteClick(getAdapterPosition());

        }




    }


    public interface OnNoteListener
    {
        void onNoteClick(int position);

    }



}
