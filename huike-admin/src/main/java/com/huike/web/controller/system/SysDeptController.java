package com.huike.web.controller.system;

import cn.hutool.core.util.BooleanUtil;
import com.github.pagehelper.PageInfo;
import com.huike.clues.service.ISysDeptService;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.core.page.TableDataInfo;
import com.huike.web.result.RoleDeptTreeAjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 17:45
 */

/**
 * 部门管理相关接口
 */
@RestController
@RequestMapping("/system/dept")
@Api(tags = "部门管理相关接口")
@Slf4j
public class SysDeptController extends BaseController {

    @Resource
    private ISysDeptService sysDeptService;

    /**
     * 获取部门列表(分页接口)
     *
     * @param sysDept
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取部门列表(分页接口)")
    public TableDataInfo list(SysDept sysDept) {
        startPage();
        List<SysDept> result = sysDeptService.list(sysDept);
        //手动封装返回数据，因为前端解析的是data数据
        TableDataInfo tableDataInfo = myGetTableDataInfo(result);
        return tableDataInfo;
        //return getDataTable(result);
    }

    /**
     * 抽取出来的手动封装返回前端的分页数据(交给前端生成树结构)
     *
     * @param result
     * @return
     */
    @NotNull
    private static TableDataInfo myGetTableDataInfo(List<SysDept> result) {
        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setMsg("查询成功");
        tableDataInfo.setData(result);
        tableDataInfo.setTotal(new PageInfo(result).getTotal());
        return tableDataInfo;
    }

    /**
     * 新增部门接口
     *
     * @param sysDept
     * @return
     */
    @ApiOperation("新增部门接口")
    @PostMapping
    public AjaxResult addDept(@RequestBody SysDept sysDept) {
        log.info("前端新增部门接收数据:{}", sysDept);
        boolean result = sysDeptService.addDept(sysDept);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 删除部门接口
     *
     * @param sysDept
     * @return
     */
    @DeleteMapping("/{deptId}")
    @ApiOperation("删除部门接口")
    public AjaxResult deleteDept(SysDept sysDept) {
        boolean result = sysDeptService.deleteDept(sysDept);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改部门接口
     *
     * @param sysDept
     * @return
     */
    @PutMapping
    @ApiOperation("修改部门接口")
    public AjaxResult updateDept(@RequestBody SysDept sysDept) {
        boolean result = sysDeptService.updateDept(sysDept);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 根据部门编号获取详细信息接口
     *
     * @param sysDept
     * @return
     */
    @ApiOperation("根据部门编号获取详细信息接口")
    @GetMapping("/{deptId}")
    public AjaxResult selectById(SysDept sysDept) {
        SysDept result = sysDeptService.selectById(sysDept);
        return AjaxResult.success(result);
    }

    /**
     * 查询部门列表(排除指定部门节点)
     *
     * @param sysDept
     * @return
     */
    @GetMapping("/list/exclude/{deptId}")
    @ApiOperation("查询部门列表(排除指定部门节点)")
    public TableDataInfo excludeList(SysDept sysDept) {
        Long deptId = sysDept.getDeptId();
        List<SysDept> result = sysDeptService.excludeList(deptId);
        //自己封装数据的抽取方法
        return myGetTableDataInfo(result);
    }

    /**
     * 获取对应角色部门列表树接口
     *
     * @return
     */
    @GetMapping("/roleDeptTreeselect/{roleId}")
    @ApiOperation("获取对应角色部门列表树接口")
    public RoleDeptTreeAjaxResult roleDeptTreeselect(@PathVariable Long roleId) {
        //List<SysDept> result = sysDeptService.roleDeptTreeselect(roleId);
        List<SysDept> result = sysDeptService.list(new SysDept());

        RoleDeptTreeAjaxResult success = RoleDeptTreeAjaxResult.success();

        List<Long> checkedKeys = result.stream().map(sysDept -> sysDept.getDeptId()).collect(Collectors.toList());
        //通过stream流进行转化数据类型
        List<Integer> checkedKeysInteger = checkedKeys.stream().map(id -> id.intValue()).collect(Collectors.toList());
        //封装返回数据
        success.setCheckedKeys(checkedKeysInteger);
        //封装树形结构
        List<SysDept> resultList = sysDeptService.getroleTreeDept(result);

        //将result转化成树形结构变成dept
        List<TreeSelect> treeSelectList = new ArrayList<>();
        for (SysDept sysDept : resultList) {
            TreeSelect treeSelect = new TreeSelect(sysDept);
            treeSelectList.add(treeSelect);
        }
        success.setDepts(treeSelectList);
        return success;
    }

    /**
     * 获取部门及人员接口
     *
     * @return
     */
    //TODO 不知道前端的接口，先做后期有问题再改
    @GetMapping("/treeAnduser")
    @ApiOperation("获取部门及人员接口")
    public AjaxResult treeAnduser() {
        List<SysDept> result = sysDeptService.list(new SysDept());

        //封装树形结构
        List<SysDept> resultList = sysDeptService.getroleTreeDept(result);

        //将result转化成树形结构变成dept
        List<TreeSelect> treeSelectList = new ArrayList<>();
        for (SysDept sysDept : resultList) {
            TreeSelect treeSelect = new TreeSelect(sysDept);
            treeSelectList.add(treeSelect);
        }
        return AjaxResult.success(treeSelectList);
    }

    /**
     * 获取部门下拉树列表接口
     *
     * @param sysDept
     * @return
     */
    @GetMapping("/treeselect")
    @ApiOperation("获取部门下拉树列表接口")
    public AjaxResult treeselct(SysDept sysDept) {
        List<SysDept> sysDeptList = sysDeptService.list(sysDept);
        //构造树状结构
        List<SysDept> treeList = sysDeptService.getroleTreeDept(sysDeptList);
        List<TreeSelect> treeSelectList = new ArrayList<>();
        for (SysDept dept : treeList) {
            TreeSelect treeSelect = new TreeSelect(dept);
            treeSelectList.add(treeSelect);
        }
        return AjaxResult.success(treeSelectList);
    }
}
