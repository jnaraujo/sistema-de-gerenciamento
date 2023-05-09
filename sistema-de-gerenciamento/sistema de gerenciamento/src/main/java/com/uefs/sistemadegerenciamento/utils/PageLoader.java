package com.uefs.sistemadegerenciamento.utils;

import com.uefs.sistemadegerenciamento.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PageLoader {
    public static FXMLLoader load(String url){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(url));

        return fxmlLoader;
    }
}
