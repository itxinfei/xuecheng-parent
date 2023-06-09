package com.xuecheng.order.service;


import com.xuecheng.framework.domain.task.XcTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 任务类
 */
@Component
public class ChooseCourseTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    TaskService taskService;

    /**
     * @param xcTask
     */
    @RabbitListener(queues = "xc_learning_finishaddchoosecourse")
    public void receiveFinishChoosecourseTask(XcTask xcTask) {
        if (xcTask != null && StringUtils.isNotEmpty(xcTask.getId())) {
            taskService.finishTask(xcTask.getId());
        }
    }

    /**
     * 每隔1分钟扫描xc_task表
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void sendChooseCourse() {
        //得到1分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<XcTask> xcTaskList = taskService.findTaskList(time, 100);
        System.out.println(xcTaskList);
        //调用service发布消息，将添加选课的任务发送给mq
        for (XcTask xcTask : xcTaskList) {
            //取任务
            if (taskService.getTask(xcTask.getId(), xcTask.getVersion()) > 0) {
                String ex = xcTask.getMqExchange();//要发送的交换机
                String routingKey = xcTask.getMqRoutingkey();//发送消息要带routingKey
                taskService.publish(xcTask, ex, routingKey);
            }
        }
    }

    /**
     * 定时执行任务
     *
     * @Scheduled(fixedRate=3000)//每隔3秒执行
     * @Scheduled(fixedDelay=3000)//每隔3秒执行，等到上次任务完成后等3秒去执行
     */
    @Scheduled(cron = "0/3 * * * * *")//每隔3秒执行
    public void task1() {
        LOGGER.info("定时执行");
        try {
            //模拟执行时长为3秒
            System.out.println("==模拟执行时长为3秒==");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Scheduled(fixedDelay=3000)//每隔3秒执行，等到上次任务完成后等3秒去执行
     */
    //@Scheduled(cron = "0/3 * * * * *")//每隔3秒执行
    public void task2() {
        LOGGER.info("=============执行任务2=============");
        try {
            //模拟执行时长为3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
