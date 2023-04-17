package com.uefs.sistemadegerenciamento.model.user;


import com.uefs.sistemadegerenciamento.constants.UserType;

/**
 * <p>
 * Classe abstrata que representa um usuário do sistema.
 *</p>
 *
 * <p>
 * Possui campos para o identificador (id), nome, e-mail, senha e tipo de usuário.
 * </p>
 *
 * <p>
 * Essa classe deve ser estendida por outras classes para adicionar comportamentos específicos
 * de acordo com o tipo de usuário, como por exemplo, um administrador, um cliente, um funcionário, etc.
 *</p>
 *
 * <p>
 * Além disso, essa classe implementa os métodos equals e toString.
 * </p>
 *
 * @see UserType
 * @see Receptionist
 * @see Administrator
 * @see Technician
 */
public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserType userType;

    /**
     * Criar um usuário
     * @param name Nome do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     * @param userType Tipo do usuário
     */
    public User(
        String name,
        String email,
        String password,
        UserType userType
    ) {
        this.name = name;
        this.email = email;
        this.password = password;

        this.userType = userType;
    }

    /**
     * Retorna o id do usuário
     * @return o id do usuário
     */
    public String getId() {
        return id;
    }

    /**
     * Define o id do usuário
     * @param id Id do usuário
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o nome do usuário
     * @return nome do usuário
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
     * Retorna o email do usuário
     * @return o email do usuário
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
     * Retorna a senha do usuário
     * @return a senha do usuário
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
     * Retorna o tipo do usuário
     * @return o tipo do usuário
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

        if(this.getId() == null || user.getId() == null) return false;

        return user.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", userType="
                + userType + "]";
    }
}
