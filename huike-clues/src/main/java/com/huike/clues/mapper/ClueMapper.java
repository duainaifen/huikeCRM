package com.huike.clues.mapper;

import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.common.annotation.AutoFill;
import com.huike.common.enums.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-20 17:15
 */
@Mapper
public interface ClueMapper {

    /**
     * 查询线索管理列表接口(分页接口)
     * @param tbClue
     * @return
     */
    List<TbClue> list(TbClue tbClue);

    /**
     * 新增线索管理接口
     * @param tbClue
     * @return
     */
    @AutoFill(OperationType.INSERT)
    boolean addClue(TbClue tbClue);

    /**
     * 根据id获取线索基础信息接口
     * @param id
     * @return
     */
    @Select("select * from tb_clue where id=#{id}")
    TbClue getById(Long id);

    /**
     * 分配后，进行更改状态操作
     * @param tbAssignRecord
     */
    @AutoFill(OperationType.UPDATE)
    @Update("update tb_clue set status=#{tbAssignRecord.status} where id =#{clueId}")
    void update(@Param("tbAssignRecord") TbAssignRecord tbAssignRecord , @Param("clueId") Long clueId);

    /**
     * 伪线索进行更改线索表线索状态
     * @param id
     * @return
     */
    @AutoFill(OperationType.UPDATE)
    @Update("update tb_clue set status='4' where id=#{id}")
    boolean updateStatus(Long id);


    /**
     * 根据名字和电话进行查询线索
     * @param name
     * @param phone
     * @return
     */
    @Select("select * from tb_clue where name=#{name} and phone =#{phone}")
    TbClue getByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    /**
     * 记录跟进表进行插入，补全线索表里面的subject，level字段
     * @param tbClueTrackRecord
     * @return
     */
    Integer updateByTbClueTrackRecord(TbClueTrackRecord tbClueTrackRecord);
}
