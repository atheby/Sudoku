package org.atheby.sudoku.model;

import android.content.Context;
import android.widget.Button;
import org.atheby.sudoku.R;

public class Square extends Button {

    private int row;
    private int column;
    private int label;
    private String[] labels= {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public Square(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.column = col;
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

    private void setStyle() {
        setBackgroundColor(getResources().getColor(R.color.colorSquareBackground));
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

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
        setText(labels[label]);
    }
}
