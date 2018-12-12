package pers.lyrichu.java.basic.jfree_chart;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartDemo {
  private ChartPanel frame;
  private JFreeChart chart;

  public static void main(String[] args) {
    BarChartDemo bar = new BarChartDemo();
    JFreeChart chart = bar.getChart();
    ChartFrame cf = new ChartFrame("BarChart",chart);
    cf.pack();
    cf.setVisible(true);
    String chartSavePath = "src/main/resources/bar_chart_demo.jpg";
    int width = 640;
    int height = 480;
    bar.saveCharAsJPG(chart,chartSavePath,width,height);
  }
  public BarChartDemo() {
    CategoryDataset dataset = getDataSet();
    chart = ChartFactory.createBarChart3D(
            "水果",
            "水果种类", //xlabel name
            "数量",// ylabel name
            dataset,
            PlotOrientation.VERTICAL, // 图表方向:水平/垂直
            true, // 是否生成图例
            false, // 是否生成工具
            false // 是否生成url链接
    );
    CategoryPlot plot = chart.getCategoryPlot(); // 获取图表区域对象
    CategoryAxis axis = plot.getDomainAxis(); //水平底部列表
    axis.setLabelFont(new Font("黑体",Font.BOLD,14)); // 水平底部标题
    axis.setTickLabelFont(new Font("宋体",Font.BOLD,12)); //垂直标题
    // 获取柱状
    ValueAxis rangeAxis = plot.getRangeAxis();
    rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
    // 图例设置字体
    chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,15));
    // 设置标题字体
    chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));
    frame = new ChartPanel(chart,true);
  }

  private static CategoryDataset getDataSet() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(100,"北京","苹果");
    dataset.addValue(100, "上海", "苹果");
    dataset.addValue(100, "广州", "苹果");
    dataset.addValue(200, "北京", "梨子");
    dataset.addValue(200, "上海", "梨子");
    dataset.addValue(200, "广州", "梨子");
    dataset.addValue(300, "北京", "葡萄");
    dataset.addValue(300, "上海", "葡萄");
    dataset.addValue(300, "广州", "葡萄");
    dataset.addValue(400, "北京", "香蕉");
    dataset.addValue(400, "上海", "香蕉");
    dataset.addValue(400, "广州", "香蕉");
    dataset.addValue(500, "北京", "荔枝");
    dataset.addValue(500, "上海", "荔枝");
    dataset.addValue(500, "广州", "荔枝");
    return dataset;
  }

  private ChartPanel getFrame() {
    return frame;
  }

  private JFreeChart getChart() {
    return chart;
  }

  private void saveCharAsJPG(JFreeChart chart,String savePath,int width,int height) {
    try {
      FileOutputStream outputStream = new FileOutputStream(savePath);
      ChartUtilities.writeChartAsJPEG(outputStream,chart,width,height);
      outputStream.close();
      System.out.println("Write chart to "+savePath+" successfully!");
    } catch (IOException e) {
      System.err.println("saveCharAsJPG error:"+e);
    }
  }

}
