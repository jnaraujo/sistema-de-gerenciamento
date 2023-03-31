package com.uefs.sistemadegerenciamento.dao;

import java.util.List;

public interface Dao<T> {
    /**
     * @param t o objeto a ser salvo
     */
    public void save(T t);

    /**
     * @param id o id do objeto a ser deletado
     */
    public void delete(String id);

    /**
     * @param t o objeto a ser atualizado
     */
    public void update(T t);

    /**
     * @param id o id do objeto a ser buscado
     * @return o objeto com o id passado
     */
    public T findById(String id);

    /**
     * @return todos os objetos
     */
    public List<T> getAll();
}
