package pers.lyrichu.projects.saolei_game;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

/**
 * 一个简单的使用 java swing 开发的扫雷小游戏,参考:https://www.bilibili.com/video/av71174896
 */
public class SaoLeiMain implements ActionListener {

  private ClassLoader classloader = getClass().getClassLoader();

  private final String RESOURCE_PATH = "saolei_game/";
  private final ImageIcon BANNER_ICON = new ImageIcon(classloader.getResource(RESOURCE_PATH + "banner.png"));
  private final ImageIcon BOMB_ICON = new ImageIcon(classloader.getResource(RESOURCE_PATH + "bomb.png"));
  private final ImageIcon FAIL_ICON = new ImageIcon(classloader.getResource(RESOURCE_PATH + "fail.png"));
  private final ImageIcon GUESS_ICON = new ImageIcon(classloader.getResource(RESOURCE_PATH + "guess.png"));
  private final ImageIcon WIN_ICON = new ImageIcon(classloader.getResource(RESOURCE_PATH + "win.png"));
  private final ImageIcon WIN_FLAG_ICON = new ImageIcon(classloader.getResource(RESOURCE_PATH + "win_flag.png"));

  private final int SCREEN_WIDTH = 600;
  private final int SCREEN_HEIGHT = 700;
  private final String WIN_MESSAGE = "你赢啦!\n点击上面的banner重新开始游戏.";
  private final String WIN_TITLE = "✌️提醒";
  private final String FAILED_MESSAGE = "你暴雷啦!\n点击上面的banner重新开始游戏.";
  private final String FAILED_TITLE = "💣提醒";

  private JFrame frame = new JFrame();
  private JPanel panel = new JPanel(new GridBagLayout()); // 存放状态 label
  private Container buttonContainer = new Container(); // 存放每个格子button
  private JButton bannerButton = new JButton(BANNER_ICON);

  private final int ROW = 20;
  private final int COL = 20;
  private final int LEI_CODE = -1; // 表示雷的常量

  private int[][] data = new int[ROW][COL]; // 存放每一个格子的信息
  private JButton[][] buttonData = new JButton[ROW][COL]; // 存放扫雷每个格子button数据

  private final Color CELL_BG_COLOR = new Color(244,183,113); // 格子的默认背景色(土黄色)
  private final Color BLANK_CELL_COLOR = Color.GREEN; // 默认打开空格的颜色
  private final Random random = new Random();
  private Timer timer = new Timer(1000,this);

  // 以下的数据每次游戏开始时需要单独初始化
  private int leiTotalCount; // 雷的总数,每次游戏开始的时候都随机初始化
  private int alreadyOpened; // 已经打开的格数
  private int notOpened; // 没有打开的格数
  private int costTime;

  private JLabel notOpenedLabel;
  private JLabel alreadyOpenedLabel;
  private JLabel costTimeLabel;


