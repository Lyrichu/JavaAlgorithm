package pers.lyrichu.projects.gobang_game;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static pers.lyrichu.projects.gobang_game.Constants.*;

/**
 * 游戏 UI
 */
public class UI {

  private JFrame frame;//五子棋游戏窗口

  //五子棋盘
  private Chessboard chessboard = new Chessboard();
  //五子棋业务逻辑
  private Chess chess = new Chess();

  private JMenuBar menu; //菜单栏
  private JMenu option; //菜单栏中的"选项"菜单
  private Action replayOption; //"选项"下拉项中的"重玩一盘"选项
  private Action machineFirstOption; //"选项"下拉项中的"机器先手"选项
  private Action humanFirstOption; //"选项"下拉项中的"人类先手"选项
  private Action backStepOption; // "悔棋" 选项

  // 游戏初始化
  public void init() {

    frame = new JFrame(GAME_TITLE); // 游戏界面窗口
    menu = new JMenuBar(); //创建菜单栏
    option = new JMenu(GAME_OPTION_TITLE); //创建菜单栏中的"选项"菜单

    //把"选项"菜单加入到菜单栏
    menu.add(option);

    // 增加下拉选项
    replayOptionInit();
    option.add(replayOption);

    machineFirstOptionInit();
    option.add(machineFirstOption);

    humanFirstOptionInit();
    option.add(humanFirstOption);

    backStepOptionInit();
    option.add(backStepOption);

    frame.setJMenuBar(menu);//把menu设置为frame的菜单栏
    frame.add(chessboard);//把五子棋盘加入到frame

    //初始化棋盘
    chessboard.init();
    chess.init();

    // 绑定鼠标事件,为了避免写无用的抽象方法的实现，用适配器
    chessboard.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        //鼠标点击引发下棋事件
        play(e);
      }
    });

    frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }



  //"重玩一盘"选项绑定相应的处理事件
  private void replayOptionInit() {
    replayOption = new AbstractAction(REPLAY_TEXT) {
      @Override
      public void actionPerformed(ActionEvent e) {
        chessboard.init();//界面方面：初始化重来
        chess.init();//逻辑业务方面：初始化重来
      }
    };
  }

  //"机器先手"选项绑定相应的处理事件
  private void machineFirstOptionInit() {
    machineFirstOption = new AbstractAction(MACHINE_FIRST_TEXT) {
      @Override
      public void actionPerformed(ActionEvent e){
        //棋盘还没有落子的时候可以选择"机器先手"，一旦有落子，选择"机器先手"失效
        if(chessboard.isEmpty()){
          Chess.chessFirst = ChessFirst.MACHINE;
          //机器先手，则先在中间位置下一个棋子
          chessboard.addChessman(7, 7, ChessCellStatus.MACHINE);
          chess.addChessman(7, 7, ChessCellStatus.MACHINE);
        }
      }
    };
  }

  //"人类先手"选项绑定相应的处理事件
  private void humanFirstOptionInit() {
    humanFirstOption = new AbstractAction(HUMAN_FIRST_TEXT) {
      @Override
      public void actionPerformed(ActionEvent e){
        //棋盘还没有落子的时候可以选择"人类先手"，一旦有落子，选择"人类先手"失效
        if(chessboard.isEmpty()){
          Chess.chessFirst = ChessFirst.HUMAN;
        }
      }
    };
  }

  // "悔棋" 选项 事件处理
  private void backStepOptionInit() {
    backStepOption = new AbstractAction(BACK_STEP_TEXT) {
      @Override
      public void actionPerformed(ActionEvent e) {
        chessboard.backStepChessman();
      }
    };
  }

  // 处理鼠标落子事件
  private void play(MouseEvent e) {
    int cellSize = chessboard.getCellSize();//每个格子的边长
    int x = (e.getX() - 5) / cellSize;//像素值转换成棋盘坐标
    int y = (e.getY() - 5) / cellSize;//像素值转换成棋盘坐标
    //判断落子是否合法
    boolean isLegal = chess.isLegal(x, y);
    //如果落子合法
    if(isLegal) {
      chessboard.addChessman(x, y, ChessCellStatus.HUMAN);//界面方面加一个棋子
      chess.addChessman(x, y, ChessCellStatus.HUMAN);//逻辑业务方面加一个棋子

      //判断人类是否胜利
      if(chess.isWin(x, y, ChessCellStatus.HUMAN)) {
        JOptionPane.showMessageDialog(frame, HUMAN_WIN_MESSAGE, HUMAN_WIN_TITLE, JOptionPane.PLAIN_MESSAGE);
        chessboard.init();
        chess.init();
        return;
      }

      //机器落子
      Location loc = chess.searchLocation();
      chessboard.addChessman(loc);
      chess.addChessman(loc.getX(), loc.getY(), loc.getCellStatus());

      //判断机器是否胜利
      if(chess.isWin(loc.getX(), loc.getY(), ChessCellStatus.MACHINE)) {
        JOptionPane.showMessageDialog(frame, MACHINE_WIN_MESSAGE, MACHINE_WIN_TITLE, JOptionPane.PLAIN_MESSAGE);
        chessboard.init();
        chess.init();
      }
    }
  }

}

