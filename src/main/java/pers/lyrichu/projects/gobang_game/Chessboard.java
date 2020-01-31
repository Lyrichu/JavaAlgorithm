package pers.lyrichu.projects.gobang_game;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import static pers.lyrichu.projects.gobang_game.Constants.*;

public class Chessboard extends JPanel {

  // 棋盘格子大小
  private int cellSize;
  //棋盘上所有可以落子的位置坐标等信息
  private ArrayList<Location> locationList = new ArrayList<>();

  //初始化棋盘
  public void init() {
    locationList.clear();
    repaint();
  }

  //覆盖paint方法
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    cellSize = getCellSize();
    drawChessboard(g);
    drawChessman(g);
  }

  //画棋盘
  private void drawChessboard(Graphics g) {
    //先画背景
    g.setColor(BG_COLOR);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    //画线
    g.setColor(LINE_COLOR);
    for(int i = 0; i < CHESSBOARD_SIZE; i++){
      g.drawLine(MARGIN, MARGIN + i*cellSize, this.getWidth() - MARGIN, MARGIN + i*cellSize);//画横线
      g.drawLine(MARGIN + i * cellSize, MARGIN,
          MARGIN + i * cellSize, this.getHeight() - MARGIN);//画纵线
    }
  }

  //画棋子
  private void drawChessman(Graphics g) {
    for(Location loc : locationList) {
      // 根据先后手以及当前棋盘落子情况,决定棋子颜色
      if(loc.getCellStatus() == ChessCellStatus.HUMAN) {
        if (Chess.chessFirst == ChessFirst.HUMAN) {
          g.setColor(Color.BLACK);
        } else {
          g.setColor(Color.WHITE);
        }
      } else {
        if (Chess.chessFirst == ChessFirst.MACHINE) {
          g.setColor(Color.BLACK);
        } else {
          g.setColor(Color.WHITE);
        }
      }
      //画棋子
      g.fillOval(MARGIN + cellSize * loc.getX() - cellSize / 2,
          MARGIN + cellSize * loc.getY() - cellSize / 2, cellSize, cellSize);

    }
  }

  //落子
  public void addChessman(int x, int y, ChessCellStatus cellStatus) {
    addChessman(new Location(x, y,cellStatus));
    repaint();
  }

  //落子
  public void addChessman(Location loc) {
    locationList.add(loc);
    repaint();
  }

  // 悔棋
  public void backStepChessman() {
    if (isEmpty()) {
      return;
    }

    int len = locationList.size();
    if (len >= 2) {
      Location latestLoc = locationList.get(len - 1);
      Location secondLatestLoc = locationList.get(len - 2);
      if (latestLoc.getCellStatus() == ChessCellStatus.MACHINE
          && secondLatestLoc.getCellStatus() == ChessCellStatus.HUMAN) {
        // 移除机器落子
        locationList.remove(latestLoc);
        Chess.chessboard[latestLoc.getX()][latestLoc.getY()] = ChessCellStatus.EMPTY;
        // 移除人类落子
        locationList.remove(secondLatestLoc);
        Chess.chessboard[secondLatestLoc.getX()][secondLatestLoc.getY()] = ChessCellStatus.EMPTY;
      }
      repaint();
    }
  }

  //计算棋盘每个小格子的大小
  public int getCellSize() {
    return (this.getWidth() - 2 * MARGIN) / (CHESSBOARD_SIZE - 1);
  }

  //判断棋盘是否还没有棋子
  public boolean isEmpty() {
    return locationList.isEmpty();
  }

}

