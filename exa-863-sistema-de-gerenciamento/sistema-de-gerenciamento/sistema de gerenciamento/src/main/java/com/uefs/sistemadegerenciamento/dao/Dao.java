package com.uefs.sistemadegerenciamento.dao;

import java.util.List;

public interface Dao<T> {
    /**
     * @param t o objeto a ser salvo
     */
    void save(T t);

    /**
     * @param id o id do objeto a ser deletado
     */
    void delete(String id);

    /**
     * @param t o objeto a ser atualizado
     */
    void update(T t);

    /**
     * @param id o id do objeto a ser buscado
     * @return o objeto com o id passado
     */
    T findById(String id);

    /**
     * @return todos os objetos
     */
    List<T> getAll();

    /**
     * Deleta todos os objetos
     */
    void deleteAll();
}
