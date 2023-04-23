package com.uefs.sistemadegerenciamento.model;

import java.io.Serializable;

/**
 * <p>
 *     Classe que representa um consumidor no sistema
 * </p>
 * <p>
 *     A classe contém os atributos id, nome, endereço, telefone e email
 * </p>
 *
 * @see WorkOrder
 * @author Jônatas Araújo
 */
public class Customer implements Serializable {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;

    /**
     * Criar um consumidor com os atributos id, nome, endereço, telefone e email
     * @param name Nome do consumidor
     * @param address Endereço do consumidor
     * @param phone Telefone do consumidor
     * @param email Email do consumidor
     */
    public Customer(
        String name,
        String address,
        String phone,
        String email
    ) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Retorna o id do consumidor
     * @return o id do consumidor
     */
    public String getId() {
        return id;
    }

    /**
     * Define o id do consumidor
     * @param id Id do consumidor
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o nome do consumidor
     * @return o nome do consumidor
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do consumidor
     * @param name Nome do consumidor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o endereço do consumidor
     * @return o endereço do consumidor
     */
    public String getAddress() {
        return address;
    }

    /**
     * Define o endereço do consumidor
     * @param address Endereço do consumidor
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retorna o telefone do consumidor
     * @return o telefone do consumidor
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Define o telefone do consumidor
     * @param phone Telefone do consumidor
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retorna o email do consumidor
     * @return o email do consumidor
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do consumidor
     * @param email Email do consumidor
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) obj;

        return customer.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone
                + ", address=" + address + "]";
    }
}
