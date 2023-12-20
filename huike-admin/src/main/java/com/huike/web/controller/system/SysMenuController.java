package com.huike.web.controller.system;

import cn.hutool.core.util.BooleanUtil;
import com.github.pagehelper.PageInfo;
import com.huike.clues.service.ISysMenuService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysMenu;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.core.page.TableDataInfo;
import com.huike.web.result.RoleMenuTreeAjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-11-23 10:40
 */
@Api(tags = "菜单相关接口")
@RestController
@Slf4j
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    @Resource
    private ISysMenuService sysMenuService;

    /**
     * 新增菜单接口
     */
    @PostMapping
    @ApiOperation("新增菜单接口")
    public AjaxResult addMenu(@RequestBody SysMenu sysMenu) {
        boolean result = sysMenuService.addMenu(sysMenu);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 修改菜单接口
     *
     * @param sysMenu
     */
    @PutMapping
    @ApiOperation("修改菜单")
    public AjaxResult updateMenu(@RequestBody SysMenu sysMenu) {
        boolean result = sysMenuService.updateMenu(sysMenu);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @ApiOperation("菜单分页接口")
    @GetMapping("/list")
    public TableDataInfo menuList(SysMenu sysMenu) {
        startPage();
        List<SysMenu> list = sysMenuService.selectMenuList(sysMenu);
        //手动封装数据，因为前端解析的数据是data
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setData(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 加载对应角色菜单列表树接口
     *
     * @param sysRole
     */
    //TODO 没找到对应的网络接口
    @GetMapping("/roleMenuTreeselect/{roleId}")
    @ApiOperation("加载对应角色菜单列表树接口")
    public AjaxResult<List> roleMenuTreeselect(SysRole sysRole) {
        logger.info("控制层接收的数据:{}",sysRole);
        List result=sysMenuService.selectMenuListByRoleId(sysRole);
        log.info("返回值为:{}",result);
        return AjaxResult.success(result);
    }

    /**
     * 获取菜单下拉树列表接口
     *
     * @param sysMenu
     */
    //TODO 未实现
    @GetMapping("/treeselect")
    @ApiOperation("获取菜单下拉树列表接口")
    public void treeSelect(SysMenu sysMenu) {

    }

    /**
     * 根据菜单编号获取详细信息接口
     *
     * @param sysMenu
     * @return
     */
    @ApiOperation("根据菜单编号获取详细信息接口")
    @GetMapping("/{menuId}")
    public AjaxResult selectMenuById(SysMenu sysMenu) {
        SysMenu result = sysMenuService.selectMenuById(sysMenu);
        return AjaxResult.success(result);
    }
}
