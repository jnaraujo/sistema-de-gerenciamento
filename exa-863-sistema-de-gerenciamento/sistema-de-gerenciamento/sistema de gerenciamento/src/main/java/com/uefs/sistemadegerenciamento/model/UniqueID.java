package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.erros.InvalidUniqueIDException;

import java.util.UUID;

public class UniqueID {
    private String id;
    public UniqueID(String id) throws InvalidUniqueIDException {
        if(!isValid(id)){
            throw new InvalidUniqueIDException("ID inválido");
        }
        this.id = id;
    }
    public static UniqueID create() throws InvalidUniqueIDException{
        return new UniqueID(generateUniqueID());
    }
    private static String generateUniqueID(){
        return UUID.randomUUID().toString();
    }

    public static boolean isValid(String id) {
        try{
            UUID.fromString(id);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }

    public String toString(){
        return id;
    }
}
