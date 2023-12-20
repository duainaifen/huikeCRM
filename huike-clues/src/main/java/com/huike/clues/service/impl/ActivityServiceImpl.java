package com.huike.clues.service.impl;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.mapper.ActivityMapper;
import com.huike.clues.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.TabableView;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-19 15:10
 */

/**
 * 活动相关接口实现层
 */
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    /**
     * 新增活动管理接口
     *
     * @param tbActivity
     * @return
     */
    @Override
    public boolean addActivity(TbActivity tbActivity) {
        boolean result = activityMapper.addActivity(tbActivity);
        return result;
    }

    /**
     * 查询活动管理列表(分页接口)
     *
     * @param tbActivity
     * @return
     */
    @Override
    public List<TbActivity> list(TbActivity tbActivity) {
        List<TbActivity> result = activityMapper.list(tbActivity);
        return result;
    }

    /**
     * 修改活动管理接口
     *
     * @param tbActivity
     * @return
     */
    @Override
    public boolean updateActivity(TbActivity tbActivity) {
        boolean result = activityMapper.updateActivity(tbActivity);
        return result;
    }

    /**
     * 获取活动管理详细信息接口
     *
     * @param id
     * @return
     */
    @Override
    public TbActivity getActivityById(Long id) {
        TbActivity result = activityMapper.getActivityById(id);
        return result;
    }

    /**
     * 获取状态为2的渠道活动列表接口
     *
     * @param tbActivity
     * @return
     */
    @Override
    public List<TbActivity> getChannelOfTwo(TbActivity tbActivity) {
        List<TbActivity> result = activityMapper.list(tbActivity);
        return result;
    }

    /**
     * 删除活动管理接口
     *
     * @param ids
     * @return
     */
    @Override
    public boolean deleteActivity(List<Long> ids) {
        boolean result = activityMapper.deleteActivity(ids);
        return result;
    }
}
