package com.huike.clues.mapper;

import com.huike.clues.domain.SysNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 9:02
 */

@Mapper
public interface SysNoticeMapper {

    /**
     * 获取系统提醒分页列表接口
     *
     * @param sysNotice
     * @return
     */
    List<SysNotice> pagelist(SysNotice sysNotice);

    /**
     * 获取详细信息接口
     *
     * @param noticeId
     * @return
     */
    @Select("select * from sys_notice where notice_id=#{noticeId}")
    SysNotice getById(Long noticeId);

    /**
     * 未读变已读接口
     * @param sysNotice
     */
    void changeStatus(SysNotice sysNotice);
}
