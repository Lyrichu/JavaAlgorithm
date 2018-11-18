package pers.lyrichu.java.javaFX.plot;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.*;
import javafx.stage.Stage;

public class BasicScatterPlots extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int[] xData = {1,2,3,4,5};
        double[] yData = {1.3,2.1,3.3,4.0,4.8};
        // add data to series
        Series series = new Series();
        for(int i = 0;i < xData.length;i++) {
            series.getData().add(new Data(xData[i],yData[i]));
        }
        // define the axies
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y");
        // create the scatter chart
        ScatterChart<Number,Number> scatterChart = new ScatterChart<>(xAxis,yAxis);
        scatterChart.getData().add(series);
        // create a scene using the chart
        Scene scene = new Scene(scatterChart,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
