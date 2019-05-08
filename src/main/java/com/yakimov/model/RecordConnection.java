package com.yakimov.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;

public abstract class RecordConnection<Record> implements AutoCloseable{
    protected Connection connection;
    protected PreparedStatement statement;

    RecordConnection() throws ActiveRecordException {
        try {
            connection = ConnectionSource.accuireConnection();
        } catch (SQLException e) {
            throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
        }
    }
    

    abstract public QueryResult<Record> executeQuery() throws ActiveRecordException;

    abstract public QueryResult<Record> executeQueryWithArgument(String arg) throws ActiveRecordException;

    public void prepareStatement(String query) throws ActiveRecordException {
        try {
            statement = connection.prepareStatement(query);
        } catch (SQLException e) {
            UsersTable.logger.error("Error while preparing statement: wrong query: " + query);
            throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
        }
    }

    public int executeUpdate() throws ActiveRecordException {
        try {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw handleSQLExsception(e);
        }
    }
    
    protected ActiveRecordException handleSQLExsception(SQLException ex) {
        ActiveRecordException recEx = new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR,ex);
        String msg = ex.getMessage();
        if (msg.startsWith("ERROR: duplicate key value")) {
            recEx.setErrorCode(ErrorCode.RECORD_EXISTS_ERROR);
        }
        return recEx;
    }
    
    public void close() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
}