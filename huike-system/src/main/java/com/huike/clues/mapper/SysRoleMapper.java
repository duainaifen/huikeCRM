package com.huike.clues.mapper;

import java.util.List;
import com.huike.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 角色表 数据层
 * 
 * 
 */
public interface SysRoleMapper
{
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
     * @return 角色列表
     */
    public List<SysRole> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userName 用户名
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserName(String userName);

    /**
     * 新增角色接口
     * @param sysRole
     */
    void addRole(SysRole sysRole);

    /**
     * 更新保存角色接口
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
     * 根据主键id进行查询
     * @param roleId
     * @return
     */
    @Select("select * from sys_role where role_id=#{roleId}")
    SysRole selectById(Long roleId);

    /**
     * 根据主键id进行删除
     * @param roleIds
     */
    void deleteIds(@Param("ids") List<Long> roleIds);
}
