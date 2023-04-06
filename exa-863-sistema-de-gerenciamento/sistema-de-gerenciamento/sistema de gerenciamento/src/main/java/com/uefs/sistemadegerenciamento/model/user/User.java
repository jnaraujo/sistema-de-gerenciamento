package com.uefs.sistemadegerenciamento.model.user;


import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * Classe que representa um usuário do sistema.
 */
public abstract class User {
    private final String id;
    private String name;
    private String email;
    private String password;
    private UserType userType;

    public User(
        String id,
        String name,
        String email,
        String password,
        UserType userType
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

        this.userType = userType;
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

    /**
     * @return Retorna a senha do usuário
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do usuário
     * @param password Senha do usuário
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Retorna o tipo do usuário
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Define o tipo do usuário
     * @param userType Tipo do usuário
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;

        return user.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", userType="
                + userType + "]";
    }
}
