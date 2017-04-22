package org.atheby.sudoku.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="score")
public class Score implements Comparable<Score> {

    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField
    private String userName;
    @DatabaseField
    private String level;
    @DatabaseField
    private int moves;
    @DatabaseField
    private int time;
    private int points;

    public Score() {}

    public Score(String userName, String level, int moves, int time) {
        this.userName = userName;
        this.level = level;
        this.moves = moves;
        this.time = time;
    }

    public int compareTo(Score score) {
        if(this.points < score.getPoints())
            return -1;
        if(this.points > score.getPoints())
            return 1;
        return 0;
    }

    public String toString() {
        return "User: " + userName + ", level: " + level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void calculatePoints() {
        double multiplier = 1;
        switch(level) {
            case "easy":
                multiplier = 1;
                break;
            case "medium":
                multiplier = 1.3;
                break;
            case "hard":
                multiplier = 1.8;
                break;
        }
        points = (int) ((1000 - moves - time) * multiplier);
    }
}
