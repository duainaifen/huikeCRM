package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.core.domain.entity.SysDictType;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-11-17 16:20
 */
public interface ISysDictService extends IService<SysDictData> {

    /**
     * 添加字典数据
     */
    boolean addDictData(SysDictData sysDictData);

    /**
     * 修改保存字典类型接口
     * @param sysDictData
     * @return
     */
    boolean updataDictData(SysDictData sysDictData);

    /**
     * 字典列表查询（分页查询）
     * @param dictType
     * @return
     */
    List<SysDictData> dictDataList(SysDictData dictType);

    /**
     * 根据字典类型查询字典数据信息接口
     * @param sysDictData
     * @return
     */
    List<SysDictData> getByDictType(SysDictData sysDictData);

    /**
     * 删除字典类型
     * @param sysDictService
     * @return
     */
    boolean deleteDictData(ISysDictService sysDictService);

    /**
     * 查询字典数据详细
     * @param sysDictData
     * @return
     */
    SysDictData getByDictCode(SysDictData sysDictData);


    /**
     * 新增字典类型接口
     * @param sysDictType
     * @return
     */
    boolean addDictType(SysDictType sysDictType);

    /**
     * 修改字典类型接口
     * @param sysDictType
     * @return
     */
    boolean updateType(SysDictType sysDictType);

    /**
     * 字典类型导出接口（分页共用接口）
     * @param sysDictType
     * @return
     */
    List<SysDictType> dictTpteList(SysDictType sysDictType);

    /**
     * 删除字典类型接口
     * @param sysDictType
     * @return
     */
    boolean deleteType(SysDictType sysDictType);

    /**
     * 查询字典类型详细接口
     * @param sysDictService
     * @return
     */
    SysDictType dictTypeDetail(SysDictType sysDictType);
}
