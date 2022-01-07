package cn.xavier.hrm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling   //开启定时任务
@EnableAsync    //开启异步
public class ScheduleConfig implements AsyncConfigurer {


    @Override
    public Executor getAsyncExecutor() {
        //指定线程数
        return Executors.newFixedThreadPool(2);
    }
    /**
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        //这里可以定义自己的异常处理
        return new CustomAsyncUncaughtExceptionHandler();
    } **/
}