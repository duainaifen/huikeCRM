package com.huike.clues.mapper;

import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.FalseClueVo;
import com.huike.common.annotation.AutoFill;
import com.huike.common.enums.OperationType;
import io.swagger.v3.oas.annotations.servers.Server;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-12 15:57
 */
@Mapper
public interface ClueTrackRecordMapper {


    /**
     * 更新跟进状态接口
     *
     * @param tbClue
     */
    @AutoFill(OperationType.INSERT)
    void insert(TbClue tbClue);

    /**
     * 伪线索更改线索跟进记录表里面的原因
     *
     * @param id
     * @param falseClueVo
     * @return
     */
    @Update("update tb_clue_track_record set false_reason=#{falseClueVo.reason},type='1' where clue_id =#{id}")
    boolean updateTypeAndFalseReason(@Param("id") Long id, @Param("falseClueVo") FalseClueVo falseClueVo);

    /**
     * 新增线索跟进记录接口
     * @param tbClueTrackRecord
     * @return
     */
    Integer addRecord(TbClueTrackRecord tbClueTrackRecord);

    /**
     * 获取线索根据记录详细信息接口
     * @param id
     * @return
     */
    @Select("select * from tb_clue_track_record where id=#{id}")
    TbClueTrackRecord selectById(Integer id);

    /**
     * 查询线索跟进记录列表接口
     * @param clueId
     * @return
     */
    @Select("select * from tb_clue_track_record where clue_id=#{clueId}")
    List<TbClueTrackRecord> selectByClueId(Integer clueId);
}
