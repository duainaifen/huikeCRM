package com.huike.clues.mapper;

import com.huike.common.core.domain.entity.SysDictType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-11-21 15:03
 */

/**
 * 字典类型相关接口
 */
@Mapper
public interface SysDictTypeMapper {
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
     * 字典类型导出接口
     * @param sysDictType
     * @return
     */
    List<SysDictType> dictTypeList(SysDictType sysDictType);

    /**
     * 删除字典类型接口
     * @param sysDictType
     * @return
     */
    @Delete("delete from sys_dict_type where dict_id=#{dictId}  ")
    boolean deleteType(SysDictType sysDictType);

    /**
     * 查询字典类型详细接口
     * @param sysDictType
     * @return
     */
    @Select("select * from sys_dict_type where dict_id=#{dictId}")
    SysDictType getByDictId(SysDictType sysDictType);
}
