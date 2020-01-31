package pers.lyrichu.projects.gobang_game;

import pers.lyrichu.projects.gobang_game.Constants.ChessCellStatus;

public class Location {

  private int x; //某个棋盘位置横坐标，0-14
  private int y; //某个棋盘位置纵坐标，0-14

  private ChessCellStatus cellStatus; // 当前位置落子情况
  private int score; //对该位置的打的分数

  public Location(int x, int y, ChessCellStatus cellStatus) {
    this.x = x;
    this.y = y;
    this.cellStatus = cellStatus;
  }
  public Location(int x, int y, ChessCellStatus cellStatus, int score) {
    this(x, y, cellStatus);
    this.score = score;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public ChessCellStatus getCellStatus() {
    return cellStatus;
  }

  public void setCellStatus(ChessCellStatus cellStatus) {
    this.cellStatus = cellStatus;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}

