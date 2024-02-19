import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        SelfHashMap<String, Integer> map = new SelfHashMap<>();
        map.put("s", 2);
        map.put("d", 4);
        map.print();
        map.put("s", 3);
        map.put("a", 3);
        map.print();
        System.out.println("-------------");
        map.clear();
        map.print();
        System.out.println(map.isEmpty());
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.print();
        System.out.println(map.isEmpty());
        System.out.println("----------------");
        System.out.println("ключ one - " + map.containsKey("one"));
        System.out.println("ключ z - " + map.containsKey("z"));
        System.out.println("значение 1 - " + map.containsValue(1));
        System.out.println("значение 10 - " + map.containsValue(10));
        System.out.println("----------------");
        System.out.println(map.get("two") + "; Класс - " + map.get("two").getClass());
        map.remove("two");
        map.print();
        Set<String> set = map.keySet();
        System.out.println(set);
        Collection<Integer> collection = map.values();
        System.out.println(collection);
        System.out.println("----------------");
        SelfHashMap<String, Integer> cloneMap = map.clone();
        cloneMap.put("four", 4);
        cloneMap.print();
        map.print();
        SelfHashMap<String, Integer> map2 = new SelfHashMap<>();
        map2.put("five", 5);
        map2.print();
        map2.putAll(cloneMap);
        map2.print();
        System.out.println(map2.size());

    }
}