  /**
   * 开始游戏
   */
  public void startGame() {
    startInitConstants();
    timer.start();
    frame = new JFrame();
    frame.setLayout(new BorderLayout()); // 东南西北中五个位置
    setHeader();
    setLei();
    setButtons();
    frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * 重新开始游戏,需要重置一些数据,包括:
   * 1.数据清空
   * 2.按钮恢复
   * 3.重启时钟
   */
  public void restartGame() {
    restartInitConstants();
    bannerButton.setIcon(BANNER_ICON);
    for (int i = 0;i < ROW;i++) {
      for (int j = 0;j < COL;j++) {
        data[i][j] = 0; // data 重新初始化
        JButton button = buttonData[i][j];
        button.setEnabled(true);
        button.setText("");
        button.setIcon(GUESS_ICON);
        button.setBackground(CELL_BG_COLOR);
      }
    }
    setLei();
    timer.start();
  }

  /**
   * 游戏开始的时候初始化一些常量
   */
  private void startInitConstants() {
    // 雷的数目在 3 - 10 之间
    leiTotalCount = 3 + random.nextInt(8);
    alreadyOpened = 0;
    notOpened = ROW * COL - alreadyOpened;
    costTime = 0;
    notOpenedLabel = new JLabel("未开:" + notOpened);
    alreadyOpenedLabel = new JLabel("已开:" + alreadyOpened);
    costTimeLabel = new JLabel("耗时:" + costTime + "s");
  }

  /**
   * 游戏重新开始时初始化一些常量
   */

  private void restartInitConstants() {
    // 雷的数目在 3 - 10 之间
    leiTotalCount = 3 + random.nextInt(8);
    alreadyOpened = 0;
    notOpened = ROW * COL - alreadyOpened;
    costTime = 0;
    notOpenedLabel.setText("未开:" + notOpened);
    alreadyOpenedLabel.setText("已开:" + alreadyOpened);
    costTimeLabel.setText("耗时:" + costTime + "s");
  }

  /**
   * 设置游戏 header
   */
  private void setHeader() {
    // 设置 button 和 label 的属性

    bannerButton.setOpaque(true); // 设置为不透明
    bannerButton.setBackground(Color.WHITE); // 背景设置为白色
    bannerButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 边框设置为灰色
    bannerButton.addActionListener(this);

    notOpenedLabel.setOpaque(true);
    notOpenedLabel.setBackground(Color.WHITE);
    notOpenedLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    alreadyOpenedLabel.setOpaque(true);
    alreadyOpenedLabel.setBackground(Color.WHITE);
    alreadyOpenedLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    costTimeLabel.setOpaque(true);
    costTimeLabel.setBackground(Color.WHITE);
    costTimeLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    // button 布局,1行3列(有3个label)
    GridBagConstraints bannerButtonGBC = new GridBagConstraints(0,0,3,1,1.0,1.0,
        GridBagConstraints.CENTER,GridBagConstraints.BOTH,
        new Insets(0,0,0,0),0,0);
    panel.add(bannerButton,bannerButtonGBC);

    // 已开
    GridBagConstraints alreadyOpenedLabelGBC = new GridBagConstraints(0,1,1,1,1.0,1.0,
        GridBagConstraints.CENTER,GridBagConstraints.BOTH,
        new Insets(0,0,0,0),0,0);
    panel.add(alreadyOpenedLabel,alreadyOpenedLabelGBC);

    // 未开
    GridBagConstraints notOpenedLabelGBC = new GridBagConstraints(1,1,1,1,1.0,1.0,
        GridBagConstraints.CENTER,GridBagConstraints.BOTH,
        new Insets(0,0,0,0),0,0);
    panel.add(notOpenedLabel,notOpenedLabelGBC);

    // 耗时
    GridBagConstraints costTimeLabelGBC = new GridBagConstraints(2,1,1,1,1.0,1.0,
        GridBagConstraints.CENTER,GridBagConstraints.BOTH,
        new Insets(0,0,0,0),0,0);
    panel.add(costTimeLabel,costTimeLabelGBC);

    // add panel
    frame.add(panel,BorderLayout.NORTH);
  }

  /**
   * 添加 扫雷格子 button
   */
  private void setButtons() {
    // 遍历添加每个格子的button
    buttonContainer.setLayout(new GridLayout(ROW,COL));
    for (int i = 0;i < ROW;i++) {
      for (int j = 0;j < COL;j++) {
        JButton button = new JButton(GUESS_ICON);
        button.setOpaque(true);
        button.setBackground(CELL_BG_COLOR);
        button.setText("");
        button.addActionListener(this);
        buttonContainer.add(button);
        buttonData[i][j] = button;
      }
    }
    frame.add(buttonContainer,BorderLayout.CENTER);
  }

  /**
   * 添加雷
   */
  private void setLei() {
    int leiCount = 0;
    while (leiCount < leiTotalCount) {
      int row = random.nextInt(ROW);
      int col = random.nextInt(COL);
      if (data[row][col] != LEI_CODE) {
        data[row][col] = LEI_CODE;
        leiCount++;
      }
    }

    // 计算非雷的格子四周雷的数量
    for (int i = 0;i < ROW;i++) {
      for (int j = 0;j < COL;j++) {
        // 如果本身是雷,则先跳过
        if (data[i][j] == LEI_CODE) {
          continue;
        }
        int neighborLeiCount = 0; // 周围累积雷的个数
        // 获取周围的坐标
        List<Coordinate> neighborCoordinates = getNeighborCoordinates(i,j);
        // 选择合法的坐标
        for (Coordinate coordinate : neighborCoordinates) {
          if (isValidCoordinate(coordinate)) {
            if (data[coordinate.getX()][coordinate.getY()] == LEI_CODE) {
              neighborLeiCount++;
            }
          }
        }
        data[i][j] = neighborLeiCount;
      }
    }
  }

  /**
   * 处理各种格子(空格/非空格/雷)的打开情况
   */
  private void openCell(int x,int y) {
    int code = data[x][y];
    if (code == 0) {
      // 如果是空格
      openCellForBlank(x,y);
      updateLabelsStatus();
    } else if (code > 0) {
      // 如果是非空格
      changeClickButton(x,y);
      updateLabelsStatus();
    } else {
      // 如果是雷
      // 改变 banner,游戏失败
      bannerButton.setIcon(FAIL_ICON);
      openCellForLei(x,y);
    }
    checkWin();
  }

  /**
   * 打开坐标为 (x,y) 空格子,并且实现空格级联打开
   * @param x x 坐标
   * @param y y 坐标
   */
  private void openCellForBlank(int x,int y) {
    JButton button = buttonData[x][y];
    if (!button.isEnabled()) {
      return;
    }
    if (data[x][y] == 0) {
      changeClickButton(x,y);
      // 级联打开周围的空格
      List<Coordinate> neighborCoordinates = getNeighborCoordinates(x,y);
      for (Coordinate coordinate : neighborCoordinates) {
        // 递归打开
        if (isValidCoordinate(coordinate)) {
          openCellForBlank(coordinate.getX(),coordinate.getY());
        }
      }
    }
  }

  /**
   * 当 (x,y)处的button被点击时，改变该button的状态
   */
  private void changeClickButton(int x,int y,Color color) {
    JButton button = buttonData[x][y];
    button.setIcon(null);
    button.setOpaque(true);
    if (color != null) {
      button.setBackground(color);
    }
    button.setText(String.valueOf(data[x][y]));
    button.setEnabled(false);
  }

  /**
   * 更改游戏 label 状态信息
   */
  private void updateLabelsStatus() {
    notOpened = calNotOpenedCellNum(); // 未打开格子数目
    notOpenedLabel.setText("未开:" + notOpened);
    alreadyOpened = ROW * COL - notOpened; // 已打开格子数目
    alreadyOpenedLabel.setText("已开:" + alreadyOpened);
  }

  /**
   * color 默认值是 BLANK_CELL_COLOR
   */
  private void changeClickButton(int x,int y) {
    changeClickButton(x,y,BLANK_CELL_COLOR);
  }

  /**
   * 打开了含有雷的格子
   */
  private void openCellForLei(int x,int y) {
    // 停止时钟
    timer.stop();
    for (int i = 0;i < ROW;i++) {
      for (int j = 0;j < COL;j++) {
        JButton button = buttonData[i][j];
        int code = data[i][j];
        if (!button.isEnabled()) {
          continue;
        }
        if (code == LEI_CODE) {
          changeButtonIcon(button,BOMB_ICON);
        } else {
          changeClickButton(i,j,null);
        }
      }
    }
    showDialog(FAILED_MESSAGE,FAILED_TITLE);
  }

  /**
   * 检查 是否已经 胜利,胜利的条件是:
   * 当前剩余的格子数目 = 雷的总数
   */
  private void checkWin() {
    // 计算剩余格子的数目
    int notOpenedCellNum = calNotOpenedCellNum();
    // 如果已经胜利
    if (notOpenedCellNum == leiTotalCount) {
      timer.stop();
      // 胜利之后，剩下的格子插上小旗子
      showFlagForNotOpenedCell();
      showDialog(WIN_MESSAGE,WIN_TITLE);
    }
  }

  /**
   * 获取没有打开的格子列表
   * @return List[JButton]
   */
  private List<JButton> getNotOpenedCells() {
    List<JButton> notOpenedCells = new ArrayList<>();
    for (int i = 0;i < ROW;i++) {
      for (int j = 0;j < COL;j++) {
        JButton button = buttonData[i][j];
        if (button.isEnabled()) {
          notOpenedCells.add(button);
        }
      }
    }
    return notOpenedCells;
  }

  /**
   * 计算没有打开的格子的数目
   */
  private int calNotOpenedCellNum() {
    return getNotOpenedCells().size();
  }

  /**
   * 胜利之后，给未打开的格子，插上胜利的旗帜
   */
  private void showFlagForNotOpenedCell() {
    List<JButton> notOpenedCells = getNotOpenedCells();
    for (JButton button : notOpenedCells) {
      changeButtonIcon(button,WIN_FLAG_ICON);
    }
  }

  /**
   * 改变 button 的 icon
   */
  private void changeButtonIcon(JButton button, Icon icon) {
    button.setEnabled(false);
    button.setIcon(icon);
    button.setDisabledIcon(icon);
  }

  /**
   * 展示 dialog 信息
   * @param message 要展示的信息
   * @param title 标题
   */
  private void showDialog(String message,String title) {
    JOptionPane.showMessageDialog(frame,message,title,JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * 获取某个格子周围格子的坐标
   * @param x 输入格子 x 坐标
   * @param y 输入格子 y 坐标
   * @return 周围格子的坐标 List
   */
  private List<Coordinate> getNeighborCoordinates(int x,int y) {
    List<Coordinate> coordinates = Lists.newArrayListWithCapacity(8);
    for (int i = x - 1;i <= x + 1;i++) {
      for (int j = y - 1;j <= y + 1;j++) {
        if (i == x && j == y) {
          continue;
        }
        coordinates.add(new Coordinate(i,j));
      }
    }
    return coordinates;
  }

  /**
   * 判断是否是合法的坐标
   * @param coordinate 输入坐标
   * @return
   */
  private boolean isValidCoordinate(Coordinate coordinate) {
    return coordinate.getX() >= 0 && coordinate.getX() < ROW
        && coordinate.getY() >= 0 && coordinate.getY() < COL;
  }


  public static void main(String[] args) {
    SaoLeiMain saoLei = new SaoLeiMain();
    saoLei.startGame();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // 如果事件由时钟触发
    if (e.getSource() instanceof Timer) {
      costTime++;
      costTimeLabel.setText("耗时:" + costTime + "s");
      return;
    }
    // 获取点击的button
    JButton button = (JButton) e.getSource();
    // 如果是 banner button,则 restart game
    if (button.equals(bannerButton)) {
      restartGame();
      return;
    }
    // 单元格 button
    for (int i = 0;i < ROW;i++) {
      for (int j = 0;j < COL;j++) {
        if (buttonData[i][j].equals(button)) {
          openCell(i,j);
        }
      }
    }
  }

  /**
   * 保存坐标的类
   */
  class Coordinate {
    private int x;
    private int y;
    public Coordinate(int x,int y) {
      this.x = x;
      this.y = y;
    }

    public void setX(int x) {
      this.x = x;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }

}
