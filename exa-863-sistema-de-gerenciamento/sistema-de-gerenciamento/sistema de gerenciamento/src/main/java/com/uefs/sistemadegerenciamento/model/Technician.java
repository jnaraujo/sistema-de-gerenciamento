package com.uefs.sistemadegerenciamento.model;

/**
 * Classe que representa um técnico
 */
public class Technician extends User{
    public Technician(
        String id,
        String name,
        String email
    ) {
        super(id, name, email);
    }
}
