package toy.swak.dependency.injector;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class ComponentPool {
    private static final Map<String, Object> dependencyMap = new ConcurrentHashMap<>();
    private static ComponentPool instance;

    private ComponentPool() {
    }

    public static synchronized ComponentPool getInstance() {
        if (instance == null) {
            return new ComponentPool();
        }

        return instance;
    }

    public Object get(String key) {
        return dependencyMap.get(key);
    }

    public void put(String key, Object value) {
        dependencyMap.put(key, value);
    }

    @Override
    public String toString() {
        List<String> keyValueSimpleStrings = new LinkedList<>();
        for (Map.Entry<String, Object> stringObjectEntry : dependencyMap.entrySet()) {
            String[] keySplit = stringObjectEntry.getKey().split("\\.");
            String[] valueSplit = stringObjectEntry.getValue().toString().split("\\.");

            keyValueSimpleStrings.add(keySplit[keySplit.length - 1] + "=" + valueSplit[valueSplit.length - 1]);
        }

        return "{" + String.join(",", keyValueSimpleStrings) + "}";
    }
}
