package com.changan.eurekademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发请求
 *
 * @author zab
 * @date 2019-07-27 07:34
 */
@RestController
@RequestMapping("/api")
public class ConcurrentTest {
    static final CountDownLatch latch = new CountDownLatch(2000);
    static final CountDownLatch latch1 = new CountDownLatch(2000);
    private static final String url = "http://localhost:14000/microservice-provider-user/api/user";
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static AtomicInteger errorNum = new AtomicInteger();
    private static Integer index = 1;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/exe")
    public String exe(int num) throws Exception {
        index = 1;
        atomicInteger.set(0);
        errorNum.set(0);
        long startTime = System.currentTimeMillis();
        ThreadFactory namedThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("mythread");
                return thread;
            }
        };

        ExecutorService threadPool = new ThreadPoolExecutor(1000, 1200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < num; i++) {
            this.f1(threadPool);
            latch1.countDown();
        }

        latch.await();


        long endTime = System.currentTimeMillis();
        Thread.sleep(1000);
        String result = "执行了" + atomicInteger.get() + "次,失败：" + errorNum.get() + "次，执行时间是：" + (endTime - startTime);
        threadPool.shutdown();
        return result;
    }

    public void f1(ExecutorService executor) {
        executor.execute(new Decrement());
    }

    class Decrement implements Runnable {

        @Override
        public void run() {
            try {
                latch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String realUrl = url;
            atomicInteger.addAndGet(index);
            //方法一：
            //kafkaTemplate.send("xxx","test");
            //方法二：
            try {
                String s = restTemplate.getForObject(realUrl, String.class);
                System.out.println(s);
            } catch (Exception e) {
                errorNum.addAndGet(index);
                System.out.println(Thread.currentThread().getName() + "出错了==============出错数量：" + errorNum.get());
                e.printStackTrace();
            }
            latch.countDown();
        }
    }


}
