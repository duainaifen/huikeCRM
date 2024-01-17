package com.huike.clues.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.mapper.CourseMapper;
import com.huike.clues.service.CourseService;
import com.huike.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-16 23:55
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;


    /**
     * 新增课程管理接口
     *
     * @param tbCourse
     * @return
     */
    @Override
    public boolean addCourse(TbCourse tbCourse) {
        tbCourse.setCreateTime(DateUtils.getNowDate());
        boolean result = courseMapper.addCourse(tbCourse);
        return result;
    }

    /**
     * 修改课程管理接口
     *
     * @param tbCourse
     * @return
     */
    @Override
    public boolean updateCourse(TbCourse tbCourse) {
        boolean result = courseMapper.updateCourse(tbCourse);
        return result;
    }

    /**
     * 查询课程管理列表接口
     *
     * @param tbCourse
     * @return
     */
    @Override
    public List<TbCourse> courseList(TbCourse tbCourse) {
        List<TbCourse> result = courseMapper.courseList(tbCourse);
        return result;
    }

    /**
     * 课程下拉列表接口
     *
     * @param subject
     * @return
     */
    @Override
    public List<TbCourse> selectBySubject(String subject) {
        List<TbCourse> result = courseMapper.selectBySubject(subject);
        return result;
    }

    /**
     * 删除课程管理接口
     *
     * @param ids
     * @return
     */
    @Override
    public boolean courseDelete(String ids) {
        //这个ids是以逗号分隔的字符串，要进行提取
        String[] split = ids.split(",");
        List<String> strings = CollUtil.toList(split);
        List<Long> idsList = new ArrayList<>();
        //要将string类型转化成Long类型,方便后面转化成List集合，后面传递到mapper层
        for (String id : strings) {
            long LongId = NumberUtil.parseLong(id);
            idsList.add(LongId);
        }
        boolean result = courseMapper.courseDelete(idsList);
        return result;
    }

    /**
     * 获取课程管理详细信息
     *
     * @param id
     * @return
     */
    @Override
    public TbCourse courseGetById(Integer id) {
        TbCourse tbCourse = courseMapper.courseGetById(id);
        return tbCourse;
    }
}
