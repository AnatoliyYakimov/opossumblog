package com.yakimov.entities;

import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.model.DataTable;

public abstract class ActiveRecord<Entity> {
    private DataTable<Entity> table;
    
    public abstract void save() throws ActiveRecordException;
    public abstract void update() throws ActiveRecordException;
    public abstract boolean isValid();
    
    public DataTable<Entity> getLinkedTable(){
        return table;
    }
    
    protected void setLinkedTable(DataTable<Entity> table) {
        this.table = table;
    }
}
