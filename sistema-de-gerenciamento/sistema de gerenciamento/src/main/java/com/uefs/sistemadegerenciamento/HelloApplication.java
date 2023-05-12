package com.uefs.sistemadegerenciamento;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;

        FXMLLoader mainFxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(mainFxmlLoader.load(), 800, 800);

        stage.setTitle("Sistema de Gerenciamento");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();

        stage.show();

    }

    public static void main(String[] args) {
        if(DAOManager.getUserDao().findByEmail("admin") == null) {
            DAOManager.getUserDao().save(
                    new Administrator(
                            "ADMIN",
                            "admin",
                            "admin"
                    )
            );
        }

        DAOManager.getWorkOrderDao().getAll().forEach(workOrder -> {
            workOrder.setTechnicianId(null);
            DAOManager.getWorkOrderDao().update(workOrder);
        });

//        Customer customer = DAOManager.getCustomerDao().save( new Customer(
//                "Joao",
//                "rua a, 123",
//                "9999999999",
//                "joao@test.com"
//        ));
//
//        DAOManager.getWorkOrderDao().save(
//                new WorkOrder(
//                        "O cliente está reclamando de um problema no sistema. Ele suspeita que seja algo relacionado as memorias ram.",
//                        customer.getId()
//                )
//        );
//
//        customer = DAOManager.getCustomerDao().save( new Customer(
//                "Maria",
//                "rua b, 456",
//                "9999999999",
//                "maria@test.com"
//        ));
//
//        DAOManager.getWorkOrderDao().save(
//                new WorkOrder(
//                        "O cliente disse que o erro ocorre quando ele tenta abrir o navegador. Segundo ela, o problema começou depois que ela instalou um programa de edição de imagens.",
//                        customer.getId()
//                )
//        );


        launch();
    }
}