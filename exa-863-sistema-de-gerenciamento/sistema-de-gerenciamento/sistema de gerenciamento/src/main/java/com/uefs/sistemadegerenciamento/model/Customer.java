package com.uefs.sistemadegerenciamento.model;

/**
 * Classe que representa um Consumidor - o cliente da loja
 */
public class Customer {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;

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
     * @return Retorna o id do consumidor
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
     * @return Retorna o nome do consumidor
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
     * @return Retorna o endereço do consumidor
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
     * @return Retorna o telefone do consumidor
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
     * @return Retorna o email do consumidor
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
