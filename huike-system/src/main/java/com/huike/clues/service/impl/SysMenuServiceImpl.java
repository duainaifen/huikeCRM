package com.huike.clues.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.huike.common.utils.DateUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huike.common.constant.UserConstants;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysMenu;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import com.huike.clues.domain.vo.MetaVo;
import com.huike.clues.domain.vo.RouterVo;
import com.huike.clues.mapper.SysMenuMapper;
import com.huike.clues.mapper.SysRoleMapper;
import com.huike.clues.mapper.SysRoleMenuMapper;
import com.huike.clues.service.ISysMenuService;

import javax.annotation.Resource;

/**
 * 菜单 业务层处理
 */
@Service
@Slf4j
public class SysMenuServiceImpl implements ISysMenuService {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Resource
    private SysMenuMapper menuMapper;


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMeunFrame(menu)) {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }


    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMeunFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMeunFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMeunFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }


    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增菜单接口
     *
     * @param sysMenu
     * @return
     */
    @Override
    public boolean addMenu(SysMenu sysMenu) {
        //补充字段
        sysMenu.setCreateBy(SecurityUtils.getUsername());
        sysMenu.setCreateTime(DateUtils.getNowDate());
        boolean result = menuMapper.addMenu(sysMenu);
        return result;
    }

    /**
     * 修改菜单接口
     *
     * @param sysMenu
     * @return
     */
    @Override
    public boolean updateMenu(SysMenu sysMenu) {
        //添加修改的字段
        sysMenu.setUpdateBy(SecurityUtils.getUsername());
        sysMenu.setUpdateTime(DateUtils.getNowDate());
        boolean result = menuMapper.updateMenu(sysMenu);
        return result;
    }

    /**
     * 菜单分页查询接口
     *
     * @param sysMenu
     * @return
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu sysMenu) {
        List<SysMenu> list = menuMapper.selectMenuList(sysMenu);
        return list;
    }

    /**
     * 根据菜单编号获取详细信息接口
     *
     * @param sysMenu
     * @return
     */
    @Override
    public SysMenu selectMenuById(SysMenu sysMenu) {
        Long menuId = sysMenu.getMenuId();
        sysMenu = menuMapper.selectMenuById(menuId);
        return sysMenu;
    }

    /**
     * 加载对应角色菜单列表树接口
     *
     * @param sysRole
     * @return
     */
    @Override
    public List selectMenuListByRoleId(SysRole sysRole) {
        //先连表搜索对应的menuid
        Long roleId = sysRole.getRoleId();
        log.info("接收的用户id为:{}",roleId);
//        List<Integer> menuIdList = menuMapper.selectMenuListByRoleId(roleId);
//        log.info("id列表是:{}", menuIdList);
//        //根据menuid进行查询全部数据
//        List<SysMenu> menuList = menuMapper.selectMenuInId(menuIdList);
//        log.info("全部数据是:{}",menuList);

        List<SysMenu> menuList=menuMapper.MenuListByRoleId(roleId);
        //将数据构造成树形结构进行返回

        //获取根节点，即父id为0的
        List<SysMenu> menuRootList = menuList.stream().filter(menu -> {
            return menu.getParentId().equals(0);
        }).collect(Collectors.toList());
        //构造返回数据的集合
        List<SysMenu> treeList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(menuRootList)) {
            SysMenu menuRoot = menuRootList.get(0);
            treeList.add(menuRoot);
            //添加子节点
            addChildren(menuRoot, menuList);
        }

        return treeList;
    }

    /**
     * 构造树形结构方法
     *
     * @param menuRoot
     * @param menuList
     */
    private void addChildren(SysMenu menuRoot, List<SysMenu> menuList) {
        List<SysMenu> menuChildrenList = menuList.stream().filter(sysMenu ->
                menuRoot.getMenuId().equals(sysMenu.getParentId())).collect(Collectors.toList());
        menuRoot.setChildren(menuChildrenList);
        menuChildrenList.forEach(menu -> {
            addChildren(menu, menuChildrenList);
        });
    }
}
