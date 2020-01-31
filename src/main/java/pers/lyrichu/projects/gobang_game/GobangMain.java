package pers.lyrichu.projects.gobang_game;

/**
 * 游戏入口
 */
public class GobangMain {

  public static void main(String[] args) {
    startGame();
  }

  private static void startGame() {
    new UI().init();
  }

}
