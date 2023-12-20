package com.huike.clues.mapper;

import com.huike.clues.domain.TbActivity;
import com.huike.common.annotation.AutoFill;
import com.huike.common.enums.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-19 15:14
 */

/**
 * 活动管理相关接口持久层
 */
@Mapper
public interface ActivityMapper {

    /**
     * 新增活动管理接口
     * @param tbActivity
     * @return
     */
    @AutoFill(value = OperationType.INSERT)
    boolean addActivity(TbActivity tbActivity);

    /**
     * 查询活动管理列表(分页接口)
     * @param tbActivity
     * @return
     */
    List<TbActivity> list(TbActivity tbActivity);

    /**
     * 修改活动管理接口
     * @param tbActivity
     * @return
     */
    @AutoFill(OperationType.UPDATE)
    boolean updateActivity(TbActivity tbActivity);

    /**
     * 获取活动管理详细信息接口
     * @param id
     * @return
     */
    @Select("select * from tb_activity where id=#{id}")
    TbActivity getActivityById(Long id);

    /**
     * 删除活动管理接口
     * @param ids
     * @return
     */
    boolean deleteActivity(@Param("ids") List<Long> ids);
}
