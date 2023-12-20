package com.huike.clues.service;

import com.huike.common.core.domain.entity.SysDept;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 17:58
 */
public interface ISysDeptService {

    /**
     * 获取部门列表(分页接口)
     * @param sysDept
     * @return
     */
    List<SysDept> list(SysDept sysDept);

    /**
     * 新增部门接口
     * @param sysDept
     * @return
     */
    boolean addDept(SysDept sysDept);

    /**
     * 删除部门接口
     * @param sysDept
     * @return
     */
    boolean deleteDept(SysDept sysDept);

    /**
     * 修改部门接口
     * @param sysDept
     * @return
     */
    boolean updateDept(SysDept sysDept);

    /**
     * 根据部门编号获取详细信息接口
     * @param sysDept
     * @return
     */
    SysDept selectById(SysDept sysDept);

    /**
     * 查询部门列表(排除指定部门节点)
     * @param deptId
     * @return
     */
    List<SysDept> excludeList(Long deptId);

    /**
     * 获取对应角色部门列表树接口
     * @param roleId
     * @return
     */
    List<SysDept> roleDeptTreeselect(Long roleId);

    /**
     * 构造树形结构接口
     * @param sysDeptList
     * @return
     */
    List<SysDept> getroleTreeDept(List<SysDept> sysDeptList);
}
