package pers.lyrichu.projects.snake_game;

import javax.swing.JFrame;

/**
 * 一个简单的贪吃蛇游戏,refercence:https://www.bilibili.com/video/av67722483
 * java -version >= 1.8
 */
public class SnakeMain {

  public static void main(String[] args) {
    JFrame jFrame = new JFrame();
    jFrame.setBounds(10,10,900,720);
    jFrame.setResizable(false);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.add(new SnakePannel());
    jFrame.setVisible(true);
  }
}
