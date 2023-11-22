package com.huike.clues.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.mapper.SysDictMapper;
import com.huike.clues.mapper.SysDictTypeMapper;
import com.huike.clues.service.ISysDictService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.core.domain.entity.SysDictType;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-11-17 16:31
 */
@Service
@Slf4j
@Api(tags = "字典相关接口")
//extends ServiceImpl<SysDictMapper,SysDictData> implements ISysDictService使用mybatisPlus的iservice顶级接口
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictData> implements ISysDictService {

    @Resource
    SysDictMapper sysDictMapper;

    /**
     * 字典数据添加接口
     */
    @Override
    public boolean addDictData(SysDictData sysDictData) {

        //先进行判空处理，增加代码健壮性
        if (ObjectUtil.isEmpty(sysDictData)) {
            AjaxResult error = AjaxResult.error("添加数据为空,请确定后重新添加");
            return false;

        }
        //补全没有的信息
        sysDictData.setCreateTime(DateUtils.getNowDate());
        sysDictData.setCreateBy(SecurityUtils.getUsername());
        sysDictData.setUpdateTime(DateUtils.getNowDate());
        sysDictData.setUpdateBy(SecurityUtils.getUsername());
        boolean result = sysDictMapper.addDictData(sysDictData);
        //sysDictMapper.insert(sysDictData);
        //super.save(sysDictData);
        return BooleanUtil.isTrue(result);
    }

    /**
     * 修改保存字典类型接口
     *
     * @param sysDictData
     * @return
     */
    @Override
    public boolean updataDictData(SysDictData sysDictData) {
        if (ObjectUtil.isEmpty(sysDictData)) {
            return false;
        }
        //这是更改接口，我们要补全更改时间和更改人的名字
        sysDictData.setUpdateBy(SecurityUtils.getUsername());
        sysDictData.setUpdateTime(DateUtils.getNowDate());
        boolean result = sysDictMapper.updataDictData(sysDictData);
        return BooleanUtil.isTrue(result);
    }

    /**
     * 字典列表查询（分页查询）
     *
     * @param sysDictData
     * @return
     */
    @Override
    public List<SysDictData> dictDataList(SysDictData sysDictData) {
        List<SysDictData> list = sysDictMapper.dictDataList(sysDictData);
        return list;
    }

    /**
     * 根据字典类型查询字典数据信息接口
     *
     * @param sysDictData
     * @return
     */
    @Override
    public List<SysDictData> getByDictType(SysDictData sysDictData) {
        List<SysDictData> list = sysDictMapper.dictDataList(sysDictData);
        return list;
    }

    /**
     * 删除字典类型
     *
     * @param sysDictService
     * @return
     */
    @Override
    public boolean deleteDictData(ISysDictService sysDictService) {
        boolean result = sysDictMapper.deleteDictData(sysDictService);
        return result;
    }

    /**
     * 查询字典数据详细
     *
     * @param sysDictData
     * @return
     */
    @Override
    public SysDictData getByDictCode(SysDictData sysDictData) {
        sysDictData = sysDictMapper.getByDictCode(sysDictData);
        return sysDictData;
    }

    //-------------------------下面是字典类型的接口------------------------
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    /**
     * 新增字典类型接口
     *
     * @param sysDictType
     * @return
     */
    @Override
    public boolean addDictType(SysDictType sysDictType) {
        //补充缺少的字段信息
        sysDictType.setCreateBy(SecurityUtils.getUsername());
        sysDictType.setUpdateBy(SecurityUtils.getUsername());
        sysDictType.setCreateTime(DateUtils.getNowDate());
        sysDictType.setUpdateTime(DateUtils.getNowDate());
        boolean result = sysDictTypeMapper.addDictType(sysDictType);
        return result;
    }

    /**
     * 修改字典类型接口
     *
     * @param sysDictType
     * @return
     */
    @Override
    public boolean updateType(SysDictType sysDictType) {
        //修改要填充改变的字段
        sysDictType.setUpdateTime(DateUtils.getNowDate());
        sysDictType.setUpdateBy(SecurityUtils.getUsername());
        boolean result=sysDictTypeMapper.updateType(sysDictType);
        return result;
    }

    /**
     * 字典类型导出接口
     *
     * @param sysDictType
     * @return
     */
    @Override
    public List<SysDictType> dictTpteList(SysDictType sysDictType) {
        List<SysDictType>list=sysDictTypeMapper.dictTypeList(sysDictType);
        return list;
    }

    /**
     * 删除字典类型接口
     *
     * @param sysDictType
     * @return
     */
    @Override
    public boolean deleteType(SysDictType sysDictType) {
        boolean result=sysDictTypeMapper.deleteType(sysDictType);
        return result;
    }

    /**
     * 查询字典类型详细接口
     *
     * @param sysDictType
     * @return
     */
    @Override
    public SysDictType dictTypeDetail(SysDictType sysDictType) {
        SysDictType result=sysDictTypeMapper.getByDictId(sysDictType);
        return result;
    }
}
