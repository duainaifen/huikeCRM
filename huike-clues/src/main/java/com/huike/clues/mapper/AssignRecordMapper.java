package com.huike.clues.mapper;

import com.huike.clues.domain.TbAssignRecord;
import com.huike.common.annotation.AutoFill;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.enums.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-27 16:57
 */

/**
 * 分配记录表持久层接口
 */
@Mapper
public interface AssignRecordMapper {

    /**
     * 批量分配接口
     * @param tbAssignRecord
     * @param ids
     * @return
     */
    @AutoFill(OperationType.INSERT)
    boolean batchAssignment(@Param("tbAssignRecord") TbAssignRecord tbAssignRecord, @Param("ids") Long []ids);

    /**
     * 根据assignId进行修改type使得该表记录的指向商机表
     * @param id
     */
    @Update("update tb_assign_record set type='1' where assign_id=#{id}")
    void updateAssignId(Long id);
}
