package com.uefs.sistemadegerenciamento.utils;

public class IdGenerator {
    /**
     * @return Retorna um id gerado aleatoriamente
     */
    public static String generate() {
        return java.util.UUID.randomUUID().toString();
    }
}
