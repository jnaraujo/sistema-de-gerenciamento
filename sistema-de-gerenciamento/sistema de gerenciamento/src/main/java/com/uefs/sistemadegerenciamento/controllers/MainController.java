package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.utils.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {
    @FXML
    public VBox root;

    public static VBox staticRoot;

    @FXML
    public void initialize() throws IOException {
        this.openPage("login.fxml");
        staticRoot = root;
    }

    private void openPage(String url) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(PageLoader.load(url).load());
    }
}