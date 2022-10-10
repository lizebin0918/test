package com.lzb.effective_java;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 复合优于继承<br/>
 * Created on : 2022-10-10 22:47
 *
 * @author mac
 */
public class Test18 {

    public static void main(String[] args) {
        CounterSet<Integer> ints = new CounterSet<>();
        ints.addAll(List.of(1, 2, 3, 4));
        // 错误示范：打印的是8，而不是4
        System.out.println(ints.getAddCount());
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

}


