package com.yakimov.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;

class RecordConnection implements AutoCloseable{
        Connection connection;
        PreparedStatement statement;

        RecordConnection() throws ActiveRecordException {
            try {
                connection = ConnectionSource.accuireConnection();
            } catch (SQLException e) {
                throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
            }
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

        public void prepareStatement(String query) throws ActiveRecordException {
            try {
                statement = connection.prepareStatement(query);
            } catch (SQLException e) {
                UsersTable.logger.error("Error while preparing statement: wrong query: " + query);
                throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
            }
        }

        public UserResult executeQuery() throws ActiveRecordException {
            try {
                return new UserResult(statement.executeQuery());
            } catch (SQLException e) {
                throw handleSQLExsception(e);
            }
        }
        
        private ActiveRecordException handleSQLExsception(SQLException ex) {
            ActiveRecordException recEx = new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR,ex);
            String msg = ex.getMessage();
            if (msg.startsWith("ERROR: duplicate key value")) {
                recEx.setErrorCode(ErrorCode.USER_EXISTS_ERROR);
            }
            return recEx;
        }
        
        public UserResult executeQueryWithArgument(String arg) throws ActiveRecordException {
            try {
                return new UserResult(statement.executeQuery(arg));
            } catch (SQLException e) {
                throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
            }
        }

        public int executeUpdate() throws ActiveRecordException {
            try {
               return statement.executeUpdate();
            } catch (SQLException e) {
                throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
            }
        }
    }
