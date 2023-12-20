package com.huike.clues.mapper;

import com.huike.common.annotation.AutoFill;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.enums.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-04 19:01
 */

@Mapper
public interface SysDeptMapper {

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
    @AutoFill(value = OperationType.INSERT)
    boolean addDept(SysDept sysDept);


    /**
     * 删除部门接口
     * @param sysDept
     * @return
     */
    @Delete("delete from sys_dept where dept_id=#{deptId}")
    boolean deleteDept(SysDept sysDept);

    /**
     * 修改部门接口
     * @param sysDept
     * @return
     */
    @AutoFill(value = OperationType.UPDATE)
    boolean updateDept(SysDept sysDept);

    /**
     * 根据部门编号获取详细信息接口
     * @param deptId
     * @return
     */
    @Select("select * from sys_dept where dept_id=#{deptId}")
    SysDept selectById(Long deptId);

    /**
     * 获取对应角色部门列表树接口
     * @param roleId
     * @return
     */
    List<SysDept> roleDeptTreeselect(Long roleId);
}
