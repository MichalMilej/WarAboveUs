package fxml;

public class TableDataType {
    private int position;
    private String playerName;
    private double score;
    private String scoreWithUnits;

    public TableDataType(int position, String playerName, double score) {
        this.position = position;
        this.playerName = playerName;
        this.score = score;
    }

    public TableDataType(int position, String playerName, String scoreWithUnits) {
        this.position = position;
        this.playerName = playerName;
        this.scoreWithUnits = scoreWithUnits;
    }

    public TableDataType(String playerName, double score) {
        this.playerName = playerName;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setScoreWithUnits(String units) {
        this.scoreWithUnits = score + units;
    }

    public String getScoreWithUnits() {
        return scoreWithUnits;
    }
}
