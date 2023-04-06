package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.User;

/**
 * Classe que representa um técnico
 */
public class Technician extends User {
    public Technician(
        String id,
        String name,
        String email,
        String password
    ) {
        super(id, name, email, password, UserType.TECHNICIAN);
    }
}
