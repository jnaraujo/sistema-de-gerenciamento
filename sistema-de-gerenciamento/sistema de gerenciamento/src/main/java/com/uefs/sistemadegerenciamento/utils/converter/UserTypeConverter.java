package com.uefs.sistemadegerenciamento.utils.converter;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.utils.UserTypeParser;
import javafx.util.StringConverter;

public class UserTypeConverter extends StringConverter<UserType> {
    @Override
    public String toString(UserType type) {
        return UserTypeParser.toString(type);
    }

    @Override
    public UserType fromString(String s) {
        return UserTypeParser.fromString(s);
    }
}
