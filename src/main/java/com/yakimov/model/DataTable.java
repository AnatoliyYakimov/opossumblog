package com.yakimov.model;

import java.util.List;
import java.util.Optional;

import com.yakimov.exceptions.ActiveRecordException;

public abstract class DataTable<Record>{
    abstract public void create(Record record) throws ActiveRecordException;
    
    abstract public void update(int id, Record record) throws ActiveRecordException;
    
    abstract public void delete(int id) throws ActiveRecordException;
    
    abstract public Optional<Record> getRecordById(int id) throws ActiveRecordException;
    abstract public List<Record> getRecordsListByIds(int ...ids) throws ActiveRecordException;
    abstract public List<Record> getRecordsListByRule(String rule) throws ActiveRecordException;
    abstract public List<Record> getAllRecordsList() throws ActiveRecordException;
    abstract public QueryResult<Record> executeSystemQuery(String query) throws ActiveRecordException;
    abstract public void executeSystemUpdate(String query) throws ActiveRecordException;
    
    
}
