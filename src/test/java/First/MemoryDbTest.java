package First;

import org.junit.Assert;
import org.junit.Test;

public class MemoryDbTest {

    @Test
    public void getByValue() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        Account accountToInsert = new Account(1L, "Warm", 2.5);
        memoryDb.insert(1L, "Warm", 2.5);
        Assert.assertEquals(accountToInsert, memoryDb.getByValue(2.5).get(0));
    }

    @Test
    public void getByAccount() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        Account accountToInsert = new Account(1L, "Warm", 2.5);
        memoryDb.insert(1L, "Warm", 2.5);
        Assert.assertEquals(accountToInsert, memoryDb.getByAccount(1L));
    }

    @Test
    public void getByName() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        Account accountToInsert = new Account(1L, "Warm", 2.5);
        memoryDb.insert(1L, "Warm", 2.5);
        Assert.assertEquals(accountToInsert, memoryDb.getByName("Warm").get(0));
    }

    @Test
    public void updateAccount() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        Account updatedAccount = new Account(2L, "Warm", 2.5);
        memoryDb.insert(1L, "Warm", 2.5);
        memoryDb.updateAccount(1L, 2L);
        Assert.assertEquals(updatedAccount, memoryDb.getByAccount(2L));
    }

    @Test
    public void updateName() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        Account updatedAccount = new Account(1L, "Cool", 2.5);
        memoryDb.insert(1L, "Warm", 2.5);
        memoryDb.updateName("Warm", "Cool");
        Assert.assertEquals(updatedAccount, memoryDb.getByName("Cool").get(0));
    }

    @Test
    public void updateValue() throws AccountNotFoundException, AccountAlreadyExistsException {
        MemoryDb memoryDb = new MemoryDb();
        Account updatedAccount = new Account(1L, "Warm", 5.);
        memoryDb.insert(1L, "Warm", 2.5);
        memoryDb.updateValue(2.5, 5.0);
        Assert.assertEquals(updatedAccount, memoryDb.getByValue(5.0).get(0));
    }

    @Test(expected = AccountNotFoundException.class)
    public void remove() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        memoryDb.insert(1L, "Warm", 2.5);
        memoryDb.remove(1L);
        memoryDb.getByAccount(1L);
    }

    @Test
    public void insert() throws AccountAlreadyExistsException, AccountNotFoundException {
        MemoryDb memoryDb = new MemoryDb();
        Account insertedAccount = new Account(1L, "Warm", 2.5);
        memoryDb.insert(1L, "Warm", 2.5);
        Assert.assertEquals(insertedAccount, memoryDb.getByAccount(1L));
    }
}