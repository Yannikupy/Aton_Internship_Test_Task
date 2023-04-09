package First;

import java.util.*;

public class MemoryDb {
    private List<Account> sortedAccounts = new ArrayList<>();
    private List<Account> sortedNames = new ArrayList<>();
    private List<Account> sortedValues = new ArrayList<>();

    /**
     * Inserts a record in cache.
     * If account is already exists throws AccountAlreadyExistsException.
     *
     * @param account
     * @param name
     * @param value
     * @throws AccountAlreadyExistsException
     */
    public void insert(Long account, String name, Double value) throws AccountAlreadyExistsException {
        Account insertAccount = new Account(account, name, value);
        try {
            if (getByAccount(account) != null) {
                throw new AccountAlreadyExistsException("Такой аккаунт уже существует в базе");
            }
        } catch (AccountNotFoundException e) {
            sortedAccounts.add(insertAccount);
            sortedNames.add(insertAccount);
            sortedValues.add(insertAccount);
            sortedAccounts.sort(Comparator.comparing(Account::getAccount));
            sortedNames.sort(Comparator.comparing(Account::getName));
            sortedValues.sort(Comparator.comparing(Account::getValue));
        }
    }

    /**
     * Gets Account by id(account)
     * If account doesn't exist in cache throws AccountNotFoundException
     *
     * @param account
     * @return Account
     * @throws AccountNotFoundException
     */

    public Account getByAccount(Long account) throws AccountNotFoundException {
        int posOfRecord = Collections.binarySearch(sortedAccounts, new Account(account, null, null),
                Comparator.comparing(Account::getAccount));
        if (posOfRecord < 0 || posOfRecord == sortedAccounts.size()) {
            throw new AccountNotFoundException("Аккаунт не найден");
        }
        return sortedAccounts.get(posOfRecord);
    }

    /**
     * Gets Account by name
     * If account doesn't exist in cache throws AccountNotFoundException
     *
     * @param name
     * @return
     * @throws AccountNotFoundException
     */
    public List<Account> getByName(String name) throws AccountNotFoundException {
        int posOfRecord = Collections.binarySearch(sortedNames, new Account(null, name, null),
                Comparator.comparing(Account::getName));
        if (posOfRecord < 0 || posOfRecord == sortedNames.size()) {
            throw new AccountNotFoundException("Аккаунт не найден");
        }
        return binarySearchFindSublist(posOfRecord, sortedNames, name);
    }

    /**
     * Gets Account by value
     * If account doesn't exist in cache throws AccountNotFoundException
     *
     * @param value
     * @return Account
     * @throws AccountNotFoundException
     */
    public List<Account> getByValue(Double value) throws AccountNotFoundException {
        int posOfRecord = Collections.binarySearch(sortedValues, new Account(null, null, value),
                Comparator.comparing(Account::getValue));
        if (posOfRecord < 0 || posOfRecord == sortedValues.size()) {
            throw new AccountNotFoundException("Аккаунт не найден");
        }
        return binarySearchFindSublist(posOfRecord, sortedValues, value);
    }

    /**
     * Updates account in cache by id(account)
     *
     * @param account
     * @param newAccount
     */
    public void updateAccount(Long account, Long newAccount) {
        Account updateAccount = null;
        try {
            updateAccount = getByAccount(account);
            if (getByAccount(newAccount) != null) {
                throw new IdAlreadyTakenException("Этот аккаунт уже занят");
            }
        } catch (AccountNotFoundException e) {
            assert updateAccount != null;
            updateAccount.setAccount(newAccount);
        } catch (IdAlreadyTakenException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates accounts in cache by name
     *
     * @param name
     * @param newName
     */
    public void updateName(String name, String newName) {
        try {
            List<Account> updatedAccounts = getByName(name);
            for (Account updatedAccount : updatedAccounts) {
                updatedAccount.setName(newName);
            }
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates accounts in cache by value
     *
     * @param value
     * @param newValue
     */
    public void updateValue(Double value, Double newValue) {
        try {
            List<Account> updatedAccounts = getByValue(value);
            for (Account updatedAccount : updatedAccounts) {
                updatedAccount.setValue(newValue);
            }
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes account from cache
     *
     * @param account
     */
    public void remove(Long account) {
        Account removeAccount = new Account(account, null, null);
        sortedAccounts.remove(removeAccount);
        sortedNames.remove(removeAccount);
        sortedValues.remove(removeAccount);
    }

    /**
     * Finds sublist with accounts matching key
     *
     * @param posOfRecord
     * @param list
     * @param key
     * @param <T>
     * @return List<Account> with accounts matching key
     */
    private <T> List<Account> binarySearchFindSublist(int posOfRecord, List<Account> list, T key) {
        int firstMatch = posOfRecord;
        int lastMatch = posOfRecord;
        if (key instanceof String) {
            while (firstMatch > 0 && Objects.equals(list.get(firstMatch - 1).getName(), key)) {
                firstMatch--;
            }
            while (lastMatch < list.size() - 1 && Objects.equals(list.get(lastMatch + 1).getName(), key)) {
                lastMatch++;
            }
        } else if (key instanceof Double) {
            while (firstMatch > 0 && Objects.equals(list.get(firstMatch - 1).getValue(), key)) {
                firstMatch--;
            }
            while (lastMatch < list.size() - 1 && Objects.equals(list.get(lastMatch + 1).getValue(), key)) {
                lastMatch++;
            }
        }
        return list.subList(firstMatch, lastMatch + 1);
    }


}
