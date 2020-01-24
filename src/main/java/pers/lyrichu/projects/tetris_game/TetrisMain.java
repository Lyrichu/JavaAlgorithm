package pers.lyrichu.projects.tetris_game;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisMain extends JFrame {

  private static final long serialVersionUID = 8995729671326316569L;

  private final int PANEL_WIDTH = 560;
  private final int PANEL_HEIGHT = 700;

  private final String GAME_TITLE_TEXT = "俄罗斯方块";

  private final String ABOUT_MESSAGE_TEXT = "简单的俄罗斯方块小游戏";
  private final String ABOUT_TEXT = "关于";

  private final String BLOCK_V4_TEXT = "v4";
  private final String BLOCK_V6_TEXT = "v6";

  private final String GAME_TEXT = "游戏";
  private final String NEW_GAME_TEXT = "新游戏";
  private final String GAME_PAUSE_TEXT = "暂停";
  private final String GAME_CONTINUE_TEXT = "继续";
  private final String GAME_EXIT_TEXT = "退出";

  private final String MODE_TEXT = "模式";
  private final String MODE_V4_TEXT = "4方块";
  private final String MODE_V6_TEXT = "6方块";

  private final String SPEED_TEXT = "速度";
  private final String SPEED_SLOW_TEXT = "慢速";
  private final String SPEED_LITTLE_FAST_TEXT = "较快";
  private final String SPEED_FAST_TEXT = "快速";
  private final String SPEED_VERY_FAST_TEXT = "极速";

  private final String HELP_TEXT = "帮助";


  private TetrisPanel tetrisPanel = new TetrisPanel();

  /**
   * 新游戏
   */
  private ActionListener newGameActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      TetrisMain.this.tetrisPanel.initPanel();
    }
  };

  /**
   * 暂停游戏
   */
  private ActionListener pauseActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      TetrisMain.this.tetrisPanel.setPause(true);
    }
  };

  /**
   * 暂停之后继续游戏
   */
  private ActionListener continueActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      TetrisMain.this.tetrisPanel.setPause(false);
    }
  };

  /**
   * 退出游戏
   */
  private ActionListener exitActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  };

  /**
   * 点击关于按钮
   */
  private ActionListener aboutActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(TetrisMain.this, ABOUT_MESSAGE_TEXT, ABOUT_TEXT, JOptionPane.PLAIN_MESSAGE);
    }
  };

  /**
   * 4 * 4 方块 版本
   */
  private ActionListener v4ActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      TetrisMain.this.tetrisPanel.setMode(BLOCK_V4_TEXT);
    }
  };

  /**
   * 6 * 6 方块版本
   */
  private ActionListener v6ActionListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      TetrisMain.this.tetrisPanel.setMode(BLOCK_V6_TEXT);
    }
  };

  /**
   * 改变游戏速度
   */

  private ActionListener gameSpeedActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      JMenuItem item = (JMenuItem) e.getSource();
      String itemText = item.getText();
      int timeDelay;
      if (SPEED_SLOW_TEXT.equals(itemText)) {
        timeDelay = TetrisMain.this.tetrisPanel.SPEED_SLOW_TIME_DELAY;
      } else if (SPEED_LITTLE_FAST_TEXT.equals(itemText)) {
        timeDelay = TetrisMain.this.tetrisPanel.SPEED_LITTLE_FAST_TIME_DELAY;
      } else if (SPEED_FAST_TEXT.equals(itemText)) {
        timeDelay = TetrisMain.this.tetrisPanel.SPEED_FAST_TIME_DELAY;
      } else {
        timeDelay = TetrisMain.this.tetrisPanel.SPEED_VERY_FAST_TIME_DELAY;
      }
      TetrisMain.this.tetrisPanel.changeGameSpeed(timeDelay);
    }
  };


  public TetrisMain() {

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
    this.setTitle(GAME_TITLE_TEXT);
    this.setResizable(false);

    JMenuBar menu = new JMenuBar();
    this.setJMenuBar(menu);

    JMenu gameMenu = new JMenu(GAME_TEXT);
    JMenuItem newGameItem = gameMenu.add(NEW_GAME_TEXT);
    newGameItem.addActionListener(this.newGameActionListener);
    JMenuItem pauseItem = gameMenu.add(GAME_PAUSE_TEXT);
    pauseItem.addActionListener(this.pauseActionListener);
    JMenuItem continueItem = gameMenu.add(GAME_CONTINUE_TEXT);
    continueItem.addActionListener(this.continueActionListener);
    JMenuItem exitItem = gameMenu.add(GAME_EXIT_TEXT);
    exitItem.addActionListener(this.exitActionListener);

    JMenu modeMenu = new JMenu(MODE_TEXT);
    JMenuItem v4Item = modeMenu.add(MODE_V4_TEXT);
    v4Item.addActionListener(this.v4ActionListener);
    JMenuItem v6Item = modeMenu.add(MODE_V6_TEXT);
    v6Item.addActionListener(this.v6ActionListener);

    JMenu speedMenu = new JMenu(SPEED_TEXT);
    JMenuItem speedSlowItem = speedMenu.add(SPEED_SLOW_TEXT);
    JMenuItem speedLittleFastItem = speedMenu.add(SPEED_LITTLE_FAST_TEXT);
    JMenuItem speedFastItem = speedMenu.add(SPEED_FAST_TEXT);
    JMenuItem speedVeryFastItem = speedMenu.add(SPEED_VERY_FAST_TEXT);
    speedSlowItem.addActionListener(this.gameSpeedActionListener);
    speedLittleFastItem.addActionListener(this.gameSpeedActionListener);
    speedFastItem.addActionListener(this.gameSpeedActionListener);
    speedVeryFastItem.addActionListener(this.gameSpeedActionListener);

    JMenu helpMenu = new JMenu(HELP_TEXT);
    JMenuItem aboutItem = helpMenu.add(ABOUT_TEXT);
    aboutItem.addActionListener(this.aboutActionListener);

    menu.add(gameMenu);
    menu.add(modeMenu);
    menu.add(speedMenu);
    menu.add(helpMenu);

    this.add(this.tetrisPanel);
    this.tetrisPanel.setFocusable(true);
  }

  static public void main(String[] args) {
    TetrisMain tetris = new TetrisMain();
    tetris.setVisible(true);
  }

}
