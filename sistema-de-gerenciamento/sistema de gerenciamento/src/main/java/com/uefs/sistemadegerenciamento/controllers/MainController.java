package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {
    @FXML
    private VBox root;

    @FXML
    private static VBox staticRoot;

    @FXML
    private void initialize() throws IOException {
        this.openPage("login.fxml");
        staticRoot = root;
    }

    private void openPage(String url) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(PageLoader.load(url).load());
    }
}
