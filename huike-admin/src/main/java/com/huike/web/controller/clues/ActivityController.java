package com.huike.web.controller.clues;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.service.ActivityService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.text.TabableView;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-19 11:43
 */

/**
 * 活动管理相关接口
 */
@RestController
@RequestMapping("/clues/activity")
@Api(tags = "活动管理相关接口")
public class ActivityController extends BaseController {

    @Resource
    private ActivityService activityService;

    /**
     * 新增活动管理接口
     *
     * @param tbActivity
     */
    @ApiOperation("新增活动管理接口")
    @PostMapping
    public AjaxResult addActivity(@RequestBody TbActivity tbActivity) {
        boolean result = activityService.addActivity(tbActivity);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 查询活动管理列表(分页接口)
     *
     * @param tbActivity
     * @return
     */
    @ApiOperation("查询活动管理列表")
    @GetMapping("/list")
    public TableDataInfo list(TbActivity tbActivity) {
        startPage();
        List<TbActivity> result = activityService.list(tbActivity);
        return getDataTable(result);
    }

    /**
     * 修改活动管理接口
     *
     * @param tbActivity
     * @return
     */
    @ApiOperation("修改活动管理接口")
    @PutMapping
    public AjaxResult updateActivity(@RequestBody TbActivity tbActivity) {
        boolean result = activityService.updateActivity(tbActivity);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 获取活动管理详细信息接口
     *
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("获取活动管理详细信息接口")
    public AjaxResult getActivityById(TbActivity tbActivity) {
        Long id = tbActivity.getId();
        TbActivity result = activityService.getActivityById(id);
        if (ObjectUtil.isEmpty(result)) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success(result);
        }
    }

    /**
     * 获取状态为2的渠道活动列表
     *
     * @param tbActivity
     * @return
     */
    @GetMapping("/listselect/{channel}")
    @ApiOperation("获取状态为2的渠道活动列表")
    public AjaxResult getChannelOfTwo(TbActivity tbActivity) {
        List<TbActivity> result = activityService.getChannelOfTwo(tbActivity);
        if (ObjectUtil.isEmpty(result)) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success(result);
        }
    }

    /**
     * 删除活动管理接口
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除活动管理接口")
    public AjaxResult deleteActivity(@PathVariable List<Long> ids) {
        boolean result = activityService.deleteActivity(ids);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }
}
