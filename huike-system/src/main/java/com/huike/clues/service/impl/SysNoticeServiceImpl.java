package com.huike.clues.service.impl;

import com.huike.clues.domain.SysNotice;
import com.huike.clues.mapper.SysNoticeMapper;
import com.huike.clues.service.ISysNoticeService;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 8:55
 */
@Service
@Slf4j
public class SysNoticeServiceImpl implements ISysNoticeService {

    @Resource
    private SysNoticeMapper noticeMapper;

    /**
     * 获取系统提醒分页列表接口
     *
     * @param sysNotice
     * @return
     */
    @Override
    public List<SysNotice> pagelist(SysNotice sysNotice) {
        List<SysNotice> result = noticeMapper.pagelist(sysNotice);
        return result;
    }

    /**
     * 获取详细信息接口
     *
     * @param noticeId
     * @return
     */
    @Override
    public SysNotice getById(Long noticeId) {
        SysNotice result = noticeMapper.getById(noticeId);
        return result;
    }

    /**
     * 未读变已读接口
     *
     * @param sysNotice
     */
    @Override
    public void changeStatus(SysNotice sysNotice) {
        //修改操作，修改字段要更新
        sysNotice.setUpdateBy(SecurityUtils.getUsername());
        sysNotice.setUpdateTime(DateUtils.getNowDate());
        noticeMapper.changeStatus(sysNotice);
    }
}
