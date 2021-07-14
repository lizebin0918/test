package com.lzb.concurrent.completablefuture;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 单条插入转批量入库，基于CompletableFuture<br/>
 * Created on : 2021-07-14 16:06
 *
 * @author chenpi
 */
public class SingleToMultipleApi {

    private static final List<PersonDto> DB = Collections.synchronizedList(new ArrayList<>());

    /**
     * 单条转批量
     *
     * @param personDto
     * @return
     */
    public boolean doInsert(PersonDto personDto) {
        return DB.add(personDto);
    }

    /**
     * 批量插入DB
     *
     * @param personDtos
     * @return
     */
    public boolean doBatchInsert(List<PersonDto> personDtos) {
        return DB.addAll(personDtos);
    }

    @Data
    private static class PersonDto {
        private String name;
        private Integer age;
    }

    @Data
    private static class Task {
        private PersonDto entity;
        private CompletableFuture<Boolean> asyn = new CompletableFuture<>();
    }

    /**
     * 生产者添加任务到队列
     */
    private static final ConcurrentLinkedQueue<Task> TASKS = new ConcurrentLinkedQueue<>();

    /**
     * provider
     * 对外暴露的API接口，多线程入库
     * @param dto
     */
    public void insert(PersonDto dto) {
        Task task = new Task();
        task.setEntity(dto);
        TASKS.add(task);
        try {
            Boolean result = task.getAsyn().get();
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                   + "入库后:" + "name:" + task.getEntity().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final int consumerSize = 2;

    /**
     * 消费者
     */
    public void consumer() {
        for (int i = 0; i < consumerSize; i++) {
            threadPool.execute(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(50));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        continue;
                    }
                    System.out.println(Thread.currentThread().getName() + "-任务总量:" + TASKS.size());
                    List<Task> batch = new ArrayList<>();
                    for (int j = 0; j < ThreadLocalRandom.current().nextInt(10); j++) {
                        Task task = TASKS.poll();
                        if (Objects.isNull(task)) {
                            break;
                        }
                        batch.add(task);
                    }
                    System.out.println(Thread.currentThread().getName() + "-取出任务量:" + batch.size());
                    if (batch.size() > 0) {
                        boolean success = doBatchInsert(batch.stream().map(Task::getEntity).collect(Collectors.toList()));
                        batch.forEach(task -> {
                            task.getAsyn().complete(success);
                        });
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SingleToMultipleApi api = new SingleToMultipleApi();

        //启动消费者
        api.consumer();

        int concurrent = 500;
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(concurrent);
        for (int i = 0; i < concurrent; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    PersonDto person = new PersonDto();
                    person.setAge(j);
                    person.setName(Thread.currentThread().getName() + ":" + j);
                    api.insert(person);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        System.out.println("done");
        System.out.println("db.size() = " + DB.size());
        System.out.println("queue.size() = " + TASKS.size());

        threadPool.shutdownNow();
        api.threadPool.shutdownNow();

    }

}
