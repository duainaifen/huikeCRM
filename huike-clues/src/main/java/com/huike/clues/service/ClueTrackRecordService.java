package com.huike.clues.service;

import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.FalseClueVo;
import com.huike.common.annotation.AutoFill;
import com.huike.common.enums.OperationType;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-11 21:26
 */
public interface ClueTrackRecordService {

    /**
     * 更新跟进状态为跟进中
     * @param tbClue
     */
    void insert(TbClue tbClue);


    /**
     * 新增线索跟进记录接口
     * @param tbClueTrackRecord
     */
    Integer addRecord(TbClueTrackRecord tbClueTrackRecord);

    /**
     * 获取线索根据记录详细信息接口
     * @param id
     * @return
     */
    TbClueTrackRecord selectById(Integer id);

    /**
     * 查询线索跟进记录列表接口
     * @param clueId
     * @return
     */
    List<TbClueTrackRecord> list(Integer clueId);
}
