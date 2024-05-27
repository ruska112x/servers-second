package org.karabalin.task10;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class MyTSHashMap<K, V> implements Map<K, V> {
    private final int size;
    private final Map<K, V>[] maps;
    private final ReentrantReadWriteLock[] locks;

    public MyTSHashMap() {
        this(16);
    }

    public MyTSHashMap(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Wrong count");
        }
        this.size = count;
        this.maps = new Map[count];
        this.locks = new ReentrantReadWriteLock[count];
        for (int i = 0; i < count; ++i) {
            maps[i] = new HashMap<>();
            locks[i] = new ReentrantReadWriteLock();
        }
    }

    @Override
    public void clear() {
        for (ReentrantReadWriteLock lock : locks) {
            lock.writeLock().lock();
        }
        Arrays.stream(maps).forEach(m -> m.clear());
        for (ReentrantReadWriteLock lock : locks) {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean containsKey(Object arg0) {
        return Arrays.stream(maps).anyMatch(m -> m.containsKey(arg0));
    }

    @Override
    public boolean containsValue(Object arg0) {
        return Arrays.stream(maps).anyMatch(m -> m.containsValue(arg0));
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        for (ReentrantReadWriteLock lock : locks) {
            lock.readLock().lock();
        }
        Set<Entry<K, V>> result = Arrays.stream(maps).map(m -> m.entrySet()).flatMap(s -> s.stream())
                .collect(Collectors.toSet());
        for (ReentrantReadWriteLock lock : locks) {
            lock.readLock().unlock();
        }
        return result;
    }

    @Override
    public V get(Object arg0) {
        int hash = arg0.hashCode();
        int mapNumber = hash % size;
        locks[mapNumber].readLock().lock();
        V result = maps[mapNumber].get(arg0);
        locks[mapNumber].readLock().unlock();
        return result;
    }

    @Override
    public boolean isEmpty() {
        return Arrays.stream(maps).allMatch(m -> m.isEmpty());
    }

    @Override
    public Set<K> keySet() {
        for (ReentrantReadWriteLock lock : locks) {
            lock.readLock().lock();
        }
        Set<K> result = Arrays.stream(maps).map(m -> m.keySet()).flatMap(s -> s.stream()).collect(Collectors.toSet());
        for (ReentrantReadWriteLock lock : locks) {
            lock.readLock().unlock();
        }
        return result;
    }

    @Override
    public V put(K arg0, V arg1) {
        int hash = arg0.hashCode();
        int mapNumber = hash % size;
        locks[mapNumber].writeLock().lock();
        V result = maps[mapNumber].put(arg0, arg1);
        locks[mapNumber].writeLock().unlock();
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> arg0) {
        for (Entry<? extends K, ? extends V> entry : arg0.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object arg0) {
        int hash = arg0.hashCode();
        int mapNumber = hash % size;
        locks[mapNumber].writeLock().lock();
        V result = maps[mapNumber].remove(arg0);
        locks[mapNumber].writeLock().unlock();
        return result;
    }

    @Override
    public int size() {
        int result = 0;
        Arrays.stream(maps).map(m -> m.size()).reduce(result, Integer::sum);
        return result;
    }

    @Override
    public Collection<V> values() {
        for (ReentrantReadWriteLock lock : locks) {
            lock.readLock().lock();
        }
        Collection<V> result = Arrays.stream(maps).map(m -> m.values()).flatMap(s -> s.stream()).toList();
        for (ReentrantReadWriteLock lock : locks) {
            lock.readLock().unlock();
        }
        return result;
    }

}
