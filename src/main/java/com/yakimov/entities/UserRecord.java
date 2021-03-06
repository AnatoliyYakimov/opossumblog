package com.yakimov.entities;

import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.model.UsersTable;

public class UserRecord extends ActiveRecord<User> {
    private User user;

    public UserRecord(User user) {
        this.setUser(user);
        this.setLinkedTable(UsersTable.getInstance());
    }

    @Override
    public void update() throws ActiveRecordException {
        getLinkedTable().update(user.getId(), user);
    }

    @Override
    public void save() throws ActiveRecordException {
        getLinkedTable().create(user);
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
