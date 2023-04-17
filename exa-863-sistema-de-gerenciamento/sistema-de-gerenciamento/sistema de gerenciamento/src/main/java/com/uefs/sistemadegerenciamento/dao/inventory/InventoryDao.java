package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;

import java.util.List;

/**
 * Interface que define os métodos de acesso a dados da classe {@link ComputerComponent}.
 * InventoryDao representa o estoque de componentes da empresa.
 *
 * @author Jônatas Araújo
 */
public interface InventoryDao extends Dao<ComputerComponent> {
    /**
     * Retorna a média de preço dos componentes
     * @return média de preço dos componentes
     */
    double getAveragePrice();

    /**
     * Retorna a média de custo dos componentes
     * @return média de custo dos componentes
     */
    double getAverageCost();

    /**
     * Retorna a média de preço dos componentes disponíveis
     * @return lista de componentes disponíveis
     */
    List<ComputerComponent> findAvailableComponents();
}
