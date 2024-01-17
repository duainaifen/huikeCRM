package com.huike.clues.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.vo.AssignmentVo;
import com.huike.clues.domain.vo.ClueTrackRecordVo;
import com.huike.clues.domain.vo.FalseClueVo;
import com.huike.clues.mapper.AssignRecordMapper;
import com.huike.clues.mapper.ClueMapper;
import com.huike.clues.mapper.ClueTrackRecordMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.service.ClueService;
import com.huike.clues.service.ClueTrackRecordService;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-20 17:14
 */
@Service
@Slf4j
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueMapper clueMapper;
    @Resource
    private AssignRecordMapper assignRecordMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private ClueTrackRecordMapper clueTrackRecordMapper;


    /**
     * 查询线索管理接口(分页接口)
     *
     * @param tbClue
     * @return
     */
    @Override
    public List<TbClue> list(TbClue tbClue) {
        List<TbClue> result = clueMapper.list(tbClue);
        return result;
    }

    /**
     * 新增线索管理接口
     *
     * @param tbClue
     * @return
     */
    @Override
    public boolean addClue(TbClue tbClue) {
        boolean result = clueMapper.addClue(tbClue);
        return result;
    }

    /**
     * 根据id获取线索基础信息接口
     *
     * @param id
     * @return
     */
    @Override
    public TbClue getById(Long id) {
        TbClue result = clueMapper.getById(id);
        return result;
    }

    /**
     * 批量分配接口
     *
     * @param assignmentVo
     * @return
     */
    @Override
    @Transactional
    public boolean batchAssignment(AssignmentVo assignmentVo) {
        Long userId = assignmentVo.getUserId();
        //根据userId查询user表获取相关信息，进行补充数据
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        TbAssignRecord tbAssignRecord = new TbAssignRecord();
        tbAssignRecord.setDeptId(sysUser.getDeptId());
        tbAssignRecord.setUserName(sysUser.getUserName());
        tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
        tbAssignRecord.setUserId(userId);
        tbAssignRecord.setLatest("1");
        tbAssignRecord.setStatus("2");


        //补充线索分配记录表
        Long[] ids = assignmentVo.getIds();

        for (Long clueId : ids) {
            //更新线索表里面的状态
            clueMapper.update(tbAssignRecord, clueId);
        }
        boolean result = assignRecordMapper.batchAssignment(tbAssignRecord, ids);

        return result;
    }

    /**
     * 伪线索接口
     *
     * @param id
     * @param falseClueVo
     * @return
     */
    @Transactional
    @Override
    public boolean falseClue(Long id, FalseClueVo falseClueVo) {
        boolean result;
        log.info("跟进记录信息:{}", falseClueVo);
        //根据线索id修改线索表里面的状态为伪线索
        result = clueMapper.updateStatus(id);

        //添加线索跟进记录表里面的type为伪线索和失败原因
        result = clueTrackRecordMapper.updateTypeAndFalseReason(id, falseClueVo);
        return result;
    }

    /**
     * 批量添加线索导入接口
     *
     * @param tbClueList
     * @return
     */
    @Override
    public String importData(List<TbClue> tbClueList) {
        if (StringUtils.isNull(tbClueList) || tbClueList.size() == 0) {
            throw new CustomException("导入数据不能为空!");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TbClue tbClue : tbClueList) {
            try {
                //验证是否有该线索了(根据名字和电话号码进行查询)
                TbClue tbClueResult = clueMapper.getByNameAndPhone(tbClue.getName(), tbClue.getPhone());
                if (StringUtils.isNull(tbClueResult)) {
                    //进行插入新线索
                    this.addClue(tbClue);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、线索名字" + tbClue.getName() + "导入成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、线索名字" + tbClue.getName() + "已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、线索名字" + tbClue.getName() + "导入失败";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共" + failureNum + "条数据格式不正确，错误如下:");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜你，数据已全部导入成功！共" + successMsg + "条，数据如下:");
        }
        return successMsg.toString();
    }

    /**
     * 记录跟进表进行插入，补全线索表里面的subject，level字段
     *
     * @param tbClueTrackRecord
     * @return
     */
    @Override
    public Integer updateByClueId(TbClueTrackRecord tbClueTrackRecord) {
        Integer result = clueMapper.updateByTbClueTrackRecord(tbClueTrackRecord);
        return result;
    }
}
