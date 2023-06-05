package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.utils.Formatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ReportController extends Controller {
    @FXML
    private AreaChart<String, Number> revenueChart;

    @FXML
    private AreaChart<String, Number> profileChart;

    @FXML
    private LineChart ordersChart;

    @FXML
    private PieChart satisfactionChart;

    @FXML
    private Label averageRepairTimeLabel;

    @FXML
    private Label averagePriceLabel;

    @FXML
    private Label averageCostLabel;

    @FXML
    private Label averageSatisfactionLabel;

    @FXML
    private void initialize() {
        List<WorkOrder> workOrders = DAOManager.getWorkOrderDao().getAll();
        workOrders.sort(Comparator.comparing(WorkOrder::getCreatedAt));

        showRevenueChart(workOrders);
        showProfitChart(workOrders);
        showSatisfactionChart(workOrders);
        showOrdersChart(workOrders);
        showOtherData();
    }

    private void showRevenueChart(List<WorkOrder> workOrders) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        Map<String, Double> revenueByMonthYear = workOrders.stream()
                .collect(Collectors.groupingBy(workOrder -> formatDate(workOrder.getCreatedAt()),
                        Collectors.summingDouble(WorkOrder::getPrice)));

        for (Map.Entry<String, Double> entry : revenueByMonthYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        revenueChart.getYAxis().setLabel("Receita (R$)");
        revenueChart.getData().add(series);
        revenueChart.setLegendVisible(false);
    }

    private void showProfitChart(List<WorkOrder> workOrders) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        Map<String, Double> profitByMonthYear = workOrders.stream()
                .collect(Collectors.groupingBy(workOrder -> formatDate(workOrder.getCreatedAt()),
                        Collectors.summingDouble(workOrder -> workOrder.getPrice() - workOrder.getCost())));

        for (Map.Entry<String, Double> entry : profitByMonthYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        profileChart.getYAxis().setLabel("Lucro (R$)");
        profileChart.getData().add(series);
        profileChart.setLegendVisible(false);
        profileChart.setAnimated(false);
    }

    private void showSatisfactionChart(List<WorkOrder> workOrders) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("0", 0),
                new PieChart.Data("1", 0),
                new PieChart.Data("2", 0),
                new PieChart.Data("3", 0),
                new PieChart.Data("4", 0),
                new PieChart.Data("5", 0)
        );

        for (WorkOrder workOrder : workOrders) {
            int score = workOrder.getSatisfactionScore();
            pieChartData.get(score).setPieValue(pieChartData.get(score).getPieValue() + 1);
        }

        satisfactionChart.setData(pieChartData);
        satisfactionChart.setLegendVisible(false);
    }

    private void showOrdersChart(List<WorkOrder> workOrders) {
        XYChart.Series<String, Number> openSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> closedSeries = new XYChart.Series<>();

        Map<String, List<WorkOrder>> workOrdersByMonthYear = workOrders.stream()
                .collect(Collectors.groupingBy(workOrder -> formatDate(workOrder.getCreatedAt())));

        for (Map.Entry<String, List<WorkOrder>> entry : workOrdersByMonthYear.entrySet()) {
            int openCount = 0;
            int closedCount = 0;

            for (WorkOrder workOrder : entry.getValue()) {
                if (workOrder.isFinished()) {
                    closedCount++;
                } else {
                    openCount++;
                }
            }

            openSeries.getData().add(new XYChart.Data<>(entry.getKey(), openCount));
            closedSeries.getData().add(new XYChart.Data<>(entry.getKey(), closedCount));
        }

        openSeries.setName("Abertas");
        closedSeries.setName("Fechadas");

        ordersChart.getData().addAll(openSeries, closedSeries);
        ordersChart.setLegendVisible(true);
        ordersChart.setLegendSide(Side.RIGHT);
    }

    private void showOtherData() {
        double averageRepairTime = DAOManager.getWorkOrderDao().getAverageTimeInHoursToRepair();
        double averagePrice = DAOManager.getWorkOrderDao().getAverageWorkOrderPrice();
        double averageCost = DAOManager.getWorkOrderDao().getAverageWorkOrderCost();
        double averageSatisfaction = DAOManager.getWorkOrderDao().getAverageCustomerSatisfaction();

        averageRepairTimeLabel.setText(formatDouble(averageRepairTime) + " horas");
        averagePriceLabel.setText(Formatter.currency(averagePrice));
        averageCostLabel.setText(Formatter.currency(averageCost));
        averageSatisfactionLabel.setText(formatDouble(averageSatisfaction) + " estrelas");
    }

    private String formatDouble(double value) {
        return String.format("%.2f", value);
    }

    private String formatDate(Date date) {
        String newDate = new SimpleDateFormat("MM/yyyy").format(date);
        return newDate;
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }
}
