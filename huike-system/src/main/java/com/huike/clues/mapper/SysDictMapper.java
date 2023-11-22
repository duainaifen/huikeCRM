package com.huike.clues.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.service.ISysDictService;
import com.huike.common.core.domain.entity.SysDictData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-11-17 19:53
 */

/**
 * 字典相关接口
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDictData> {

    /**
     * 字典数据添加接口
     * @param sysDictData
     */
    boolean addDictData(SysDictData sysDictData);

    /**
     * 修改保存字典类型接口
     * @return
     */
    boolean updataDictData(SysDictData sysDictData);

    /**
     * 字典列表查询（分页查询）
     * @param sysDictData
     * @return
     */
    List<SysDictData> dictDataList(SysDictData sysDictData);

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
    @Select("select * from sys_dict_data where dict_code=#{dictCode}")
    SysDictData getByDictCode(SysDictData sysDictData);
}
