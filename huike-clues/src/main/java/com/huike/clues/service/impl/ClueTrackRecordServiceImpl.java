package com.huike.clues.service.impl;

import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.ClueTrackRecordVo;
import com.huike.clues.domain.vo.FalseClueVo;
import com.huike.clues.mapper.ClueTrackRecordMapper;
import com.huike.clues.service.ClueTrackRecordService;
import com.huike.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-11 21:27
 */
@Service
public class ClueTrackRecordServiceImpl implements ClueTrackRecordService {

    @Resource
    private ClueTrackRecordMapper clueTrackRecordMapper;


    /**
     * 更新跟进状态为跟进中
     *
     * @param tbClue
     */
    @Override
    public void insert(TbClue tbClue) {
        clueTrackRecordMapper.insert(tbClue);
    }

    /**
     * 新增线索跟进记录接口
     *
     * @param tbClueTrackRecord
     */
    @Override
    public Integer addRecord(TbClueTrackRecord tbClueTrackRecord) {
        tbClueTrackRecord.setCreateTime(DateUtils.getNowDate());
        Integer insertNum = clueTrackRecordMapper.addRecord(tbClueTrackRecord);
        return insertNum;
    }

    /**
     * 获取线索根据记录详细信息接口
     *
     * @param id
     * @return
     */
    @Override
    public TbClueTrackRecord selectById(Integer id) {
        TbClueTrackRecord tbClueTrackRecord = clueTrackRecordMapper.selectById(id);
        return tbClueTrackRecord;
    }

    /**
     * 查询线索跟进记录列表接口
     *
     * @param clueId
     * @return
     */
    @Override
    public List<TbClueTrackRecord> list(Integer clueId) {
        List<TbClueTrackRecord> result = clueTrackRecordMapper.selectByClueId(clueId);
        return result;
    }
}
