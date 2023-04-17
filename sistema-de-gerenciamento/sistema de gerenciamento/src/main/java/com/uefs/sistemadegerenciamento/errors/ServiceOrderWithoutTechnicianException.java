package com.uefs.sistemadegerenciamento.errors;

/**
 * Exceção lançada quando uma ordem de serviço não possui um técnico.
 *
 * @author Jônatas Araújo
 */
public class ServiceOrderWithoutTechnicianException extends Exception{
    /**
     * Cria uma nova instância de ServiceOrderWithoutTechnicianException com a mensagem de erro especificada.
     * @param message mensagem de erro
     */
    public ServiceOrderWithoutTechnicianException(String message) {
        super(message);
    }
}
