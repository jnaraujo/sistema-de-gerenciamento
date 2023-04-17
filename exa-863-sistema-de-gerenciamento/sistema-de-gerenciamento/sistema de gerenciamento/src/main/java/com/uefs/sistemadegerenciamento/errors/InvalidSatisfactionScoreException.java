package com.uefs.sistemadegerenciamento.errors;

/**
 * Exceção lançada quando um valor inválido de satisfação é atribuído a um objeto.
 *
 * @author Jônatas Araújo
 */
public class InvalidSatisfactionScoreException extends Exception{
    /**
     * Cria uma nova instância de InvalidSatisfactionScoreException com a mensagem de erro especificada.
     * @param message mensagem de erro
     */
    public InvalidSatisfactionScoreException(String message) {
        super(message);
    }
}
