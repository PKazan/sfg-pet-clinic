package guru.springframework.sfgpetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService<T, ID> {

    protected Map<Long, T> map = new HashMap<>();

    private Long id = 0L;

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(T object) {
        return map.put(id, object);
    }

    protected Long getNextId() {
        return ++id;

    }

    void delete(T object) {
        map.entrySet().removeIf( entry -> entry.getValue().equals(object));
    }

    void deleteById(ID id) {
        map.remove(id);
    }

}
