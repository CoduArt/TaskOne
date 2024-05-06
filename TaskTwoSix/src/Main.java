import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        PutOrderMap<Integer, Integer> putOrderMap = new PutOrderMap<>(new HashMap<>());
        putOrderMap.put(1, 2);
        putOrderMap.put(2, 3);
        putOrderMap.put(3, 4);
        putOrderMap.put(4, 5);
        putOrderMap.put(5, 13);
        putOrderMap.put(4, 10);
        putOrderMap.put(4, 10);
        putOrderMap.put(6, 0);
        putOrderMap.remove(4);

        for (Block<Integer, Integer> block: putOrderMap) {
            System.out.println(block.first + " " + block.second);
        }
        System.out.println("------------------------------------");
        PutOrderMap<String, Integer> putOrderMap2 = new PutOrderMap<>(new MyMap<>());
        putOrderMap2.put("key", 100);
        putOrderMap2.put("word", 1337);
        putOrderMap2.put("put", 789);
        putOrderMap2.put("101", 113);
        putOrderMap2.put("101", 114);
        putOrderMap2.put("110", 113);
        putOrderMap2.remove("put");
        System.out.println(putOrderMap2.get("110"));
        for (Block<String, Integer> block: putOrderMap2) {
            System.out.println(block.first + " " + block.second);
        }
    }
}
