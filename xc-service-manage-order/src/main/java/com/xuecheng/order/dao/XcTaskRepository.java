package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface XcTaskRepository extends JpaRepository<XcTask, String> {

    /**
     * 通过乐观锁校验任务是否可以获取到
     *
     * @param taskId
     * @param version
     * @return
     */
    @Modifying
    @Query("update XcTask t set t.version = :version+1 where t.id = :taskId and t.version =:version")
    int updateTaskVersion(@Param("taskId") String taskId, @Param("version") Integer version);

    /**
     * 查询任务列表，一次查询n条任务，查询某个时间点之前的任务
     *
     * @param pageable
     * @param updateTime
     * @return
     */
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    /**
     * 更新任务处理时间
     *
     * @param id
     * @param updateTime
     * @return
     */
    @Modifying
    @Query("update XcTask t set t.updateTime = :updateTime where t.id = :id")
    int updateTaskTime(@Param(value = "id") String id, @Param(value = "updateTime") Date updateTime);

}
