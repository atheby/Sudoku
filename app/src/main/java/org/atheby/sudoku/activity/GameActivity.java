package org.atheby.sudoku.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import org.atheby.sudoku.R;
import org.atheby.sudoku.model.Square;

import java.util.*;

public class GameActivity extends Activity {

    private LinearLayout gameLayout;
    private List<List<Integer>> numbers;
    private List<Square> squares;
    private Chronometer chronometer;
    private long time;
    private TextView movesCounter;
    private int moves;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        level = getIntent().getStringExtra("level");
        gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
        initNumbers();
        createTopLayout();
        createGrid();
        createBottomLayout();
        newGame();
    }

    private void newGame() {
        shuffle(5);
        setDefaultState();
        switch(level) {
            case "easy":
                randomSquaresTurnOn(35);
                break;
            case "medium":
                randomSquaresTurnOn(40);
                break;
            case "hard":
                randomSquaresTurnOn(45);
                break;
        }
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void reset() {
        for(Square sqr: squares) {
            if (sqr.isClickable())
                sqr.setLabel(0);
            sqr.setDefaultTextColor();
        }
    }

    private void createTopLayout() {
        LinearLayout topLayout = new LinearLayout(this);
        topLayout.setPadding(30, 10, 30, 10);
        TextView movesLabel = new TextView(this);
        movesLabel.setText("Moves:");
        movesLabel.setTextSize(20);
        movesLabel.setTextColor(Color.WHITE);
        movesCounter = new TextView(this);
        movesCounter.setTextSize(20);
        movesCounter.setTextColor(Color.WHITE);
        movesCounter.setPadding(20, 0, 20, 0);
        chronometer = new Chronometer(this);
        chronometer.setTextSize(20);
        chronometer.setTextColor(Color.WHITE);
        chronometer.setPadding(20, 0, 20, 0);
        topLayout.addView(movesLabel);
        topLayout.addView(movesCounter);
        topLayout.addView(chronometer);
        gameLayout.addView(topLayout, 0);
    }

    private void createBottomLayout() {
        LinearLayout buttonsLayout = new LinearLayout(this);
        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        buttonsLayout.setLayoutParams(params);
        Button resetBtn = new Button(this);
        resetBtn.setText("Reset");
        Button newGameBtn = new Button(this);
        newGameBtn.setText("New game");
        resetBtn.setLayoutParams(params);
        newGameBtn.setLayoutParams(params);
        buttonsLayout.addView(resetBtn);
        buttonsLayout.addView(newGameBtn);
        gameLayout.addView(buttonsLayout);
        newGameBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        resetBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
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
        int rowCounter = 0;
        for(int row = 0; row < 9; row++) {
            if(row % 3 == 0 && row != 0)
                rowCounter += 3;
            int colCounter = 0;
            for (int col = 0; col < 9; col++) {
                if(col % 3 == 0 && col != 0)
                    colCounter++;
                squareWrapperLayout = new LinearLayout(this);
                squareWrapperLayout.setLayoutParams(squareWrapperParams);
                Square sqr = new Square(this, row, col, rowCounter + colCounter);
                squares.add(sqr);
                squareWrapperLayout.addView(sqr);
                squareWrapperLayout.setPadding(2, 2, 2, 2);
                gridLayout.addView(squareWrapperLayout);
            }
        }
    }

    private void setDefaultState() {
        moves = 0;
        movesCounter.setText(((Integer) moves).toString());
        for(Square sqr: squares) {
            sqr.setLabel(numbers.get(sqr.getRow()).get(sqr.getColumn()));
            sqr.setClickable(false);
            sqr.setDefaultTextColor();
        }
    }

    private OnClickListener squareOnClick(final Square sqr)  {
        return new OnClickListener() {
            public void onClick(View v) {
                sqr.increment();
                incrementMoves();
                checkResult();
            }
        };
    }

    private void randomSquaresTurnOn(int toTurnOn) {
        do {
            Square sqr = squares.get(getRandom(0, squares.size() - 1));
            if(!sqr.isClickable()) {
                sqr.turnOn();
                sqr.setOnClickListener(squareOnClick(sqr));
                toTurnOn--;
            }
        } while(toTurnOn != 0);
    }

    private void squaresTurnOff() {
        for(Square sqr: squares)
            sqr.setClickable(false);
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

    private void checkResult() {
        List<Square> duplicates = new ArrayList<>();
        for(Square sqr1: squares) {
            for(Square sqr2: squares) {
                if(sqr1.equals(sqr2))
                    continue;
                if(sqr1.getRow() == sqr2.getRow() && sqr1.getLabel() == sqr2.getLabel() ||
                        sqr1.getColumn() == sqr2.getColumn() && sqr1.getLabel() == sqr2.getLabel() ||
                        sqr1.getGroup() == sqr2.getGroup() && sqr1.getLabel() == sqr2.getLabel())
                    duplicates.add(sqr1);
            }
        }
        boolean isFinished = true;
        for(Square sqr: squares) {
            if(sqr.getLabel() == 0)
                isFinished = false;
            if (duplicates.contains(sqr) && !level.equals("hard"))
                sqr.setDuplicate(true);
            else
                sqr.setDuplicate(false);
        }
        if(duplicates.size() == 0 && isFinished) {
            chronometer.stop();
            time = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
            squaresTurnOff();
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game finished");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(GameActivity.this, ScoresActivity.class);
                intent.putExtra("level", getIntent().getStringExtra("level"));
                intent.putExtra("userName", getIntent().getStringExtra("userName"));
                intent.putExtra("moves", movesCounter.getText());
                intent.putExtra("time", ((Long) time).toString());
                startActivity(intent);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void incrementMoves() {
        moves++;
        movesCounter.setText(((Integer) moves).toString());
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
