package pers.lyrichu.projects.tetris_game;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class TetrisPanel extends JPanel {
  
  private static final long serialVersionUID = -807909536278284335L;
  private static final int BLOCK_SIZE = 20;
  private static final int BLOCK_WIDTH = 16;
  private static final int BLOCK_HEIGHT = 26;

  public static final int SPEED_SLOW_TIME_DELAY = 1000; // 慢速,延时 1000ms
  public static final int SPEED_LITTLE_FAST_TIME_DELAY = 600; // 较快速,延时 600ms
  public static final int SPEED_FAST_TIME_DELAY = 300; // 快速,延时 300ms
  public static final int SPEED_VERY_FAST_TIME_DELAY = 100; // 极速,延时 100ms

  private static final String[] AUTHOR_INFO = {
      "制作人：","Lyrichu"
  };

  // 存放已经固定的方块
  private boolean[][] blockMap = new boolean[BLOCK_HEIGHT][BLOCK_WIDTH];

  // 分数
  private int score = 0;

  //是否暂停
  private boolean isPause = false;

  // 7种形状
  private static boolean[][][] SHAPE = BlockV4.SHAPE;

  // 下落方块的位置,左上角坐标
  private Point nowBlockPos;

  // 当前方块矩阵
  private boolean[][] nowBlockMap;
  // 下一个方块矩阵
  private boolean[][] nextBlockMap;

  /**
   * 范围[0,28) 7种，每种有4种旋转状态，共4*7=28 %4获取旋转状态 /4获取形状
   */
  private int nextBlockState;
  private int nowBlockState;

  //计时器
  private Timer timer;
  // 随机数发生器
  private Random random = new Random();

  private final Color WALL_COLOR = Color.black; // 墙的颜色
  private final Color GAME_SCORE_COLOR = Color.red; // 游戏得分颜色
  private final Color GAME_PAUSE_COLOR = Color.white; // 游戏暂停颜色


  // 定时器监听
  private ActionListener timerListener = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if (TetrisPanel.this.isTouch(TetrisPanel.this.nowBlockMap, new Point(TetrisPanel.this.nowBlockPos.x, TetrisPanel.this.nowBlockPos.y + 1))){
        if (TetrisPanel.this.fixBlock()){
          TetrisPanel.this.score += TetrisPanel.this.clearLines() * 10;
          TetrisPanel.this.getNextBlock();
        }
        else{
          JOptionPane.showMessageDialog(TetrisPanel.this.getParent(), "GAME OVER");
          TetrisPanel.this.initPanel();
        }
      }
      else{
        TetrisPanel.this.nowBlockPos.y ++;
      }
      TetrisPanel.this.repaint();
    }
  };

  //按键监听
  private KeyListener keyListener = new KeyListener(){

    @Override
    public void keyPressed(KeyEvent e) {
      if (!isPause){
        Point DesPoint;
        switch (e.getKeyCode()) {
          case KeyEvent.VK_DOWN:
            DesPoint = new Point(TetrisPanel.this.nowBlockPos.x, TetrisPanel.this.nowBlockPos.y + 1);
            if (!TetrisPanel.this.isTouch(TetrisPanel.this.nowBlockMap, DesPoint)){
              TetrisPanel.this.nowBlockPos = DesPoint;
            }
            break;
          case KeyEvent.VK_UP:
            boolean[][] TurnBlock = TetrisPanel.this.rotateBlock(TetrisPanel.this.nowBlockMap,1);
            if (!TetrisPanel.this.isTouch(TurnBlock, TetrisPanel.this.nowBlockPos)){
              TetrisPanel.this.nowBlockMap = TurnBlock;
            }
            break;
          case KeyEvent.VK_RIGHT:
            DesPoint = new Point(TetrisPanel.this.nowBlockPos.x + 1, TetrisPanel.this.nowBlockPos.y);
            if (!TetrisPanel.this.isTouch(TetrisPanel.this.nowBlockMap, DesPoint)){
              TetrisPanel.this.nowBlockPos = DesPoint;
            }
            break;
          case KeyEvent.VK_LEFT:
            DesPoint = new Point(TetrisPanel.this.nowBlockPos.x - 1, TetrisPanel.this.nowBlockPos.y);
            if (!TetrisPanel.this.isTouch(TetrisPanel.this.nowBlockMap, DesPoint)){
              TetrisPanel.this.nowBlockPos = DesPoint;
            }
            break;
        }
        repaint();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
  };

  public TetrisPanel() {
    this.initPanel();
    timer = new Timer(TetrisPanel.SPEED_SLOW_TIME_DELAY, this.timerListener);
    timer.start();
    this.addKeyListener(this.keyListener);
  }

  public void setMode(String mode){
    if (mode.equals("v6")) {
      TetrisPanel.SHAPE = BlockV6.SHAPE;
    } else {
      TetrisPanel.SHAPE = BlockV4.SHAPE;
    }
    this.initPanel();
    this.repaint();
  }

  /**
   * 新的方块落下时的初始化
   */
  private void getNextBlock() {
    // 将已经生成好的下一次方块赋给当前方块
    this.nowBlockState = this.nextBlockState;
    this.nowBlockMap = this.nextBlockMap;
    // 再次生成下一次方块
    this.nextBlockState = this.createNewBlockState();
    this.nextBlockMap = this.getBlockMap(nextBlockState);
    // 计算方块位置
    this.nowBlockPos = this.calNewBlockInitPos();
  }

  /**
   * 判断正在下落的方块和墙、已经固定的方块是否有接触
   */
  private boolean isTouch(boolean[][] SrcNextBlockMap,Point SrcNextBlockPos) {
    for (int i = 0; i < SrcNextBlockMap.length;i ++){
      for (int j = 0;j < SrcNextBlockMap[i].length;j ++){
        if (SrcNextBlockMap[i][j]){
          if (SrcNextBlockPos.y + i >= TetrisPanel.BLOCK_HEIGHT ||
              SrcNextBlockPos.x + j < 0 || SrcNextBlockPos.x + j >= TetrisPanel.BLOCK_WIDTH) {
            return true;
          } else{
            if (SrcNextBlockPos.y + i < 0){
              continue;
            } else{
              if (this.blockMap[SrcNextBlockPos.y + i][SrcNextBlockPos.x + j]){
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * 固定方块到地图
   */
  private boolean fixBlock(){
    for (int i = 0;i < this.nowBlockMap.length;i ++){
      for (int j = 0;j < this.nowBlockMap[i].length;j ++){
        if (this.nowBlockMap[i][j])
          if (this.nowBlockPos.y + i < 0)
            return false;
          else
            this.blockMap[this.nowBlockPos.y + i][this.nowBlockPos.x + j] = this.nowBlockMap[i][j];
      }
    }
    return true;
  }

  /**
   * 计算新创建的方块的初始位置
   * @return 返回坐标
   */
  private Point calNewBlockInitPos(){
    return new Point(TetrisPanel.BLOCK_WIDTH / 2 - this.nowBlockMap[0].length / 2, - this.nowBlockMap.length);
  }

  /**
   * 初始化
   */
  public void initPanel() {
    //清空Map
    for (int i = 0;i < this.blockMap.length;i ++){
      for (int j = 0;j < this.blockMap[i].length;j ++){
        this.blockMap[i][j] = false;
      }
    }
    //清空分数
    this.score = 0;
    // 初始化第一次生成的方块和下一次生成的方块
    this.nowBlockState = this.createNewBlockState();
    this.nowBlockMap = this.getBlockMap(this.nowBlockState);
    this.nextBlockState = this.createNewBlockState();
    this.nextBlockMap = this.getBlockMap(this.nextBlockState);
    // 计算方块位置
    this.nowBlockPos = this.calNewBlockInitPos();
    this.repaint();
  }

  public void setPause(boolean value){
    this.isPause = value;
    if (this.isPause){
      this.timer.stop();
    }
    else{
      this.timer.restart();
    }
    this.repaint();
  }

  /**
   * 随机生成新方块状态
   */
  private int createNewBlockState() {
    int Sum = TetrisPanel.SHAPE.length * 4;
    return (int) (Math.random() * 1000) % Sum;
  }

  private boolean[][] getBlockMap(int BlockState) {
    int SHAPE = BlockState / 4;
    int Arc = BlockState % 4;
    return this.rotateBlock(TetrisPanel.SHAPE[SHAPE], Arc);
  }

  /**
   *
   * @param shape 7种图形之一
   * @param time 旋转次数
   * @return
   *
   * https://blog.csdn.net/janchin/article/details/6310654  翻转矩阵
   */

  private boolean[][] rotateBlock(boolean[][] shape, int time) {
    if(time == 0) {
      return shape;
    }
    int heigth = shape.length;
    int width = shape[0].length;
    boolean[][] ResultMap = new boolean[heigth][width];
    int tmpH = heigth - 1, tmpW = 0;
    for(int i = 0; i < heigth && tmpW < width; i++) {
      for(int j = 0; j < width && tmpH > -1; j++) {
        ResultMap[i][j] = shape[tmpH][tmpW];
        tmpH--;
      }
      tmpH = heigth - 1;
      tmpW++;
    }
    for(int i = 1; i < time; i++) {
      ResultMap = rotateBlock(ResultMap, 0);
    }
    return ResultMap;
  }

  /**
   * 绘制游戏界面
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // 画墙
    g.setColor(WALL_COLOR);
    for (int i = 0; i < TetrisPanel.BLOCK_HEIGHT + 1; i++) {
      g.drawRect(0, i * TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE);
      g.drawRect((TetrisPanel.BLOCK_WIDTH + 1) * TetrisPanel.BLOCK_SIZE, i * TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE,
          TetrisPanel.BLOCK_SIZE);
    }
    for (int i = 0; i < TetrisPanel.BLOCK_WIDTH; i++) {
      g.drawRect((1 + i) * TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_HEIGHT * TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE,
          TetrisPanel.BLOCK_SIZE);
    }
    // 画当前方块
    g.setColor(generateRandomColor());
    for (int i = 0; i < this.nowBlockMap.length; i++) {
      for (int j = 0; j < this.nowBlockMap[i].length; j++) {
        if (this.nowBlockMap[i][j]) {
          g.fillRect((1 + this.nowBlockPos.x + j) * TetrisPanel.BLOCK_SIZE, (this.nowBlockPos.y + i) * TetrisPanel.BLOCK_SIZE,
              TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE);
        }
      }
    }
    // 画已经固定的方块
    g.setColor(generateRandomColor());
    for (int i = 0; i < TetrisPanel.BLOCK_HEIGHT; i++) {
      for (int j = 0; j < TetrisPanel.BLOCK_WIDTH; j++) {
        if (this.blockMap[i][j]) {
          g.fillRect(TetrisPanel.BLOCK_SIZE + j * TetrisPanel.BLOCK_SIZE, i * TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE,
              TetrisPanel.BLOCK_SIZE);
        }
      }
    }
    //绘制下一个方块
    g.setColor(generateRandomColor());
    for (int i = 0;i < this.nextBlockMap.length;i ++){
      for (int j = 0;j < this.nextBlockMap[i].length;j ++){
        if (this.nextBlockMap[i][j]) {
          g.fillRect(380 + j * TetrisPanel.BLOCK_SIZE, 60 + i * TetrisPanel.BLOCK_SIZE,
              TetrisPanel.BLOCK_SIZE, TetrisPanel.BLOCK_SIZE);
        }
      }
    }
    // 绘制其他信息
    g.setColor(GAME_SCORE_COLOR);
    g.drawString("游戏分数:" + this.score, 380, 20);
    for (int i = 0;i < TetrisPanel.AUTHOR_INFO.length;i ++){
      g.drawString(TetrisPanel.AUTHOR_INFO[i], 380, 200 + i * 40);
    }

    //绘制暂停
    if (this.isPause){
      g.setColor(GAME_PAUSE_COLOR);
      g.fillRect(140, 200, 100, 40);
      g.setColor(Color.black);
      g.drawRect(140, 200, 100, 40);
      g.drawString("暂停", 180, 226);
    }
  }

  /**
   * 消除一行
   */
  private int clearLines(){
    int lines = 0;
    for (int i = 0;i < this.blockMap.length;i ++){
      boolean isLine = true;
      for (int j = 0;j < this.blockMap[i].length;j ++){
        if (!this.blockMap[i][j]){
          isLine = false;
          break;
        }
      }
      if (isLine){
        for (int k = i;k > 0;k --) {
          this.blockMap[k] = this.blockMap[k - 1];
        }
        this.blockMap[0] = new boolean[TetrisPanel.BLOCK_WIDTH];
        lines ++;
      }
    }
    return lines;
  }

  private Color generateRandomColor() {
    int r = random.nextInt(256);
    int g = random.nextInt(256);
    int b = random.nextInt(256);
    return new Color(r,g,b);
  }

  public void changeGameSpeed(int timeDelay) {
    timer.setDelay(timeDelay);
  }

}
