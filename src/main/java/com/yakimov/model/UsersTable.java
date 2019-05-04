package com.yakimov.model;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yakimov.entities.User;
import com.yakimov.exceptions.ActiveRecordException;

public class UsersTable extends DataTable<User> {
    static final Logger logger = LogManager.getLogger();
    private static UsersTable ourTable = new UsersTable();

    private UsersTable() {

    }

    public static UsersTable getInstance() {
        return ourTable;
    }

    @Override
    public void create(User record) throws ActiveRecordException {
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("INSERT INTO users(login,password) VALUES('%s', '%s')", record.getLogin(),
                    record.getPassword());
            connection.prepareStatement(query);
            connection.executeUpdate();
        }
    }

    @Override
    public void update(int id, User record) throws ActiveRecordException {
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("UPDATE USERS " + "SET login = '%s', password = '%s'" + " WHERE id = '%d'",
                    record.getLogin(), record.getPassword(), id);
            connection.prepareStatement(query);
            connection.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws ActiveRecordException {
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("DELETE from USERS WHERE id = %d", id);
            connection.prepareStatement(query);
            connection.executeUpdate();
        }
    }
    
    public void delete(String login) throws ActiveRecordException{
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("DELETE from USERS WHERE login = '%s'", login);
            connection.prepareStatement(query);
            connection.executeUpdate();
        }
    }

    @Override
    public Optional<User> getRecordById(int id) throws ActiveRecordException{
        Optional<User> user = Optional.empty();
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("SELECT * from USERS WHERE id = %d", id);
            connection.prepareStatement(query);
            UserResult result = connection.executeQuery();
            user = result.parseNext();
            
        }
        return user;
    }
    
    public Optional<User> getRecordByLogin(String login) throws ActiveRecordException{
        Optional<User> user = Optional.empty();
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("SELECT * from USERS WHERE login = '%s'", login);
            connection.prepareStatement(query);
            UserResult result = connection.executeQuery();
            user = result.parseNext();
        }
        return user;
    }
    
    public void executeSystemUpdate(String query) throws ActiveRecordException{
        try (UsersConnection connection = new UsersConnection();) {
            connection.prepareStatement(query);
            connection.executeUpdate();
        }
    }
    
    public UserResult executeSystemQuery(String query) throws ActiveRecordException{
        try (UsersConnection connection = new UsersConnection();) {
            connection.prepareStatement(query);
            return connection.executeQuery();
        }
    }

    @Override
    public List<User> getRecordsListByIds(int... IDs) throws ActiveRecordException {
        List<User> list;
        try (UsersConnection connection = new UsersConnection();) {
            String query = createQueryForIds(IDs);
            connection.prepareStatement(query);
            UserResult result = connection.executeQuery();
            list = result.parseToRecordsList();
        }
        return list;
    }
    private String createQueryForIds(int ...IDs) {
        StringBuffer buffer = new StringBuffer("SELECT * from USERS WHERE ");
        for (int i = 0; i < IDs.length; i++) {
            buffer.append("id = ").append(IDs[i]);
            if (indexIsNotLast(i, IDs)) {
                buffer.append(" or ");
            }
        }
        return buffer.substring(0);
    }
    
    private boolean indexIsNotLast(int i, int ...IDs) {
        return i != IDs.length - 1;
    }

    @Override
    public List<User> getRecordsListByRule(String rule) throws ActiveRecordException {
        List<User> list = null;
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("SELECT * from USERS WHERE " + rule);
            connection.prepareStatement(query);
            UserResult result = connection.executeQuery();
            list = result.parseToRecordsList();
        }
        return list;
    }

    @Override
    public List<User> getAllRecordsList() throws ActiveRecordException {
        List<User> list = null;
        try (UsersConnection connection = new UsersConnection();) {
            String query = String.format("SELECT * from USERS");
            connection.prepareStatement(query);
            UserResult result = connection.executeQuery();
            list = result.parseToRecordsList();
        }
        return list;
    }
}
