package com.huike.clues.utils;

import java.util.Calendar;
import java.util.Date;

import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.domain.TbRulePool;
import com.huike.common.utils.spring.SpringUtils;

/**
 * 获取结束时间的工具类
 * @author 86150
 *
 */
public class HuiKeCrmDateUtils {
	

	
	public static Date getDate(int time, String type, Date now){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        if(TbRulePool.LimitTimeType.HOUR.getValue().equals(type)){
            cal.add(Calendar.HOUR, time);
        }else if(TbRulePool.LimitTimeType.DAY.getValue().equals(type)){
            cal.add(Calendar.DATE, time);
        }else if(TbRulePool.LimitTimeType.WEEK.getValue().equals(type)){
            cal.add(Calendar.DAY_OF_WEEK, time);
        }
        return cal.getTime();
    }
}
