package com.huike.clues.service;

import java.util.List;
import java.util.Set;

import com.huike.common.core.domain.entity.SysMenu;
import com.huike.common.core.domain.entity.SysRole;

/**
 * 角色业务层
 * 
 * 
 */
public interface ISysRoleService
{
    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll();

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 新增角色接口
     * @param sysRole
     */
    void addRole(SysRole sysRole);

    /**
     * 修改保存角色接口
     * @param sysRole
     * @return
     */
    boolean updateRole(SysRole sysRole);

    /**
     * 根据角色编号获取详细信息接口
     * @param roleId
     * @return
     */
    SysRole getByRoleId(Long roleId);

    /**
     * 获取角色选择框列表接口
     * @param userId
     * @return
     */
    List<SysMenu> optionselect(Long userId);

    /**
     * 删除角色接口
     * @param roleIds
     */
    void delete(List<Long> roleIds);
}
