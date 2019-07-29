package com.changan.eurekademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://127.0.0.1:80/api/del_stock";
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static Integer index = 1;
    static final CountDownLatch latch = new CountDownLatch(100);
    static final CountDownLatch latch1 = new CountDownLatch(100);

    @GetMapping("/exe")
    public void exe() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 100; i++) {
            this.f1(executor);
            latch1.countDown();
        }

        latch.await();

        System.out.println("执行完毕！");
        System.out.println("执行了" + atomicInteger.get() + "次");

    }

    public void f1(ThreadPoolExecutor executor) {
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
            String s = restTemplate.getForObject(realUrl, String.class);
            latch.countDown();
        }
    }


}
