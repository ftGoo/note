package com.wb.note.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by wb on 2020/3/25
 * CompletionService的实现原理也是内部维护了一个阻塞队列
 * 当任务执行结束就把任务的执行结果加入到阻塞队列中。
 * CompletionService是把任务执行结果的Future对象加入到阻塞队列中
 * CompletionService接口的实现类是ExecutorCompletionService
 * ExecutorCompletionService(Executor executor);
 * ExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> completionQueue)
 * 两个构造方法都需要传入一个线程池，如果不指定completionQueue，那么会默认使用无边界的LinkedBlockingQueue
 * 任务执行结果的Future对象就是加入到completionQueue中
 * -------------------------------CompletionService的接口说明----------------------------------
 * submit()提交任务
 * take()、poll()都是从阻塞队列里获取并移除一个元素
 * 它们的区别在于如果阻塞队列是空的，那么调用 take() 方法的线程会被阻塞，而 poll() 方法会返回 null 值
 * poll(long timeout, TimeUnit unit) 方法支持以超时的方式获取并移除阻塞队列头部的一个元素，
 * 如果等待了 timeout unit时间，阻塞队列还是空的，那么该方法会返回 null 值。
 */
public class NoteOfCompletionService {
    //用于看源码
    CompletionService<String> completionService;

    public Integer demo() {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs =
                new ExecutorCompletionService<>(executor);
        // 用于保存Future对象
        List<Future<Integer>> futures =
                new ArrayList<>(3);
        //提交异步任务，并保存future到futures
        futures.add(
                cs.submit(()->geocoderByS1()));
        futures.add(
                cs.submit(()->geocoderByS2()));
        futures.add(
                cs.submit(()->geocoderByS3()));
        // 获取最快返回的任务执行结果
        Integer r = 0;
        try {
            // 只要有一个成功返回，则break
            for (int i = 0; i < 3; ++i) {
                r = cs.take().get();
                //简单地通过判空来检查是否成功返回
                if (r != null) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            //取消所有任务
            for(Future<Integer> f : futures)
                f.cancel(true);
        }
    // 返回结果
        return r;
    }

    private Integer geocoderByS3() {
        return null;
    }

    private Integer geocoderByS2() {
        return null;
    }

    private Integer geocoderByS1() {
        return null;
    }
}
