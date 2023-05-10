package com.uefs.sistemadegerenciamento.utils;

import com.uefs.sistemadegerenciamento.HelloApplication;
import com.uefs.sistemadegerenciamento.controllers.HomeController;
import com.uefs.sistemadegerenciamento.controllers.MainController;
import com.uefs.sistemadegerenciamento.model.user.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PageLoader {
    public static FXMLLoader load(String url){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(url));

        return fxmlLoader;
    }

    /**
     * Abre uma página no sistema
     * @param url - url da página a ser aberta
     * @param <T> - o controller da página
     * @return - o controller da página
     */
    public static  <T> T openPage(String url) {
        try{
            FXMLLoader fxmlLoader = PageLoader.load(url);
            Parent root = fxmlLoader.load();

            MainController.staticRoot.getChildren().clear();
            MainController.staticRoot.getChildren().add(root);

            return fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a página inicial do sistema
     * @param user - o usuário que está logado no sistema
     */
    public static void goHome(User user) {
        HomeController controller = PageLoader.openPage("home.fxml");
        controller.setUser(user);
    }
}
