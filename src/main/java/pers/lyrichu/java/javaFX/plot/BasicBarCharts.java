package pers.lyrichu.java.javaFX.plot;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.*;
import javafx.stage.Stage;

public class BasicBarCharts extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        String[] xData = {"Mon","Tues","Wed","Thurs","Fri"};
        double[] yData = {1.1,2.2,3.3,4.4,5.5};
        Series series = new Series();
        for(int i = 0;i < xData.length;i++) {
            series.getData().add(new Data(xData[i],yData[i]));
        }
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y");

        BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        barChart.getData().add(series);

        Scene scene = new Scene(barChart,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
