package com.wb.note.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by wb on 2020/3/24
 * -------------------------创建方式-----------------------------
 *
 * 创建CompletableFuture对象的方式
 * runAsync(Runnable runnable) run方法没有返回值
 * supplyAsync(Supplier<U> supplier) get方法有返回值
 *
 * runAsync(Runnable runnable, Executor executor)
 * SupplyAsync(Supplier<U> supplier, Executor executor)
 * 区别是下面的两个方法可以指定线程池参数
 *
 * 默认情况下CompletableFuture会使用默认的ForkJoinPool线程池
 * 这个线程池默认的线程数是cpu核心数
 * 也可以通过JVM option:-Djava.util.concurrent.ForkJoinPool.common.parallelism来设置ForkJoinPool线程池的线程数
 *
 * 强烈建议用不同的业务类型创建不同的线程池，以免互相干扰
 *
 * 创建完CompletableFuture对象后，会自动地异步执行runnable.run()方法或者执行supplier.get()方法
 *
 * 串行关系、并行关系、汇聚关系
 *
 * -------------------------CompletionStage----------------------
 * 描述时序关系
 * 串行关系
 * And聚合关系： 都完成才能进行当前的任务
 * Or 聚合关系： 只要有一个完成就能进行当前任务
 * 异常处理
 *
 * 串行关系：thenApply、 thenAccept、 theRun、 theCompose
 *      thenApply系列函数参数是Function<T,R>
 *               这个接口与CompletionStage相关的方法是R apply(T t) 所以thenApply系列方法返回的是CompletionStage<R>
 *
 *      thenAccept系列方法参数是Consumer<T>
 *               void accept(T t) 返回的是CompletionStage(void)
 *
 *      thenRun  Runnable  CompletionStage(void)
 *
 *      这些方法里面Async代表的是异步执行fn、consumer或者action。
 *      thenCompose
 *               这个系列的方法会创建出一个子流程，最后结果和thenApply一致
 *
 * And聚合关系： thenCombine、thenAcceptBoth、runAfterBoth参数不同而已
 *
 * Or聚合关系：  applyToEither、acceptEither、runAfterEither
 *
 * 异常处理 exceptionally(类似于catch)、whenComplete(类似于finally)、whenCompleteAsync、handle(类似于finally)、handleAsync
 *
 */
public class NoteOfCompletableFuture {
    public void demo() {
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("T1:洗水壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T1:烧开水...");
                    sleep(15, TimeUnit.SECONDS);
                });
        //任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("T2:洗茶壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T2:洗茶杯...");
                    sleep(2, TimeUnit.SECONDS);

                    System.out.println("T2:拿茶叶...");
                    sleep(1, TimeUnit.SECONDS);
                    return "龙井";
                });
        //任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 =
                f1.thenCombine(f2, (__, tf) -> {
                    System.out.println("T1:拿到茶叶:" + tf);
                    System.out.println("T1:泡茶...");
                    return "上茶:" + tf;
                });
        //等待任务3执行结果
        System.out.println(f3.join());


    }

    public void demo1() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(
                () -> "hello world"
        ).thenApply(
                s->  s + " i don't like YOU"
        ).thenApply(
                String::toLowerCase
        );

        System.out.println(f1.join());
    }

    private void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        NoteOfCompletableFuture note = new NoteOfCompletableFuture();
        //note.demo();
        note.demo1();
    }
}
