package First;

import java.util.*;


public class MemoryDb {
    private List<Account> sortedAccount = new ArrayList<>();
    private List<Account> sortedNames = new ArrayList<>();
    private List<Account> sortedValues = new ArrayList<>();

    public void insert(Long account, String name, Double value) throws AccountAlreadyExistsException {
        Account insertAccount = new Account(account, name, value);
        try {
            if(getByAccount(account) != null) {
                throw new AccountAlreadyExistsException("Такой аккаунт уже существует в базе");
            }
        } catch (AccountNotFoundException e) {
            sortedAccount.add(insertAccount);
            sortedNames.add(insertAccount);
            sortedValues.add(insertAccount);
            sortedAccount.sort(Comparator.comparing((Account o) -> o.getAccount()));
            sortedNames.sort(Comparator.comparing((Account o) -> o.getName()));
            sortedValues.sort(Comparator.comparing((Account o) -> o.getValue()));
        }
    }

    public Account getByAccount(Long account) throws AccountNotFoundException {
        int posOfRecord = Collections.binarySearch(sortedAccount, new Account(account, null, null),
                (o1, o2) -> (o1.getAccount().compareTo(o2.getAccount())));
        if (posOfRecord < 0 || posOfRecord == sortedAccount.size()) {
            throw new AccountNotFoundException("Аккаунт не найден");
        }
        return sortedAccount.get(posOfRecord);
    }

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
        }
        else if (key instanceof Double) {
            while (firstMatch > 0 && Objects.equals(list.get(firstMatch - 1).getValue(), key)) {
                firstMatch--;
            }
            while (lastMatch < list.size() - 1 && Objects.equals(list.get(lastMatch + 1).getValue(), key)) {
                lastMatch++;
            }
        }
        return list.subList(firstMatch, lastMatch + 1);
    }

    public List<Account> getByName(String name) throws AccountNotFoundException {
        int posOfRecord = Collections.binarySearch(sortedNames, new Account(null, name, null),
                (o1, o2) -> (o1.getName().compareTo(o2.getName())));
        if (posOfRecord < 0 || posOfRecord == sortedNames.size()) {
            throw new AccountNotFoundException("Аккаунт не найден");
        }
        return binarySearchFindSublist(posOfRecord, sortedNames, name);
    }

    public List<Account> getByValue(Double value) throws AccountNotFoundException {
        int posOfRecord = Collections.binarySearch(sortedValues, new Account(null, null, value),
                (o1, o2) -> (o1.getValue().compareTo(o2.getValue())));
        if (posOfRecord < 0 || posOfRecord == sortedValues.size()) {
            throw new AccountNotFoundException("Аккаунт не найден");
        }
        return binarySearchFindSublist(posOfRecord, sortedValues, value);
    }

    public void remove(Long account) {
        Account removeAccount = new Account(account, null, null);
        sortedAccount.remove(removeAccount);
        sortedNames.remove(removeAccount);
        sortedValues.remove(removeAccount);
    }

    public void updateAccount(Long account, Long newAccount) {
        try {
            Account updateAccount = getByAccount(account);
            updateAccount.setAccount(newAccount);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateName(String name, String newName) {
        try {
            List<Account> updatedAccounts = getByName(name);
            for(Account updatedAccount : updatedAccounts) {
                updatedAccount.setName(newName);
            }
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateValue(Double value, Double newValue) {
        try {
            List<Account> updatedAccounts = getByValue(value);
            for(Account updatedAccount : updatedAccounts) {
                updatedAccount.setValue(newValue);
            }
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }
}
