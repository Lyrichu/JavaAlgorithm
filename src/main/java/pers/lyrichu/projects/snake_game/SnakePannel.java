package pers.lyrichu.projects.snake_game;
import java.io.BufferedInputStream;
import java.util.Random;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;

/**
 * 画布
 */
public class SnakePannel extends JPanel implements KeyListener, ActionListener {

  private ClassLoader classLoader = getClass().getClassLoader();

  private final int ONE_GRID_SIZE = 25; // 一格长度
  private final int RECT_INIT_X = 25; // 矩形左上角起始x坐标
  private final int RECT_INIT_Y = 75; // 矩形左上角起始y坐标
  private final int RECT_WIDTH = 850; // 矩形宽度
  private final int RECT_HEIGHT = 600; // 矩形高度
  private final int RECT_LEFT = RECT_INIT_X; // 矩形最左边
  private final int RECT_RIGHT = RECT_INIT_X + RECT_WIDTH - ONE_GRID_SIZE; // 矩形最右边
  private final int RECT_UP = RECT_INIT_Y; // 矩形最上面
  private final int RECT_DOWN = RECT_INIT_Y + RECT_HEIGHT - ONE_GRID_SIZE; // 矩形最下面
  private final int FLUSH_TIMER_MILL = 150; // 定时器刷新频率
  private final int FONT_SIZE = 40; // 字体大小
  private final int SCORE_LEN_ONE = 10; // 长度加1增加的分数值

  private int snake_len;
  private int score;
  private int[] snakex = new int[100];
  private int[] snakey = new int[100];
  private int foodx;
  private int foody;


  // 资源文件
  private ImageIcon title; // 标题
  private ImageIcon body; // 蛇身体
  private ImageIcon up; // 蛇头 向上
  private ImageIcon down; // 蛇头 向下
  private ImageIcon left; // 蛇头 向左
  private ImageIcon right; // 蛇头 向右
  private ImageIcon food; // 食物
  private Clip bgm; // 背景音乐


  // 方向控制
  enum DIRECTION {
    LEFT, // 左
    RIGHT, // 右
    UP, // 上
    DOWN // 下
  }

  // 蛇头方向初始化
  private DIRECTION headDirection = DIRECTION.RIGHT;
  // 移动方向初始化
  private DIRECTION moveDirection = DIRECTION.RIGHT;

  // 是否开始游戏
  private boolean isStarted = false;
  // 游戏是否失败
  private boolean isFailed = false;

  // 控制蛇移动的定时器
  private Timer timer = new Timer(FLUSH_TIMER_MILL,this);
  // 随机数生成器
  private Random random = new Random();




