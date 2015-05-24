package edu.sjsu.cmpe.cache.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.cache.domain.Entry;

public class InMemoryCache implements CacheInterface {
    /** In-memory map cache. (Key, Value) -> (Key, Entry) */
    private final ConcurrentHashMap<Long, Entry> inMemoryMap;

    public InMemoryCache(ConcurrentHashMap<Long, Entry> entries) {
        inMemoryMap = entries;
    }

    @Override
    public Entry save(Entry newEntry) {
        checkNotNull(newEntry, "new Entry instance must not be null");
        inMemoryMap.put(newEntry.getKey(), newEntry);
        Entry newValue = inMemoryMap.get(newEntry.getKey());
        System.out.println("Insert value : " + newEntry.getValue());
        System.out.println("Cache value : " + newValue.getValue());

        return newEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        return inMemoryMap.get(key);
    }

    @Override
    public void delete(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        inMemoryMap.remove(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(inMemoryMap.values());
    }
}
