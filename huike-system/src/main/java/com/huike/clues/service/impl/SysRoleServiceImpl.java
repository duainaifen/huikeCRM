package com.huike.clues.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.huike.common.annotation.DataScope;
import com.huike.common.constant.UserConstants;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.StringUtils;
import com.huike.common.utils.spring.SpringUtils;
import com.huike.clues.domain.SysRoleDept;
import com.huike.clues.domain.SysRoleMenu;
import com.huike.clues.mapper.SysRoleDeptMapper;
import com.huike.clues.mapper.SysRoleMapper;
import com.huike.clues.mapper.SysRoleMenuMapper;
import com.huike.clues.mapper.SysUserRoleMapper;
import com.huike.clues.service.ISysRoleService;

import javax.annotation.Resource;

/**
 * 角色 业务层处理
 * 
 * 
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Resource
    private SysRoleMapper roleMapper;


    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}
