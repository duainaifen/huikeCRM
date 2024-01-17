package com.huike.clues.service;

import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.AssignmentVo;
import com.huike.clues.domain.vo.FalseClueVo;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-20 17:14
 */
public interface ClueService {

    /**
     * 查询线索管理接口(分页接口)
     * @param tbClue
     * @return
     */
    List<TbClue> list(TbClue tbClue);

    /**
     * 新增线索管理接口
     * @param tbClue
     * @return
     */
    boolean addClue(TbClue tbClue);

    /**
     * 根据id获取线索基础信息接口
     * @param id
     * @return
     */
    TbClue getById(Long id);

    /**
     * 批量分配接口
     * @param assignmentVo
     * @return
     */
    boolean batchAssignment(AssignmentVo assignmentVo);


    /**
     * 伪线索接口
     * @param id
     * @param falseClueVo
     * @return
     */
    boolean falseClue(Long id, FalseClueVo falseClueVo);

    /**
     * 批量添加线索导入接口
     * @param tbClueList
     * @return
     */
    String importData(List<TbClue> tbClueList);

    /**
     * 记录跟进表进行插入，补全线索表里面的subject，level字段
     * @param tbClueTrackRecord
     * @return
     */
    Integer updateByClueId(TbClueTrackRecord tbClueTrackRecord);
}
