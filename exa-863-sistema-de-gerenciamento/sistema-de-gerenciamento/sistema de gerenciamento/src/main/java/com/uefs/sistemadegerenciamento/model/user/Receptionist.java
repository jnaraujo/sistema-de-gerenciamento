package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * <p>
 *     Classe que representa um administrador do sistema.
 * </p>
 * <p>
 *     A classe herda de {@link User} e possui um construtor que recebe o nome, email e senha do usuário.
 *     O tipo do usuário é definido como {@link UserType#RECEPTIONIST}.
 * </p>
 *
 * @see User
 * @author Jônatas Araújo
 */
public class Receptionist extends User {
    /**
     * Cria um novo usuário do tipo {@link UserType#RECEPTIONIST}
     * @param name Nome do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     */
    public Receptionist(
        String name,
        String email,
        String password
    ) {
        super(name, email, password, UserType.RECEPTIONIST);
    }
}
