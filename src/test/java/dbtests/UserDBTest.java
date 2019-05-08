package dbtests;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yakimov.entities.User;
import com.yakimov.entities.UserRecord;
import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.model.ConnectionSource;
import com.yakimov.model.UsersTable;

public class UserDBTest {
    @BeforeClass
    public static void initializeTestDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ConnectionSource.connectToDatabase("testdb");
    }

    @AfterClass
    public static void initializeDefaultDatabase() {
        ConnectionSource.connectToDefaultDatabase();
    }

    @After
    public void truncateDatabase() {
        try {
            UsersTable.getInstance().executeSystemUpdate("TRUNCATE TABLE USERS");
            UsersTable.getInstance().executeSystemQuery("SELECT setval('users_id_seq', 1)");
        } catch (ActiveRecordException e) {
            System.out.println("Error while truncate Query: " + e.getMessage());
        }
    }

    @Test
    public void userGetByIDsTest() {
        UserRecord[] records = initTestRecords();
        try {
            for (UserRecord userRecord : records) {
                userRecord.save();
            }
            List<User> list = UsersTable.getInstance().getRecordsListByIds(2, 4, 5);
            assertTrue(list != null);
            assertTrue(!list.isEmpty());
            assertTrue(list.size() == 3);
            assertTrue(list.get(0).getLogin().equals("User1"));
            assertTrue(list.get(1).getLogin().equals("User3"));
            assertTrue(list.get(2).getLogin().equals("User4"));
        } catch (ActiveRecordException e) {
            System.err.println("error in GetListTest: " + e.getMessage());
        }
    }

    @Test
    public void GetByRuleTest() {
        UserRecord[] records = initTestRecords();
        try {
            for (UserRecord userRecord : records) {
                userRecord.save();
            }
            String rule = "login = 'User1' or login = 'User2'";
            List<User> list = UsersTable.getInstance().getRecordsListByRule(rule);
            assertTrue(list != null);
            assertTrue(!list.isEmpty());
            assertTrue(list.size() == 2);
//TODO delete            System.err.println("getByRuleTest list(0) == | " + list.get(0).getLogin() + " | " + "User1 |");
            assertTrue(list.get(0).getLogin().compareTo("User1") == 0);
        } catch (ActiveRecordException e) {
            System.err.println("error in GetListTest: " + e.getMessage());
        }
    }

    @Test
    public void GetAllTest() {
        UserRecord[] records = initTestRecords();
        try {
            for (UserRecord userRecord : records) {
                userRecord.save();
            }
            List<User> list = UsersTable.getInstance().getAllRecordsList();
            assertTrue(list != null);
            assertTrue(!list.isEmpty());
            assertTrue(list.size() == 5);
        } catch (ActiveRecordException e) {
            System.err.println("error in GetListTest: " + e.getMessage());
        }
    }

    private UserRecord[] initTestRecords() {
        String[] logins = { "User1", "User2", "User3", "User4", "User5" };
        String password = "password";
        UserRecord[] records = new UserRecord[5];
        for (int i = 0; i < records.length; i++) {
            records[i] = new UserRecord(new User(logins[i], password));
        }
        return records;
    }

    @Test
    public void SaveTest() {
        UserRecord record = new UserRecord(new User("Gabenushka", "password"));
        try {
            record.save();
            User user = UsersTable.getInstance().getRecordByLogin("Gabenushka").get();
            assertTrue(user != null);
        } catch (ActiveRecordException e) {
            System.out.println("Error in test ||" + "QueryTest//userSaveTest|| " + e.getErrorCode().getMessage() + "\n"
                    + e.getMessage());
        }
    }

    @Test
    public void DeleteTest() {
        UserRecord record = new UserRecord(new User("Gabenushka", "password"));
        try {
            record.save();
            Optional<User> opt = UsersTable.getInstance().getRecordByLogin("Gabenushka");
            assertTrue(opt.isPresent());
            UsersTable.getInstance().delete("Gabenushka");
            opt = UsersTable.getInstance().getRecordByLogin("Gabenushka");
            assertTrue(opt.isEmpty());
        } catch (ActiveRecordException e) {
            System.out.println("Error in test ||" + "QueryTest//userDeleteTest|| " + e.getErrorCode().getMessage()
                    + "\n" + e.getMessage());
        }
    }
}
