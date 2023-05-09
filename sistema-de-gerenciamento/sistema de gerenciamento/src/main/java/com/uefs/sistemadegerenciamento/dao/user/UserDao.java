package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;

import java.util.List;

/**
 * Interface que define os métodos de acesso a dados da classe {@link User}
 * UserDao representa os usuários do sistema.
 * @see User
 * @see Technician
 * @see Administrator
 * @see Receptionist
 * @author Jônatas Araújo
 */
public interface UserDao extends Dao<User> {
    /**
     * Retorna a lista de técnicos
     * @return lista de técnicos
     */
    List<Technician> findAllTechnicians();

    /**
     * Retorna a lista de administradores
     * @return lista de administradores
     */
    List<Administrator> findAllAdministrators();

    /**
     * Retorna a lista de recepcionistas
     * @return lista de recepcionistas
     */
    List<Receptionist> findAllReceptionists();

    /**
     * Retorna um usuário pelo email
     * @param email Email do usuário
     * @return Usuário com o email especificado
     */
    User findByEmail(String email);
}