  public SnakePannel() {
    initSnake();
    this.setFocusable(true);
    this.addKeyListener(this);
    //启动定时器
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setBackground(Color.WHITE);
    title.paintIcon(this,g,25,11);
    g.fillRect(RECT_INIT_X,RECT_INIT_Y,RECT_WIDTH,RECT_HEIGHT);

    // 在画布上绘制蛇头
    switch (headDirection) {
      case UP:
        up.paintIcon(this,g,snakex[0],snakey[0]);
        break;
      case DOWN:
        down.paintIcon(this,g,snakex[0],snakey[0]);
        break;
      case LEFT:
        left.paintIcon(this,g,snakex[0],snakey[0]);
        break;
      case RIGHT:
        right.paintIcon(this,g,snakex[0],snakey[0]);
        break;

      default:
        right.paintIcon(this,g,snakex[0],snakey[0]);
    }
    // 绘制蛇身
    for (int i = 1;i < snake_len;i++) {
      body.paintIcon(this,g,snakex[i],snakey[i]);
    }
    // 绘制食物
    food.paintIcon(this,g,foodx,foody);
    // 绘制分数
    g.setColor(Color.WHITE);
    g.drawString("len:" + snake_len,750,35);
    g.drawString("score:" + score,750,50);
    // start game hint
    if (!isStarted && !isFailed) {
      drawText(g,"Press space to start!");
      stopBGM();
    }
    if (isFailed) {
      drawText(g,"Press space to restart!",300,300,Color.RED,"arial",Font.BOLD,FONT_SIZE);
      stopBGM();
    }
    if (isStarted && !isFailed) {
      playBGM();
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_SPACE) {
      if (isFailed) {
        isFailed = false;
        initSnake();
      } else {
        isStarted = !isStarted;
      }
      repaint();
    } else if (keyCode == KeyEvent.VK_UP) {
      moveDirection = DIRECTION.UP;
    } else if (keyCode == KeyEvent.VK_DOWN) {
      moveDirection = DIRECTION.DOWN;
    } else if (keyCode == KeyEvent.VK_LEFT) {
      moveDirection = DIRECTION.LEFT;
    } else if (keyCode == KeyEvent.VK_RIGHT) {
      moveDirection = DIRECTION.RIGHT;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (isStarted && !isFailed) {
      for (int i = snake_len - 1;i > 0;i--) {
        snakex[i] = snakex[i-1];
        snakey[i] = snakey[i-1];
      }
      // 控制移动的方向，并且处理边界情况
      switch (moveDirection) {
        case RIGHT:
          snakex[0] += ONE_GRID_SIZE;
          // 处理边界
          if (snakex[0] > RECT_RIGHT) {
            snakex[0] = RECT_LEFT;
          }
          headDirection = DIRECTION.RIGHT;
          break;
        case LEFT:
          snakex[0] -= ONE_GRID_SIZE;
          if (snakex[0] < RECT_LEFT) {
            snakex[0] = RECT_RIGHT;
          }
          headDirection = DIRECTION.LEFT;
          break;
        case UP:
          snakey[0] -= ONE_GRID_SIZE;
          if (snakey[0] < RECT_UP) {
            snakey[0] = RECT_DOWN;
          }
          headDirection = DIRECTION.UP;
          break;
        case DOWN:
          snakey[0] += ONE_GRID_SIZE;
          if (snakey[0] > RECT_DOWN) {
            snakey[0] = RECT_UP;
          }
          headDirection = DIRECTION.DOWN;
          break;
      }
      // 如果蛇头吃到了食物,则蛇的长度加1
      if (snakex[0] == foodx && snakey[0] == foody) {
        snake_len++;
        score += SCORE_LEN_ONE;
        // 重新初始化食物位置
        initFood();
      }
      // 如果游戏失败
      if (isGameFailed()) {
        isFailed = true;
      }
      repaint();
    }
    timer.start();
  }

  public void initSnake() {
    snake_len = 3;
    score = 0;
    // 蛇头
    snakex[0] = 4 * ONE_GRID_SIZE;
    snakex[1] = snakex[0] - ONE_GRID_SIZE;
    snakex[2] = snakex[1] - ONE_GRID_SIZE;

    snakey[0] = 4 * ONE_GRID_SIZE;
    snakey[1] = snakey[0];
    snakey[2] = snakey[0];
    headDirection = DIRECTION.RIGHT;
    moveDirection = DIRECTION.RIGHT;
    initImageResource();
    initFood();
    initBGM();
  }

  // 食物坐标随机初始化
  public void initFood() {
    // 注意这里不要让食物初始化与墙壁接触，不然无法吃到食物(撞墙就死)
    foodx = RECT_INIT_X + ONE_GRID_SIZE * (1 + random.nextInt(RECT_WIDTH / ONE_GRID_SIZE - 2));
    foody = RECT_INIT_Y + ONE_GRID_SIZE * (1 + random.nextInt(RECT_HEIGHT / ONE_GRID_SIZE - 2));
  }

  // 初始化图片资源
  public void initImageResource() {
    title = new ImageIcon(classLoader.getResource("snake_game/title.jpg"));
    body = new ImageIcon(classLoader.getResource("snake_game/body.png"));
    up = new ImageIcon(classLoader.getResource("snake_game/up.png"));
    down = new ImageIcon(classLoader.getResource("snake_game/down.png"));
    left = new ImageIcon(classLoader.getResource("snake_game/left.png"));
    right = new ImageIcon(classLoader.getResource("snake_game/right.png"));
    food = new ImageIcon(classLoader.getResource("snake_game/food.png"));
  }

  /**
   * 判定游戏是否失败,判定的规则是:
   * 1.蛇头与身体重合
   * 2.蛇头撞墙
   */
  public boolean isGameFailed() {
    return isHeadBodyOverLap() || isHeadHitWall();
  }

  // 判定蛇的头和身体是否重合
  public boolean isHeadBodyOverLap() {
    for (int i = 1;i < snake_len;i++) {
      if (snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
        return true;
      }
    }
    return false;
  }

  // 判断蛇头是否撞墙
  public boolean isHeadHitWall() {
    return snakex[0] <= RECT_LEFT || snakex[0] >= RECT_RIGHT ||
        snakey[0] <= RECT_UP || snakey[0] >= RECT_DOWN;
  }

  public void drawText(Graphics g,String text,int x,int y,
      Color color,String fontName,int fontStyle,int fontSize) {
    g.setColor(color);
    g.setFont(new Font(fontName,fontStyle,fontSize));
    g.drawString(text,x,y);
  }

  public void drawText(Graphics g,String text) {
    drawText(g,text,300,300,Color.WHITE,"arial",Font.BOLD,FONT_SIZE);
  }

  // 初始化bgm
  public void initBGM() {
    try {
      bgm = AudioSystem.getClip();
      BufferedInputStream bis = new BufferedInputStream(classLoader.getResourceAsStream("snake_game/bgm2.wav"));
      AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
      bgm.open(ais);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 播放 bgm
  public void playBGM() {
    bgm.loop(Clip.LOOP_CONTINUOUSLY);
  }

  // 停止 bgm
  public void stopBGM() {
    bgm.stop();
  }

}
