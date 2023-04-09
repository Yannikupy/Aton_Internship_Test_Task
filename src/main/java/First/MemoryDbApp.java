package First;

public class MemoryDbApp {
    public static void main(String[] args) {
        MemoryDb memoryDb = new MemoryDb();
        try {
            memoryDb.insert(13131L, "Yan", 2.5);
            memoryDb.insert(1312532L, "Petr", 4.);
            memoryDb.insert(2L, "Yan", 6.);
            memoryDb.insert(1L, "Stanford", 6.);
            System.out.println(memoryDb.getByAccount(13131L));
            System.out.println(memoryDb.getByName("Yan"));
            System.out.println(memoryDb.getByValue(6.));
            System.out.println(memoryDb.getByName("Petr"));
            memoryDb.updateAccount(1312532L, 13131L);
            System.out.println(memoryDb.getByValue(2.5));
            memoryDb.updateName("Yan", "Yanniku");
            System.out.println(memoryDb.getByName("Yanniku"));
            System.out.println(memoryDb.getByAccount(13131L));
            memoryDb.updateAccount(13131L, 7L);
            System.out.println(memoryDb.getByAccount(7L));
            System.out.println(memoryDb.getByName("Petr"));
            memoryDb.remove(1L);
            System.out.println(memoryDb.getByName("Petr"));
        } catch (AccountNotFoundException | AccountAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
