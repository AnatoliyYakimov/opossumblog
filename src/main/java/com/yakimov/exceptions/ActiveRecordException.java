package com.yakimov.exceptions;

public class ActiveRecordException extends Exception {
    public enum ErrorCode{
        OK("OK"),
        USER_EXISTS_ERROR("User with this login already exist"),
        USER_DOESNT_EXISTS_ERROR("User doesnt exist"),
        SQL_CONNECTION_ERROR("SQL Connection error"),
        PARSE_ERROR("Error while parsing result");
        
        private String msg;
        
        ErrorCode(String msg){
            this.msg = msg;
        }
        
        public String getMessage() {
            return msg;
        }
        
    }
    
    private ErrorCode err = ErrorCode.OK;
    private Exception ex = null;
    
    public ActiveRecordException(ErrorCode err, Exception ex) {
        super(ex.getMessage());
        this.err = err;
        this.ex = ex;
    }
    
    public ActiveRecordException(ErrorCode err) {
        super(err.getMessage());
    }
    
    
    public Exception getLinkedException() {
        return ex;
    }
    
    public ErrorCode getErrorCode() {
        return err;
    }
    
    public void setErrorCode(ErrorCode newErrCode) {
        err = newErrCode;
    }
    
    
    
}
