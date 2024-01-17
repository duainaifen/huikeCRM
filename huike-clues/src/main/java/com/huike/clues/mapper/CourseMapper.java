package com.huike.clues.mapper;

import com.huike.clues.domain.TbCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 课程相关接口
 * @Author daqiang
 * @Date 2024-01-16 23:56
 */
@Mapper
public interface CourseMapper {

    /**
     * 新增课程管理接口
     *
     * @param tbCourse
     * @return
     */
    boolean addCourse(TbCourse tbCourse);

    /**
     * 修改课程管理接口
     *
     * @param tbCourse
     * @return
     */
    boolean updateCourse(TbCourse tbCourse);

    /**
     * 查询课程管理列表接口
     *
     * @param tbCourse
     * @return
     */
    List<TbCourse> courseList(TbCourse tbCourse);

    /**
     * 课程下拉列表接口
     *
     * @param subject
     * @return
     */
    @Select("select * from tb_course where subject =#{subject}")
    List<TbCourse> selectBySubject(String subject);

    /**
     * 删除课程管理接口
     *
     * @param idsList
     * @return
     */
    boolean courseDelete(List<Long> idsList);

    @Select("select * from tb_course where id=#{id} and is_delete=0")
    TbCourse courseGetById(Integer id);
}
