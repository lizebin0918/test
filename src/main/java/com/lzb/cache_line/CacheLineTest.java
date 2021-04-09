package com.lzb.cache_line;
/**
 * 缓存行测试，由于多线程争用导致性能下降<br/>
 * Created on : 2021-03-29 17:26
 * @author chenpi 
 */
public class CacheLineTest {

    public static void main(String[] args) throws Exception {
        TestOne.test();
        TestTwo.test();
    }

    private static class TestOne {
        private static class T {
            public volatile long x = 0L;
        }

        public static T[] arr = new T[2];

        static {
            arr[0] = new T();
            arr[1] = new T();
        }

        public static void test() throws Exception {
            Thread t1 = new Thread(()->{
                for (long i = 0; i < 1000_0000L; i++) {
                    arr[0].x = i;
                }
            });

            Thread t2 = new Thread(()->{
                for (long i = 0; i < 1000_0000L; i++) {
                    arr[1].x = i;
                }
            });

            final long start = System.nanoTime();
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println((System.nanoTime() - start)/100_0000);
        }
    }

    private static class TestTwo {
        private static class Padding {
            public volatile long p1, p2, p3, p4, p5, p6, p7;
        }

        private static class T extends Padding {
            public volatile long x = 0L;
        }

        public static T[] arr = new T[2];

        static {
            arr[0] = new T();
            arr[1] = new T();
        }

        public static void test() throws Exception {
            Thread t1 = new Thread(()->{
                for (long i = 0; i < 1000_0000L; i++) {
                    arr[0].x = i;
                }
            });

            Thread t2 = new Thread(()->{
                for (long i = 0; i < 1000_0000L; i++) {
                    arr[1].x = i;
                }
            });

            final long start = System.nanoTime();
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println((System.nanoTime() - start)/100_0000);
        }
    }


}
