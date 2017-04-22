package org.atheby.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import org.atheby.sudoku.R;
import org.atheby.sudoku.helper.DatabaseHelper;
import org.atheby.sudoku.model.Score;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ScoresActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private TableLayout tableLayout;
    private Dao<Score, Integer> scoresDao;
    private List<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        String userName = getIntent().getStringExtra("userName");
        String level = getIntent().getStringExtra("level");
        int moves = Integer.parseInt(getIntent().getStringExtra("moves"));
        int time = Integer.parseInt(getIntent().getStringExtra("time"));
        Score newScore = new Score(userName, level, moves, time);
        try {
            scoresDao = getHelper().getScoreDao();
            scoresDao.create(newScore);
            scores = scoresDao.queryForAll();
            for(Score score: scores)
                score.calculatePoints();
            Collections.sort(scores);
            Collections.reverse(scores);
            for(int x = 0; x < scores.size(); x++) {
                if(x > 9) break;
                tableLayout.addView(getScoreRow(scores.get(x)));
            }
        } catch (SQLException e) {}
        Button newGameBtn = (Button) findViewById(R.id.newGameBtn);
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoresActivity.this, GameActivity.class);
                intent.putExtra("level", getIntent().getStringExtra("level"));
                intent.putExtra("userName", getIntent().getStringExtra("userName"));
                startActivity(intent);
            }
        });
    }

    private View getScoreRow(Score score) {
        TableRow scoreRow = new TableRow(this);

        TextView userName = new TextView(this);
        userName.setPadding(0, 10, 10, 10);
        userName.setText(score.getUserName());
        scoreRow.addView(userName);
        TextView level = new TextView(this);
        level.setPadding(10, 10, 10, 10);
        level.setGravity(Gravity.CENTER);
        level.setText(score.getLevel());
        scoreRow.addView(level);
        TextView moves = new TextView(this);
        moves.setPadding(10, 10, 10, 10);
        moves.setGravity(Gravity.CENTER);
        moves.setText(((Integer)score.getMoves()).toString());
        scoreRow.addView(moves);
        TextView time = new TextView(this);
        time.setPadding(10, 10, 10, 10);
        time.setGravity(Gravity.CENTER);
        String seconds;
        if(score.getTime() % 60 < 10)
            seconds = "0" + ((Integer)(score.getTime() % 60)).toString();
        else
            seconds = ((Integer)(score.getTime() % 60)).toString();
        time.setText(((Integer)(
                score.getTime() / 60)).toString() + ":" + seconds);
        scoreRow.addView(time);
        TextView points = new TextView(this);
        points.setPadding(10, 10, 10, 10);
        points.setGravity(Gravity.CENTER);
        points.setText(((Integer)score.getPoints()).toString());
        scoreRow.addView(points);

        return scoreRow;
    }
}
