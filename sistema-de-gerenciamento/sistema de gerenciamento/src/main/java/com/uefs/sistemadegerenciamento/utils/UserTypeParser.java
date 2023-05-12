package com.uefs.sistemadegerenciamento.utils;

import com.uefs.sistemadegerenciamento.constants.UserType;

public class UserTypeParser {
    public static String toString(UserType userType) {
        return switch (userType) {
            case ADMINISTRATOR -> "Administrador";
            case RECEPTIONIST -> "Recepcionista";
            case TECHNICIAN -> "Técnico";
        };
    }

    public static UserType fromString(String s) {
        return switch (s) {
            case "Administrador" -> UserType.ADMINISTRATOR;
            case "Recepcionista" -> UserType.RECEPTIONIST;
            case "Técnico" -> UserType.TECHNICIAN;
            default -> null;
        };
    }
}
