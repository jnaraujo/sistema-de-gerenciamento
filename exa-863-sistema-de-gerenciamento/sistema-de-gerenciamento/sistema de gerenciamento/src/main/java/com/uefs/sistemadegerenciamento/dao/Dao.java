package com.uefs.sistemadegerenciamento.dao;

import java.util.List;

/**
 * Interface para um DAO genérico que contém as operações básicas de persistência de um objeto.
 * @param <T> o tipo de objeto que será salvo
 */
public interface Dao<T> {
    /**
     * Salva um objeto no banco de dados.
     * Se o objeto já existir, ele será atualizado.
     * @param t o objeto a ser salvo
     * @return o objeto salvo
     */
    T save(T t);

    /**
     * Deleta um objeto do banco de dados.
     * @param id o id do objeto a ser deletado
     */
    void delete(String id);

    /**
     * Atualiza um objeto no banco de dados.
     * @param t o objeto a ser atualizado
     */
    void update(T t);

    /**
     * Busca um objeto pelo id.
     * @param id o id do objeto a ser buscado
     * @return o objeto encontrado, ou null caso não exista
     */
    T findById(String id);

    /**
     * Busca todos os objetos do tipo T no banco de dados.
     * @return uma lista com todos os objetos do tipo T
     */
    List<T> getAll();

    /**
     * Deleta todos os objetos do tipo T do banco de dados.
     */
    void deleteAll();
}
