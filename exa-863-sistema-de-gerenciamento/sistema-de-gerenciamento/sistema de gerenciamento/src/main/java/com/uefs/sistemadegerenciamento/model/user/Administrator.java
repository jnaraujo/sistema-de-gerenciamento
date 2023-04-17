package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * <p>
 *     Classe que representa um administrador do sistema.
 * </p>
 * <p>
 *     A classe herda de {@link User} e possui um construtor que recebe o nome, email e senha do usuário.
 * </p>
 * <p>
 *     O tipo do usuário é definido como {@link UserType#ADMINISTRATOR}.
 * </p>
 *
 * @see User
 */
public class Administrator extends User {
    /**
     * Criar um administrador
     * @param name Nome do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     */
    public Administrator(
        String name,
        String email,
        String password
    ) {
        super(name, email, password, UserType.ADMINISTRATOR);
    }
}
