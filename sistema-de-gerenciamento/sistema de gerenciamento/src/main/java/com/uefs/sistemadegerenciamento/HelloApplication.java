package com.uefs.sistemadegerenciamento;

import com.uefs.sistemadegerenciamento.controllers.MainController;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainFxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(mainFxmlLoader.load(), 500, 500);

        HelloApplication.stage = stage;

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

        launch();
    }
}