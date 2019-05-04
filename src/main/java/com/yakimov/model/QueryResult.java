package com.yakimov.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yakimov.entities.User;
import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;

class UserResult {
    ResultSet result;

    UserResult(ResultSet result) {
        this.result = result;
    }

    public Optional<User> parseNext() throws ActiveRecordException {
        try {
            result.next();
            Optional<User> user = parseCurrent();
            return user;
        } catch (SQLException e) {
            throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
        }
    }

    public Optional<User> parseCurrent() throws ActiveRecordException {
        User user = null;
        try {
            int id = result.getInt("id");
            String login = result.getString("login").replaceAll("\\s", "");
            String password = result.getString("password").replaceAll("\\s", "");
            user = new User(login, password);
            user.setId(id);
        } catch (SQLException e) {
            if (notEmptyResultException(e)) {
                throw new ActiveRecordException(ErrorCode.PARSE_ERROR, e);
            }
        }
        return Optional.ofNullable(user);
    }
    
    private boolean notEmptyResultException(SQLException e) {
        return !(e.getMessage().startsWith("ResultSet not positioned properly"));
    }

    public List<User> parseToUsersList() throws ActiveRecordException {
        List<User> list = new ArrayList<>();
        Optional<User> user = parseNext();
        while(user.isPresent()) {
            list.add(user.get());
            user = parseNext();
        }
        return list;
    }

}
