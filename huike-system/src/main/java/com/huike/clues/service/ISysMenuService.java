package com.huike.clues.service;

import java.util.List;
import java.util.Set;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysMenu;
import com.huike.clues.domain.vo.RouterVo;
import com.huike.common.core.domain.entity.SysRole;

/**
 * 菜单 业务层
 *
 *
 */
public interface ISysMenuService
{




    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);



    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);


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
     * @param sysMenu
     * @return
     */
    SysMenu selectMenuById(SysMenu sysMenu);

    /**
     * 加载对应角色菜单列表树接口
     * @param sysRole
     * @return
     */
    List selectMenuListByRoleId(SysRole sysRole);
}
