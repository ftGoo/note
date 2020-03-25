package com.wb.note.threads;

/**
 * Created by wb on 2020/3/25
 * 对于简单的任务，可以通过线程池+Future的方案来解决
 如果任务之间有聚合关系，无论是And聚合还是OR聚合，都可以通过CompletableFuture来解决
 批量的并行任务，通过CompletionService来解决

 并发编程分成三个层面：
 分工、协作、互斥

 类比现实世界的分工，把线程池、Future、CompletableFuture和CompletionService都列到了分工里面。

 分治
 大数据领域知名计算机框架MapReduce背后的思想是分治。
 java并发包里提供了一种叫做Fork/Join的并行计算框架，就是用来支持分治这种任务模型的。

 分治疗任务模型
 两阶段：
 一个阶段是任务分解：
 将任务迭代地分解为子任务，直到子任务可以直接计算结果。
 另一个结果是结构合并：
 逐层合并子任务地执行结果，直到获取最终结果。

 Fork/Join的使用
 Fork/Join是一个并行计算的框架，主要就是用来支持分治任务模型的
 Fork对应的是分治任务模型里的任务分解，Join对应的是结果合并。

 ForkJoin计算框架包含两部分
 分治任务的线程池ForkJoinPOOL
 分治任务 ForkJoinTask
 类似于ThreadPoolExecutor和Runnable的关系

 ForkJoinTask核心方法：
 fork() 异步的执行一个子任务
 join() 阻塞当前线程等待子线程的结果

 ForkJoinTask的两个子类RecursiveAction和RecursiveTask
 递归，一个有返回值，一个没有返回值

 ForkJoinPool
 本质也是一个生产者-消费者的实现，内部有多个队列
 路由规则
 任务窃取

 模拟MapReduce统计单词数量

 */
public class NoteOfForkJoin {
}
