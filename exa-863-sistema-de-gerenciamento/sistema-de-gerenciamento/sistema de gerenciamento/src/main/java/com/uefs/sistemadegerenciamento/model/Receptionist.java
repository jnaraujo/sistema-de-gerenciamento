package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * Classe que representa um Recepionista
 */
public class Receptionist extends User{
    public Receptionist(
        String id,
        String name,
        String email,
        String password
    ) {
        super(id, name, email, password, UserType.RECEPCIONIST);
    }
}
