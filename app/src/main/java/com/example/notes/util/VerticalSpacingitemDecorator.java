package com.example.notes.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingitemDecorator extends RecyclerView.ItemDecoration
{

    private final int VerticalspaceHeight;

    public VerticalSpacingitemDecorator(int verticalspaceHeight) {
        VerticalspaceHeight = verticalspaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {

        outRect.bottom=VerticalspaceHeight;


    }
}
