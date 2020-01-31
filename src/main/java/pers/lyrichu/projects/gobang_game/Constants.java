package pers.lyrichu.projects.gobang_game;

import java.awt.Color;

/**
 * 用于存放一些常量
 */
public class Constants {

  public static int PANEL_WIDTH = 518;
  public static int PANEL_HEIGHT = 565;
  public static final int CHESSBOARD_SIZE = 15; // 棋盘个数

  public static final Color BG_COLOR = new Color(255, 245, 186);//棋盘背景色
  public static final Color LINE_COLOR = new Color(66, 66, 66);//棋盘线条颜色
  public static final int MARGIN = 20;//棋盘边缘长度

  // 存放一些字符串常量
  public static final String GAME_TITLE = "人机对战五子棋";
  public static final String GAME_OPTION_TITLE = "选项";
  public static final String REPLAY_TEXT = "重玩一盘";
  public static final String MACHINE_FIRST_TEXT = "机器先手";
  public static final String HUMAN_FIRST_TEXT = "人类先手";
  public static final String BACK_STEP_TEXT = "悔棋";
  public static final String MACHINE_WIN_MESSAGE = "机器获胜!️";
  public static final String MACHINE_WIN_TITLE = "很遗憾,您输了/(ㄒoㄒ)/~~";
  public static final String HUMAN_WIN_MESSAGE = "人类获胜!";
  public static final String HUMAN_WIN_TITLE = "恭喜,您赢了O(∩_∩)O~~";



  // 先手信息
  public enum ChessFirst {
    HUMAN, // 人类先手
    MACHINE // 机器先手
  }

  // 棋盘每格落子信息
  public enum ChessCellStatus {
    HUMAN, // 人类落子
    MACHINE, // 机器落子
    EMPTY // 未落子
  }

}
