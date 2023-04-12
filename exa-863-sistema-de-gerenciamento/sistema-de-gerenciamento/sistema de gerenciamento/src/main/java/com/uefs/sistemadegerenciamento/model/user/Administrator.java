package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * Classe que representa um Administrador
 */
public class Administrator extends User {
    public Administrator(
        String name,
        String email,
        String password
    ) {
        super(name, email, password, UserType.ADMINISTRATOR);
    }
}
