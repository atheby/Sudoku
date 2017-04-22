package org.atheby.sudoku.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import org.atheby.sudoku.R;

public class Square extends Button {

    private int row;
    private int column;
    private int group;
    private int label;
    private String[] labels= {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public Square(Context context, int row, int col, int group) {
        super(context);
        this.row = row;
        this.column = col;
        this.group = group;
        setClickable(false);
        setStyle();
    }

    public void turnOn() {
        setClickable(true);
        setLabel(0);
    }

    public void increment() {
        if(label < 9)
            label++;
        else
            label = 0;
        setLabel(label);
    }

    public void setDuplicate(Boolean duplicate) {
        if(duplicate)
            if(isClickable())
                setTextColor(ContextCompat.getColor(getContext(), R.color.colorSquareTextDuplicateClickable));
            else
                setTextColor(ContextCompat.getColor(getContext(), R.color.colorSquareTextDuplicate));
        else
            if(isClickable())
                setTextColor(ContextCompat.getColor(getContext(), R.color.colorSquareTextClickable));
            else
                setTextColor(ContextCompat.getColor(getContext(), R.color.colorSquareText));
    }

    private void setStyle() {
        setPadding(0, 0, 0, 0);
        setTextSize(25);
        setTextColor(ContextCompat.getColor(getContext(), R.color.colorSquareText));
        if(group % 2 == 0)
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorSquareBackground));
        else
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorSquareBackgroundDark));
    }

    public void setDefaultTextColor() {
        setTextColor(ContextCompat.getColor(getContext(), R.color.colorSquareText));
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
        setText(labels[label]);
    }
}
