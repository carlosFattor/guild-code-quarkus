package org.guildcode.application.context;

import javax.enterprise.context.RequestScoped;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class ScopedContextStorage {

    private static Map<String, Object> storage = null;

    public ScopedContextStorage() {
    }

    public void create() {
        storage = new HashMap<String, Object>();
    }

    public void put(String key, Object value) {
        storage.put(key, value);
    }

    public Object get(String key) {
        return storage.get(key);
    }
}
