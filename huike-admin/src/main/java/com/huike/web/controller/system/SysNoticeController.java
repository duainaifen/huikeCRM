package com.huike.web.controller.system;

import com.huike.clues.domain.SysNotice;
import com.huike.clues.service.ISysNoticeService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.BaseEntity;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 8:30
 */

/**
 * 通知公告相关接口
 */
@RestController
@RequestMapping("system/notice")
@Slf4j
@Api(tags = "通知公告相关接口")
public class SysNoticeController extends BaseController {

    @Resource
    private ISysNoticeService sysNoticeService;

    /**
     * 获取系统提醒分页列表接口
     *
     * @param sysNotice
     * @return
     */
    @GetMapping("/pagelist/{status}")
    @ApiOperation("获取系统提醒分页列表接口")
    public TableDataInfo pagelist(SysNotice sysNotice) {
        startPage();
        List<SysNotice> result = sysNoticeService.pagelist(sysNotice);
        return getDataTable(result);
    }

    /**
     * 获取详细信息接口
     *
     * @param sysNotice
     */
    @ApiOperation("获取详细信息接口")
    @GetMapping("/{noticeId}")
    public AjaxResult getById(SysNotice sysNotice) {
        Long noticeId = sysNotice.getNoticeId();
        log.info("接收到的id为{}",noticeId);
        SysNotice result = sysNoticeService.getById(noticeId);
        return AjaxResult.success(result);
    }

    /**
     * 未读变已读接口
     * @param sysNotice
     */
    @PutMapping("/{noticeId}")
    public void changeStatus(SysNotice sysNotice){
        sysNoticeService.changeStatus(sysNotice);
    }
}
