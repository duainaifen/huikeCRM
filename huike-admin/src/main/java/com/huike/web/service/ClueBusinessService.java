package com.huike.web.service;

import cn.hutool.core.bean.BeanUtil;
import com.huike.business.domain.TbBusiness;
import com.huike.business.mapper.BusinessMapper;
import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.AssignRecordMapper;
import com.huike.clues.mapper.ClueMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-11 19:55
 */
@Service
public class ClueBusinessService {

    @Resource
    private AssignRecordMapper assignRecordMapper;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private BusinessMapper businessMapper;

    /**
     * 线索转商机接口
     *
     * @param id
     * @return
     */

    @Transactional
    public boolean changeBusiness(Long id) {
        //要线索转商机，我们要修改两张表一是tb_assign_record表，我们要重新关联到商机表
        assignRecordMapper.updateAssignId(id);
        //二是tb_business表，更新商机表记录对应的信息
        TbClue tbClue = clueMapper.getById(id);
        //商机表的数据来源于两张表，分别是跟进记录表和课程表(构造插入数据)
        TbBusiness tbBusiness = new TbBusiness();
        BeanUtil.copyProperties(tbClue, tbBusiness, true);
        //进行调用对应mapper进行插入数据
        boolean result = businessMapper.insert(tbBusiness);
        return result;
    }
}
