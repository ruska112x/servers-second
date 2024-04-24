package org.karabalin.task10;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyTSHashMap<K, V> {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    private final HashMap<K, V> map;

    public MyTSHashMap() {
        this.map = new HashMap<>();
    }

    public MyTSHashMap(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    public V put(K key, V value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public V remove(K key) {
        writeLock.lock();
        try {
            return map.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean containsKey(K key) {
        readLock.lock();
        try {
            return map.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    public int size() {
        readLock.lock();
        try {
            return map.size();
        } finally {
            readLock.unlock();
        }
    }

    public Set<K> keySet() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public Collection<V> valueSet() {
        readLock.lock();
        try {
            return map.values();
        } finally {
            readLock.unlock();
        }
    }
}
