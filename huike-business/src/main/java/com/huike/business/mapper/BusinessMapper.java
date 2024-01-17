package com.huike.business.mapper;

import com.huike.business.domain.TbBusiness;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2024-01-11 8:35
 */
@Mapper
public interface BusinessMapper {
    /**
     * 线索转化成商机进行商机表插入接口
     * @param tbBusiness
     */
    boolean insert(TbBusiness tbBusiness);
}
