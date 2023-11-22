package com.huike.web.controller.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.huike.clues.service.ISysDictService;
import com.huike.common.constant.Constants;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.core.domain.entity.SysDictType;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.core.redis.RedisCache;
import com.huike.common.utils.poi.ExcelUtil;
import com.huike.web.result.TableDataInfoActivityList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialJavaObject;
import java.util.Collection;
import java.util.List;

/**
 * @Description 字典相关接口
 * @Author daqiang
 * @Date 2023-11-17 14:24
 */
@RestController
@RequestMapping("/system/dict")
@Api(tags = "字典相关接口")
@Slf4j
public class SysDictController extends BaseController {

    @Resource
    private ISysDictService sysDictService;

    /**
     * 新增字典数据接口
     *
     * @param sysDictData
     */
    @ApiOperation("新增字典数据接口")
    @PostMapping("/data")
    public AjaxResult addDictData(@RequestBody SysDictData sysDictData) {
        boolean result = sysDictService.addDictData(sysDictData);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改保存字典类型接口
     *
     * @param sysDictData
     * @return
     */
    @ApiOperation("修改保存字典类型接口")
    @PutMapping("/data")
    public AjaxResult updataDictData(@RequestBody SysDictData sysDictData) {
        log.info("接收的数据{}", sysDictData);
        boolean result = sysDictService.updataDictData(sysDictData);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 字典数据导出接口
     */
    @GetMapping("/data/export")
    @ApiOperation("字典导出接口")
    public AjaxResult exportData(SysDictData sysDictData) {
        List<SysDictData> list = sysDictService.dictDataList(sysDictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<>(SysDictData.class);
        return util.exportExcel(list, "字典数据导出");
    }

    /**
     * 字典列表查询（分页查询）
     *
     * @param sysDictData
     * @return
     */
    @GetMapping("/data/list")
    @ApiOperation("字典列表查询（分页查询）")
    public TableDataInfo dictDataList(SysDictData sysDictData) {
        super.startPage();
        List<SysDictData> list = sysDictService.dictDataList(sysDictData);
        return getDataTable(list);
    }

    /**
     * 根据字典类型查询字典数据信息接口
     *
     * @param sysDictData
     */
    @GetMapping("/data/type")
    @ApiOperation("根据字典类型查询字典数据信息接口")
    public AjaxResult getByDictType(SysDictData sysDictData) {
        List<SysDictData> list = sysDictService.getByDictType(sysDictData);
        if (CollUtil.isEmpty(list)) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success(list);
        }
    }

    /**
     * 删除字典类型接口
     *
     * @param sysDictData
     */
    @DeleteMapping("/data")
    @ApiOperation("删除字典数据类型")
    public AjaxResult deleteDictData(SysDictData sysDictData) {
        boolean result = sysDictService.deleteDictData(sysDictService);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询字典数据详细
     *
     * @param sysDictData
     * @return
     */
    @GetMapping("/data")
    @ApiOperation("查询字典数据详细")
    public AjaxResult getByDictCode(SysDictData sysDictData) {
        startPage();
        sysDictData = sysDictService.getByDictCode(sysDictData);
        if (ObjectUtil.isEmpty(sysDictData)) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success(sysDictData);
        }
    }

//---------------------------下面是字典类型相关接口----------------------

    /**
     * 新增字典类型接口
     *
     * @param sysDictType
     * @return
     */
    @ApiOperation("新增字典类型接口")
    @PostMapping("/type")
    public AjaxResult addDictType(@RequestBody SysDictType sysDictType) {
        boolean result = sysDictService.addDictType(sysDictType);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改字典类型接口
     *
     * @param sysDictType
     * @return
     */
    @PutMapping("/type")
    @ApiOperation("修改字典类型接口")
    public AjaxResult updateType(@RequestBody SysDictType sysDictType) {
        boolean result = sysDictService.updateType(sysDictType);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    @Resource
    private RedisCache redisCache;

    /**
     * 字典类型清除缓存接口
     */
    @DeleteMapping("/type/clearCache")
    @ApiOperation("字典类型清除缓存接口")
    public void clearCache() {
        Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 字典类型导出接口
     *
     * @param sysDictType
     * @return
     */
    @GetMapping("/type/export")
    @ApiOperation("字典类型导出接口")
    public AjaxResult exportType(SysDictType sysDictType) {
        List<SysDictType> list = sysDictService.dictTpteList(sysDictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        return util.exportExcel(list, "字典类型导出");
    }

    /**
     * 字典类型分页查询接口
     *
     * @param sysDictType
     * @return
     */
    @GetMapping("/type/list")
    @ApiOperation("字典类型分页查询接口")
    public TableDataInfo dictTypeList(SysDictType sysDictType) {
        startPage();
        List<SysDictType> list = sysDictService.dictTpteList(sysDictType);
        return getDataTable(list);
    }

    /**
     * 获取字典选择框列表
     *
     * @param sysDictType
     * @return
     */
    @GetMapping("/type/optionselect")
    @ApiOperation("获取字典选择框列表")
    public AjaxResult optionselect(SysDictType sysDictType) {
        List<SysDictType> list = sysDictService.dictTpteList(sysDictType);
        if (CollUtil.isEmpty(list)) {
            return AjaxResult.error();
        } else {
            return AjaxResult.success(list);
        }
    }

    /**
     * 删除字典类型接口
     *
     * @param sysDictType
     * @return
     */
    @ApiOperation("删除字典类型")
    @DeleteMapping("/type")
    public AjaxResult deleteType(SysDictType sysDictType) {
        boolean result = sysDictService.deleteType(sysDictType);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询字典类型详细接口
     */
    @GetMapping("/type/{dictId}")
    @ApiOperation("查询字典类型详细接口")
    public AjaxResult dictTypeDetail(SysDictType sysDictType) {
        SysDictType result = sysDictService.dictTypeDetail(sysDictType);
        return AjaxResult.success(result);
    }
}
