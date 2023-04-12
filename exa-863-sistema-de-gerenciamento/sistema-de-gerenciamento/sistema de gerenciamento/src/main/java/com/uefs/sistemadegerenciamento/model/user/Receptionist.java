package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * Classe que representa um Recepionista
 */
public class Receptionist extends User {
    public Receptionist(
        String name,
        String email,
        String password
    ) {
        super(name, email, password, UserType.RECEPCIONIST);
    }
}
