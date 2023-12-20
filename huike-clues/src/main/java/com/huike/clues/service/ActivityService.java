package com.huike.clues.service;

import com.huike.clues.domain.TbActivity;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-19 15:09
 */
public interface ActivityService {

    /**
     * 新增活动管理接口
     * @param tbActivity
     * @return
     */
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
    boolean updateActivity(TbActivity tbActivity);

    /**
     * 获取活动管理详细信息接口
     * @param id
     * @return
     */
    TbActivity getActivityById(Long id);

    /**
     * 获取状态为2的渠道活动列表接口
     * @param tbActivity
     * @return
     */
    List<TbActivity> getChannelOfTwo(TbActivity tbActivity);

    /**
     * 删除活动管理接口
     * @param ids
     * @return
     */
    boolean deleteActivity(List<Long> ids);
}
