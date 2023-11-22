package com.huike.clues.mapper;

import java.util.List;
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
}
