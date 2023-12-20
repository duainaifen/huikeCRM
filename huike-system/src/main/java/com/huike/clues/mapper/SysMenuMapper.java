package com.huike.clues.mapper;

import java.util.List;

import com.huike.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import com.huike.common.core.domain.entity.SysMenu;

/**
 * 菜单表 数据层
 *
 * 
 */
public interface SysMenuMapper
{
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 新增菜单接口
     * @param sysMenu
     * @return
     */
    boolean addMenu(SysMenu sysMenu);

    /**
     * 修改菜单接口
     * @param sysMenu
     * @return
     */
    boolean updateMenu(SysMenu sysMenu);

    /**
     * 菜单分页查询接口
     * @param sysMenu
     * @return
     */
    List<SysMenu> selectMenuList(SysMenu sysMenu);

    /**
     * 根据菜单编号获取详细信息接口
     * @param menuId
     * @return
     */
    SysMenu selectMenuById(Long menuId);

    /**
     * 联表查询根据roleId进行查询对应的menuId列表
     * @param roleId
     * @return
     */
    List<Integer> selectMenuListByRoleId(Long roleId);

    /**
     * 获取menuId对应的批量数据
     * @param menuIdList
     * @return
     */
    List<SysMenu> selectMenuInId(@Param("menuIdList") List<Integer> menuIdList);

    /**
     * 根据用户id获取对应下拉树
     * @param roleId
     * @return
     */
    List<SysMenu> MenuListByRoleId(Long roleId);
}
