package com.yakimov.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yakimov.entities.User;
import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;

abstract class QueryResult<Record>{
    protected ResultSet result;
    
    public QueryResult(ResultSet result) {
        this.result = result;
    }
    
    abstract public Optional<Record> parseNext() throws ActiveRecordException;
    abstract public Optional<Record> parseCurrent() throws ActiveRecordException;
    abstract public List<Record> parseToRecordsList() throws ActiveRecordException;
}

class UserResult extends QueryResult<User>{

    UserResult(ResultSet result) {
        super(result);
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

    public List<User> parseToRecordsList() throws ActiveRecordException {
        List<User> list = new ArrayList<>();
        Optional<User> user = parseNext();
        while(user.isPresent()) {
            list.add(user.get());
            user = parseNext();
        }
        return list;
    }
}
