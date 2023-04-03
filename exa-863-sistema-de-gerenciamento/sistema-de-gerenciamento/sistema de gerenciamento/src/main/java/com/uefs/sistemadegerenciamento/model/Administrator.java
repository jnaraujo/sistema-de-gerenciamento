package com.uefs.sistemadegerenciamento.model;

/**
 * Classe que representa um Administrador
 */
public class Administrator extends User{
    public Administrator(
        String id,
        String name,
        String email
    ) {
        super(id, name, email);
    }
}
