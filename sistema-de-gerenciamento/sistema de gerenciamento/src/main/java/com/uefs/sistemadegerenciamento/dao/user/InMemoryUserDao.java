package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link UserDao} que armazena os dados em memória.
 * </p>
 *
 * @see UserDao
 * @author Jônatas Araújo
 */
public class InMemoryUserDao implements UserDao{
    private HashMap<String, User> users;

    /**
     * Cria um novo {@link InMemoryUserDao}.
     */
    public InMemoryUserDao() {
        users = new HashMap<>();
    }

    /**
     * Salva um usuário.
     * @param user Usuário a ser salvo.
     * @return Usuário salvo com o ID gerado.
     */
    @Override
    public User save(User user) {
        String id = IdGenerator.generate();
        user.setId(id);

        users.put(user.getId(), user);

        return user;
    }

    /**
     * Deleta um usuário pelo ID.
     * @param userID ID do usuário a ser deletado.
     */
    @Override
    public void delete(String userID) {
        users.remove(userID);
    }

    /**
     * Atualiza um usuário.
     * @param user Usuário a ser atualizado.
     */
    @Override
    public void update(User user) {
        users.replace(user.getId(), user);
    }

    /**
     * Busca um usuário pelo ID.
     * @param id ID do usuário a ser buscado.
     * @return Usuário com o ID informado.
     */
    @Override
    public User findById(String id) {
        return users.get(id);
    }

    /**
     * @param email Email do usuário a ser buscado.
     * @return Todos os usuários cadastrados.
     */
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Deleta todos os usuários.
     */
    @Override
    public void deleteAll() {
        users.clear();
    }

    /**
     * Retorna todos os {@link Technician} cadastrados.
     * @return Todos os técnicos cadastrados.
     */
    @Override
    public List<Technician> findAllTechnicians() {
        List<Technician> technicians = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getUserType().equals(UserType.TECHNICIAN)) {
                technicians.add((Technician) user);
            }
        }
        return technicians;
    }

    /**
     * Retorna todos os {@link Administrator} cadastrados.
     * @return Todos os administradores cadastrados.
     */
    @Override
    public List<Administrator> findAllAdministrators() {
        List<Administrator> administrators = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getUserType().equals(UserType.ADMINISTRATOR)) {
                administrators.add((Administrator) user);
            }
        }
        return administrators;
    }

    /**
     * Retorna todos os {@link Receptionist} cadastrados.
     * @return Todos os recepcionistas cadastrados.
     */
    @Override
    public List<Receptionist> findAllReceptionists() {
        List<Receptionist> receptionists = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getUserType().equals(UserType.RECEPTIONIST)) {
                receptionists.add((Receptionist) user);
            }
        }
        return receptionists;
    }
}
