package com.uefs.sistemadegerenciamento.constants;

/**
 * Classe que contém os status possíveis de uma WorkOrder
 *
 * @author Jônatas Araújo
 */
public class OrderStatus {
    /**
     * Status de uma WorkOrder que está em andamento
     */
    public static final String OPEN = "Em andamento";

    /**
     * Status de uma WorkOrder que foi finalizada
     */
    public static final String CLOSED = "Finalizado";

    /**
     * Status de uma WorkOrder que foi cancelada
     */
    public static final String CANCELED = "Cancelado";
}
