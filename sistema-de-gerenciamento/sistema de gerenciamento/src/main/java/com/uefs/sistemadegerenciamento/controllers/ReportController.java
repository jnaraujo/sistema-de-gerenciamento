package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.util.Comparator;
import java.util.List;

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

        for (WorkOrder workOrder : workOrders) {
            series.getData().add(new XYChart.Data<>(workOrder.getCreatedAt().toString(), workOrder.getPrice()));
        }

        revenueChart.getYAxis().setLabel("Receita (R$)");
        revenueChart.getData().add(series);
        revenueChart.setLegendVisible(false);
    }

    private void showProfitChart(List<WorkOrder> workOrders) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (WorkOrder workOrder : workOrders) {
            Double profit = workOrder.getPrice() - workOrder.getCost();
            series.getData().add(new XYChart.Data<>(workOrder.getCreatedAt().toString(), profit));
        }

        profileChart.getYAxis().setLabel("Lucro (R$)");
        profileChart.getData().add(series);
        profileChart.setLegendVisible(false);
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

        for (WorkOrder workOrder : workOrders) {
            if (workOrder.getFinishedAt() != null) {
                closedSeries.getData().add(new XYChart.Data<>(workOrder.getCreatedAt().toString(), 1));
                openSeries.getData().add(new XYChart.Data<>(workOrder.getCreatedAt().toString(), 0));
            } else {
                openSeries.getData().add(new XYChart.Data<>(workOrder.getCreatedAt().toString(), 1));
                closedSeries.getData().add(new XYChart.Data<>(workOrder.getCreatedAt().toString(), 0));
            }
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
        averagePriceLabel.setText("R$ " + formatDouble(averagePrice));
        averageCostLabel.setText("R$ " + formatDouble(averageCost));
        averageSatisfactionLabel.setText(formatDouble(averageSatisfaction) + " estrelas");
    }

    private String formatDouble(double value) {
        return String.format("%.2f", value);
    }

    @FXML
    private void onBackButtonClick() {
        backPage();
    }
}
