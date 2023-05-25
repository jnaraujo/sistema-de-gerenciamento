package com.uefs.sistemadegerenciamento.controllers;

import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.PageLoader;

/**
 * Classe abstrata que representa um controlador de tela.
 * Todos os controladores de tela devem herdar dessa classe.
 * Essa classe possui métodos que são comuns a todos os controladores de tela.
 *
 * @see PageLoader
 * @see User
 */
public abstract class Controller {
    private User loggedUser;
    private String previousPage;

    /**
     * Cria um controlador de tela.
     */
    public Controller(){
        this.previousPage = "home.fxml";
    }

    /**
     * Define o usuário logado no sistema.
     * @param loggedUser Usuário logado no sistema.
     */
    public void setLoggedUser(User loggedUser){
        this.loggedUser = loggedUser;
    }

    /**
     * Retorna o usuário logado no sistema.
     * @return Usuário logado no sistema.
     */
    public User getLoggedUser(){
        return loggedUser;
    }

    /**
     * Define a página anterior.
     * @param page Página anterior.
     */
    public void setPreviousPage(String page){
        this.previousPage = page;
    }

    /**
     * Retorna a página anterior.
     * @return Página anterior.
     */
    public String getPreviousPage(){
        return previousPage;
    }

    /**
     * Retorna a página anterior.
     * A página anterior padrão é a página home.
     */
    public void backPage(){
        Controller controller = PageLoader.openPage(previousPage);
        controller.setLoggedUser(loggedUser);
    }
}
