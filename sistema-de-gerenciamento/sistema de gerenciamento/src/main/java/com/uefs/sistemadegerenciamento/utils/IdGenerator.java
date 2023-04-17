package com.uefs.sistemadegerenciamento.utils;

/**
 * <p>
 *     Classe que gera um id aleatório
 * </p>
 * <p>
 *     A classe gera um id aleatório utilizando a classe {@link java.util.UUID}
 *     e retorna uma string com o id gerado.
 * </p>
 *
 * @see java.util.UUID
 * @author Jônatas Araújo
 */
public class IdGenerator {
    /**
     * Gera um id aleatório
     * @return Retorna um id gerado aleatoriamente
     */
    public static String generate() {
        return java.util.UUID.randomUUID().toString();
    }
}
