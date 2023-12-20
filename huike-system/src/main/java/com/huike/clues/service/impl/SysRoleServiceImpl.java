package com.huike.clues.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.huike.clues.mapper.SysMenuMapper;
import com.huike.clues.mapper.SysRoleMapper;
import com.huike.clues.service.ISysRoleService;
import com.huike.common.core.domain.entity.SysMenu;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import com.huike.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色 业务层处理
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements ISysRoleService {
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;


    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }


    /**
     * 新增角色接口
     *
     * @param sysRole
     */
    @Override
    public void addRole(SysRole sysRole) {
        //补充字段信息
        sysRole.setCreateBy(SecurityUtils.getUsername());
        sysRole.setCreateTime(DateUtils.getNowDate());
        roleMapper.addRole(sysRole);
    }

    /**
     * 修改保存角色接口
     *
     * @param sysRole
     * @return
     */
    @Override
    public boolean updateRole(SysRole sysRole) {
        //补充更新字段
        sysRole.setUpdateBy(SecurityUtils.getUsername());
        sysRole.setUpdateTime(DateUtils.getNowDate());
        boolean result = roleMapper.updateRole(sysRole);
        return result;
    }

    /**
     * 根据角色编号获取详细信息接口
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRole getByRoleId(Long roleId) {
        SysRole result = roleMapper.getByRoleId(roleId);
        return result;
    }

    /**
     * 获取角色选择框列表接口
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> optionselect(Long userId) {
        List<Integer> roleIdList = roleMapper.selectRoleListByUserId(userId);
        List<SysMenu> sysMenuList = null;
        for (Integer roleId : roleIdList) {
            Long RoleId = Convert.toLong(roleId);
            sysMenuList = sysMenuMapper.MenuListByRoleId(RoleId);
        }
        log.info("角色选择接口返回数据:{}", sysMenuList);
        return sysMenuList;
    }

    /**
     * 删除角色接口
     *
     * @param roleIds
     */
    @Override
    public void delete(List<Long> roleIds) {
        for (Long roleId : roleIds) {
            //获取无法删除的角色名字，用于抛出异常
            SysRole sysRole = roleMapper.selectById(roleId);
            String status = sysRole.getStatus();
            if (ObjectUtil.equal(status, "0")) {
                throw new CustomException(String.format("角色%s正在被使用,无法删除", sysRole.getRoleName()));
            }
        }
        //遍历完成后，没有正在使用的，就进行删除处理
        roleMapper.deleteIds(roleIds);
    }
}
