package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * Classe que representa um Recepionista
 */
public class Receptionist extends User{
    public Receptionist(
        String id,
        String name,
        String email
    ) {
        super(id, name, email, UserType.RECEPCIONIST);
    }
}
