package com.xuecheng.order.service;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.order.dao.XcTaskHisRepository;
import com.xuecheng.order.dao.XcTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class TaskService {

    @Autowired
    XcTaskRepository xcTaskRepository;

    @Autowired
    XcTaskHisRepository xcTaskHisRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    /**
     * 一次查询n条任务，查询某个时间点之前的任务
     *
     * @param updateTime
     * @param n
     * @return
     */
    public List<XcTask> findTaskList(Date updateTime, int n) {
        LOGGER.info("一次查询n条任务，查询某个时间点之前的任务");
        //设置分页参数
        Pageable pageable = new PageRequest(0, n);//查询n条任务
        Page<XcTask> content = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
        List<XcTask> list = content.getContent();
        return list;
    }

    /**
     * 发送消息
     *
     * @param taskId     任务对象
     * @param ex         交换机id
     * @param routingKey
     */
    @Transactional
    public void publish(XcTask taskId, String ex, String routingKey) {
        LOGGER.info("发送消息");
        //查询任务
        Optional<XcTask> taskOptional = xcTaskRepository.findById(taskId.getId());
        if (taskOptional.isPresent()) {
            XcTask task = taskOptional.get();
            rabbitTemplate.convertAndSend(ex, routingKey, task);
            //更新任务时间为当前时间
            taskId.setUpdateTime(new Date());
            xcTaskRepository.save(task);
        }
    }

    /**
     * 使用乐观锁方法校验任务
     * 获取任务
     *
     * @param taskId
     * @param version
     * @return
     */
    @Transactional
    public int getTask(String taskId, Integer version) {
        return xcTaskRepository.updateTaskVersion(taskId, version);
    }

    /**
     * 完成任务
     *
     * @param taskId
     */
    @Transactional
    public void finishTask(String taskId) {
        LOGGER.info("完成任务");
        Optional<XcTask> optionalXcTask = xcTaskRepository.findById(taskId);
        if (optionalXcTask.isPresent()) {
            //当前任务
            XcTask xcTask = optionalXcTask.get();
            //历史任务
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask, xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
            xcTaskRepository.delete(xcTask);
        }
    }
}
