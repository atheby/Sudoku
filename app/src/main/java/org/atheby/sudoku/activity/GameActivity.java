package org.atheby.sudoku.activity;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.atheby.sudoku.R;
import org.atheby.sudoku.model.Square;

import java.util.*;

public class GameActivity extends AppCompatActivity {

    private List<List<Integer>> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initNumbers();
        createGrid();
    }

    private void createGrid() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        LinearLayout squareWrapperLayout;
        LayoutParams squareWrapperParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int squareSize = getScreenWidth() / 9;
        squareWrapperParams.height = squareSize;
        squareWrapperParams.width = squareSize;
        for(int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                squareWrapperLayout = new LinearLayout(this);
                squareWrapperLayout.setLayoutParams(squareWrapperParams);
                Square sqr = new Square(this, row, col);
                sqr.setLabel(numbers.get(row).get(col));
                squareWrapperLayout.addView(sqr);
                squareWrapperLayout.setPadding(2, 2, 2, 2);
                gridLayout.addView(squareWrapperLayout);
            }
        }
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

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x < size.y ? size.x : size.y;
    }
}
