package org.atheby.sudoku.activity;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.atheby.sudoku.R;
import org.atheby.sudoku.model.Square;

import java.util.*;

public class GameActivity extends AppCompatActivity {

    private List<List<Integer>> numbers;
    private List<Square> squares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initNumbers();
        shuffle(5);
        createGrid();
        squaresTurnOn(30);
    }

    private void createGrid() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        LinearLayout squareWrapperLayout;
        LayoutParams squareWrapperParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int squareSize = getScreenWidth() / 9;
        squareWrapperParams.height = squareSize;
        squareWrapperParams.width = squareSize;
        squares = new ArrayList<>();
        for(int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                squareWrapperLayout = new LinearLayout(this);
                squareWrapperLayout.setLayoutParams(squareWrapperParams);
                Square sqr = new Square(this, row, col);
                sqr.setLabel(numbers.get(row).get(col));
                squares.add(sqr);
                squareWrapperLayout.addView(sqr);
                squareWrapperLayout.setPadding(2, 2, 2, 2);
                gridLayout.addView(squareWrapperLayout);
            }
        }
    }

    private OnClickListener squareOnClick(final Square sqr)  {
        return new OnClickListener() {
            public void onClick(View v) {
                sqr.increment();
            }
        };
    }

    private void squaresTurnOn(int toTurnOn) {
        do {
            Square sqr = squares.get(getRandom(0, squares.size() - 1));
            if(!sqr.isClickable()) {
                sqr.turnOn();
                sqr.setOnClickListener(squareOnClick(sqr));
                toTurnOn--;
            }
        } while(toTurnOn != 0);
    }

    private void initNumbers() {
        List<Integer> r0 = Arrays.asList(1, 2, 9, 4, 6, 5, 3, 8, 7);
        List<Integer> r1 = Arrays.asList(3, 4, 8, 1, 2, 7, 6, 5, 9);
        List<Integer> r2 = Arrays.asList(5, 6, 7, 9, 8, 3, 1, 2, 4);
        List<Integer> r3 = Arrays.asList(2, 1, 4, 3, 5, 6, 9, 7, 8);
        List<Integer> r4 = Arrays.asList(9, 7, 6, 2, 1, 8, 4, 3, 5);
        List<Integer> r5 = Arrays.asList(8, 3, 5, 7, 4, 9, 2, 1, 6);
        List<Integer> r6 = Arrays.asList(4, 9, 1, 5, 7, 2, 8, 6, 3);
        List<Integer> r7 = Arrays.asList(7, 8, 3, 6, 9, 1, 5, 4, 2);
        List<Integer> r8 = Arrays.asList(6, 5, 2, 8, 3, 4, 7, 9, 1);
        numbers = new ArrayList<>();
        numbers.add(r0);
        numbers.add(r1);
        numbers.add(r2);
        numbers.add(r3);
        numbers.add(r4);
        numbers.add(r5);
        numbers.add(r6);
        numbers.add(r7);
        numbers.add(r8);
    }

    private void shuffle(int iterations) {
        int[][] groups = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        List<Integer> tempRow;
        for(int i = 0; i < iterations; i++) {
            for(int x = 0; x < iterations; x++) {
                int row1, row2, group = getRandom(0, 2);
                do {
                    row1 = groups[group][getRandom(0, 2)];
                    row2 = groups[group][getRandom(0, 2)];
                } while(row1 == row2);
                tempRow = numbers.get(row1);
                numbers.set(row1, numbers.get(row2));
                numbers.set(row2, tempRow);
            }
            for(int x = 0; x < iterations; x++) {
                int tempNum, col1, col2, group = getRandom(0, 2);
                do {
                    col1 = groups[group][getRandom(0, 2)];
                    col2 = groups[group][getRandom(0, 2)];
                } while(col1 == col2);
                for(int y = 0; y < 9; y++) {
                    tempRow = numbers.get(y);
                    tempNum = tempRow.get(col1);
                    tempRow.set(col1, tempRow.get(col2));
                    tempRow.set(col2, tempNum);
                    numbers.set(y, tempRow);
                }
            }
        }
    }

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x < size.y ? size.x : size.y;
    }

    private int getRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
