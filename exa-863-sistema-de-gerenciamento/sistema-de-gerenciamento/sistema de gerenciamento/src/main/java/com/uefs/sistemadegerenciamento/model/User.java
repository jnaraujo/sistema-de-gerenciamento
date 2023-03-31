package com.uefs.sistemadegerenciamento.model;


/**
 * Classe que representa um usuário do sistema.
 */
public abstract class User {
    private final String id;
    private String name;
    private String email;

    public User(
        String id,
        String name,
        String email
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return Retorna o id do usuário
     */
    public String getId() {
        return id;
    }

    /**
     * @return Retorna o nome do usuário
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do usuário
     * @param name Nome do usuário
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Retorna o email do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário
     * @param email Email do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;

        return user.getId().equals(this.getId());
    }
}
