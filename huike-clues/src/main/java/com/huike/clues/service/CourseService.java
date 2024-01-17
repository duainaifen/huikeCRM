package com.huike.clues.service;

import com.huike.clues.domain.TbCourse;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-16 23:55
 */
public interface CourseService {
    /**
     * 新增课程管理接口
     * @param tbCourse
     * @return
     */
    boolean addCourse(TbCourse tbCourse);

    /**
     * 修改课程管理接口
     * @param tbCourse
     * @return
     */
    boolean updateCourse(TbCourse tbCourse);

    /**
     * 查询课程管理列表接口
     * @param tbCourse
     * @return
     */
    List<TbCourse> courseList(TbCourse tbCourse);

    /**
     * 课程下拉列表接口
     * @param subject
     * @return
     */
    List<TbCourse> selectBySubject(String subject);

    /**
     * 删除课程管理接口
     * @param ids
     * @return
     */
    boolean courseDelete(String ids);

    /**
     * 获取课程管理详细信息
     * @param id
     * @return
     */
    TbCourse courseGetById(Integer id);
}
