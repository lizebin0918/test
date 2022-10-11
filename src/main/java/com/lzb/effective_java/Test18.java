package com.lzb.effective_java;

import java.util.*;

/**
 * 复合优于继承<br/>
 * Created on : 2022-10-10 22:47
 *
 * @author mac
 */
public class Test18 {

    public static void main(String[] args) {
        CounterSet<Integer> ints = new CounterSet<>();
        List<Integer> list = List.of(1, 2, 3, 4, 4, 5, 4, 6, 7);
        ints.addAll(list);
        // 错误示范：打印的是8，而不是4
        System.out.println(ints.getAddCount());

        MyCounterSet<Integer> ints1 = new MyCounterSet<>(new HashSet<>());
        ints1.addAll(list);
        System.out.println(ints1.getAddCount());
    }

    /**
     * 扩展一个Set，统计它从创建依赖添加了多少个元素（不能用size()，因为有可能删除而递减）
     */
    private static class CounterSet<E> extends HashSet<E> {

        private int addCount;

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

    private static class MyCounterSet<E> implements Set<E> {

        private final Set<E> set;
        private int addCount;

        MyCounterSet(Set<E> set) {
            this.set = set;
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean isEmpty() {
            return set.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return set.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return set.iterator();
        }

        @Override
        public Object[] toArray() {
            return set.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return set.toArray(a);
        }

        @Override
        public boolean add(E e) {
            addCount++;
            return set.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return set.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return set.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return set.addAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return set.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return set.removeAll(c);
        }

        @Override
        public void clear() {
            set.clear();
        }

        public int getAddCount() {
            return addCount;
        }
    }

}


