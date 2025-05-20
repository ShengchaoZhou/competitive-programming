package grammer;

import java.util.HashSet;
import java.util.Set;

public class setDemo {
    public static void main(String[] args) {
        Set<Integer> hash = new HashSet<>();
        hash.add(10); hash.add(20); hash.add(30);
        hash.contains(10);
        hash.isEmpty();
        hash.size();
        hash.remove(20);
        for (int x : hash) System.out.println(x);
        hash.clear();
    }
}
