package com.yakimov.model;
import java.sql.SQLException;

import com.yakimov.entities.User;
import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;

class UsersConnection extends RecordConnection<User>{

        UsersConnection() throws ActiveRecordException {
            super();
        }
        
        @Override
        public UserResult executeQuery() throws ActiveRecordException {
            try {
                return new UserResult(statement.executeQuery());
            } catch (SQLException e) {
                throw handleSQLExsception(e);
            }
        }
        
        @Override
        public UserResult executeQueryWithArgument(String arg) throws ActiveRecordException {
            try {
                return new UserResult(statement.executeQuery(arg));
            } catch (SQLException e) {
                throw new ActiveRecordException(ErrorCode.SQL_CONNECTION_ERROR, e);
            }
        }
    }
