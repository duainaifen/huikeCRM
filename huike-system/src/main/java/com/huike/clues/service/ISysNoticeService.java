package com.huike.clues.service;

import com.huike.clues.domain.SysNotice;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 8:55
 */
public interface ISysNoticeService {

    /**
     * 获取系统提醒分页列表接口
     * @param sysNotice
     * @return
     */
    List<SysNotice> pagelist(SysNotice sysNotice);

    /**
     * 获取详细信息接口
     * @param noticeId
     * @return
     */
    SysNotice getById(Long noticeId);

    /**
     * 未读变已读接口
     * @param noticeId
     */
    void changeStatus(SysNotice sysNotice);
}
