package org.atheby.sudoku.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.atheby.sudoku.R;
import org.atheby.sudoku.model.Score;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "sudoku.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Score, Integer> scoreDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Score.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Could not create a database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) { }

    public Dao<Score, Integer> getScoreDao() throws SQLException {
        if (scoreDao == null) {
            scoreDao = getDao(Score.class);
        }
        return scoreDao;
    }

    @Override
    public void close() {
        super.close();
        scoreDao = null;
    }
}
