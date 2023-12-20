package com.huike.web.controller.system;

import cn.hutool.core.util.BooleanUtil;
import com.huike.clues.service.ISysRoleService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysMenu;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-11-30 11:29
 */

/**
 * 角色信息控制层相关接口
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色信息相关接口")
@Slf4j
public class SysRoleController extends BaseController {

    @Resource
    private ISysRoleService sysRoleService;

    /**
     * 新增角色接口
     *
     * @param sysRole
     */
    @PostMapping
    @ApiOperation("新增角色接口")
    public void addRole(@RequestBody SysRole sysRole) {
        sysRoleService.addRole(sysRole);
    }

    @PutMapping
    @ApiOperation("修改保存接口")
    public AjaxResult updateRole(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateRole(sysRole);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 状态修改接口
     *
     * @param sysRole
     */
    @ApiOperation("状态修改接口")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateRole(sysRole);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改保存数据权限
     *
     * @param sysRole
     * @return
     */
    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateRole(sysRole);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 角色分页查询
     *
     * @param sysRole
     * @return
     */
    @GetMapping("list")
    public TableDataInfo list(SysRole sysRole) {
        startPage();
        List<SysRole> sysRoles = sysRoleService.selectRoleList(sysRole);
        return getDataTable(sysRoles);
    }

    /**
     * 角色数据导出接口
     *
     * @param sysRole
     * @return
     */
    @GetMapping("/export")
    public AjaxResult export(SysRole sysRole) {
        List<SysRole> sysRoles = sysRoleService.selectRoleList(sysRole);
        ExcelUtil<SysRole> util = new ExcelUtil<>(SysRole.class);
        return util.exportExcel(sysRoles, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息接口
     *
     * @param sysRole
     * @return
     */
    @ApiOperation("根据角色编号获取详细信息接口")
    @GetMapping("/{roleId}")
    public AjaxResult getByRoleId(SysRole sysRole) {
        Long roleId = sysRole.getRoleId();
        SysRole result = sysRoleService.getByRoleId(roleId);
        log.info("当前用户id{}", SecurityUtils.getUserId());
        log.info("当前用户名字{}", SecurityUtils.getUsername());
        return AjaxResult.success(result);
    }

    /**
     * 获取角色选择框列表接口
     *
     * @return
     */
    @ApiOperation("获取角色选择框列表接口")
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> result = sysRoleService.optionselect(userId);
        return AjaxResult.success(result);
    }

    /**
     * 删除角色接口
     */
    @ApiOperation("删除角色接口")
    @DeleteMapping("/{roleIds}")
    public void deleteRole(@PathVariable List<Long> roleIds) {
        sysRoleService.delete(roleIds);
    }
}
