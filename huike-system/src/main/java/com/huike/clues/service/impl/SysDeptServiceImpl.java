package com.huike.clues.service.impl;

import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.service.ISysDeptService;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 18:55
 */
@Service
@Slf4j
public class SysDeptServiceImpl implements ISysDeptService {

    @Resource
    private SysDeptMapper deptMapper;

    /**
     * 获取部门列表(分页接口)
     *
     * @param sysDept
     * @return
     */
    @Override
    public List<SysDept> list(SysDept sysDept) {
        List<SysDept> result = deptMapper.list(sysDept);
        return result;
    }

    /**
     * 新增部门接口
     *
     * @param sysDept
     * @return
     */
    @Override
    public boolean addDept(SysDept sysDept) {
        //补充字段
//        sysDept.setCreateBy(SecurityUtils.getUsername());
//        sysDept.setCreateTime(DateUtils.getNowDate());
        //构建祖级列表
        sysDept.setAncestors(sysDept.getAncestors() + "," + sysDept.getParentId());
        boolean result = deptMapper.addDept(sysDept);
        return result;
    }

    /**
     * 删除部门接口
     *
     * @param sysDept
     * @return
     */
    @Override
    public boolean deleteDept(SysDept sysDept) {
        //填充字段
        sysDept.setUpdateBy(SecurityUtils.getUsername());
        sysDept.setUpdateTime(DateUtils.getNowDate());
        boolean result = deptMapper.deleteDept(sysDept);
        return result;
    }

    /**
     * 修改部门接口
     *
     * @param sysDept
     * @return
     */
    @Override
    public boolean updateDept(SysDept sysDept) {
        //补充字段
//        sysDept.setUpdateTime(DateUtils.getNowDate());
//        sysDept.setUpdateBy(SecurityUtils.getUsername());
        return deptMapper.updateDept(sysDept);
    }

    /**
     * 根据部门编号获取详细信息接口
     *
     * @param sysDept
     * @return
     */
    @Override
    public SysDept selectById(SysDept sysDept) {
        Long deptId = sysDept.getDeptId();
        SysDept result = deptMapper.selectById(deptId);
        return result;
    }

    /**
     * 查询部门列表(排除指定部门节点)
     *
     * @param deptId
     * @return
     */
    @Override
    public List<SysDept> excludeList(Long deptId) {
        SysDept sysDept = new SysDept();
        List<SysDept> allList = deptMapper.list(sysDept);

        //利用lamada表达式过滤出需要的数据
        List<SysDept> resultList = allList.stream().filter(dept -> !(dept.getDeptId().equals(deptId))).collect(Collectors.toList());
        return resultList;
    }

    //TODO 没找到对应的前端接口

    /**
     * 获取对应角色部门列表树接口
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysDept> roleDeptTreeselect(Long roleId) {
        List<SysDept> result = deptMapper.roleDeptTreeselect(roleId);

        return result;
    }

    @Override
    //构造树形结构
    public List<SysDept> getroleTreeDept(List<SysDept> sysDeptList) {
        List<SysDept> resultList = new ArrayList<>();
        List<SysDept> pathList = sysDeptList.stream().filter(dept -> dept.getParentId() == 0).collect(Collectors.toList());

        SysDept rootDeot = pathList.get(0);
        resultList.add(rootDeot);
        //添加子节点
        addChildren(rootDeot, sysDeptList);
        return resultList;
    }

    //添加子节点抽取方法
    public void addChildren(SysDept rootDept, List<SysDept> sysDeptList) {
        //过滤出包含有根节点的父id，放进去
        List<SysDept> tempList = sysDeptList.stream().filter(dept -> dept.getParentId()==rootDept.getDeptId())
                .collect(Collectors.toList());
        rootDept.setChildren(tempList);
        //递归进行遍历
        tempList.forEach(dept -> {
            addChildren(dept, sysDeptList);
        });
    }
}
