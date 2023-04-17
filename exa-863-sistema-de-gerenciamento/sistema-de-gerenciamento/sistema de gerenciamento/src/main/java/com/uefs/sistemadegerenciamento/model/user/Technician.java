package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * <p>
 *     Classe que representa um técnico no sistema.
 * </p>
 * <p>
 *     A classe herda de {@link User} e possui um construtor que recebe o nome, email e senha do usuário.
 *     O tipo do usuário é definido como {@link UserType#TECHNICIAN}.
 * </p>
 *
 * @see User
 * @author Jônatas Araújo
 */
public class Technician extends User {
    /**
     * Criar um técnico com nome, email e senha.
     * @param name Nome do técnico
     * @param email Email do técnico
     * @param password Senha do técnico
     */
    public Technician(
        String name,
        String email,
        String password
    ) {
        super(name, email, password, UserType.TECHNICIAN);
    }
}
