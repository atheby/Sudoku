package org.atheby.sudoku.model;

import android.content.Context;
import android.widget.Button;

public class Square extends Button {

    private int row;
    private int column;

    public Square(Context context) {
        super(context);
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
}
