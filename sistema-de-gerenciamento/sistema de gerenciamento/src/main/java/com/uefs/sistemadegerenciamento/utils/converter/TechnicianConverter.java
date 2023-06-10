package com.uefs.sistemadegerenciamento.utils.converter;

import com.uefs.sistemadegerenciamento.model.user.Technician;
import javafx.util.StringConverter;
import java.util.List;

public class TechnicianConverter extends StringConverter<Technician>{
    List<Technician> technicians;

    public TechnicianConverter(List<Technician> technicians){
        this.technicians = technicians;
    }

    @Override
    public String toString(Technician technician) {
        if(technician == null) return "";

        return technician.getName() + " - " + technician.getEmail();
    }

    @Override
    public Technician fromString(String string) {
        return technicians
                .stream()
                .filter(technician -> this.toString(technician).equals(string))
                .findFirst()
                .orElse(null);
    }
}
