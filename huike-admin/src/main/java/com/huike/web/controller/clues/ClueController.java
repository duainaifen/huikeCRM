package com.huike.web.controller.clues;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.huike.clues.domain.TbClue;
import com.huike.clues.domain.TbClueTrackRecord;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.domain.vo.AssignmentVo;
import com.huike.clues.domain.vo.FalseClueVo;
import com.huike.clues.service.ClueService;
import com.huike.clues.service.ClueTrackRecordService;
import com.huike.clues.service.CourseService;
import com.huike.common.annotation.Log;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.enums.BusinessType;
import com.huike.common.utils.poi.ExcelUtil;
import com.huike.web.service.ClueBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-20 17:12
 */

/**
 * 线索管理相关接口
 */
@RestController
@RequestMapping("/clues")
@Slf4j
@Api(tags = "线索管理相关接口")
public class ClueController extends BaseController {

    @Resource
    private ClueService clueService;
    @Resource
    private ClueBusinessService clueBusinessService;

    /**
     * 查询线索管理接口(分页接口)
     *
     * @param tbClue
     * @return
     */
    @GetMapping("/clue/list")
    public TableDataInfo list(TbClue tbClue) {
        startPage();
        List<TbClue> result = clueService.list(tbClue);
        return getDataTable(result);
    }

    /**
     * 新增线索管理接口
     *
     * @param tbClue
     * @return
     */
    @PostMapping("/clue")
    public AjaxResult addClue(@RequestBody TbClue tbClue) {
        boolean result = clueService.addClue(tbClue);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 根据id获取线索基础信息接口
     *
     * @return
     */
    @GetMapping("/clue/{id}")
    public AjaxResult getById(TbClue tbClue) {
        Long id = tbClue.getId();
        TbClue result = clueService.getById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量分配接口
     *
     * @param assignmentVo
     * @return
     */
    @ApiOperation("批量分配接口")
    @PutMapping("/clue/assignment")
    public AjaxResult batchAssignment(@RequestBody AssignmentVo assignmentVo) {
        boolean result = clueService.batchAssignment(assignmentVo);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 线索转商机接口
     *
     * @param id
     * @return
     */
    @PutMapping("/clue/changeBusiness/{id}")
    @ApiOperation("线索转商机接口")
    public AjaxResult changeBusiness(@PathVariable Long id) {
        boolean result = clueBusinessService.changeBusiness(id);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 伪线索接口
     *
     * @param id
     * @param falseClueVo
     * @return
     */
    @ApiOperation("伪线索接口")
    @PutMapping("/clue/false/{id}")
    public AjaxResult falseClue(@PathVariable Long id, @RequestBody FalseClueVo falseClueVo) {
        boolean result = clueService.falseClue(id, falseClueVo);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 查询线索管理接口(分页接口)
     *
     * @param tbClue
     * @return
     */
    @GetMapping("/clue/pool")
    public TableDataInfo poolList(TbClue tbClue) {
        startPage();
        List<TbClue> result = clueService.list(tbClue);
        return getDataTable(result);
    }

    /**
     * 批量捞取接口
     *
     * @param assignmentVo
     * @return
     */
    @ApiOperation("批量捞取接口")
    @PutMapping("/clue/gain")
    public AjaxResult gain(@RequestBody AssignmentVo assignmentVo) {
        log.info("批量捞取接收的信息:{}", assignmentVo);
        boolean result = clueService.batchAssignment(assignmentVo);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }

    }

    /**
     * 批量添加线索导入接口
     *
     * @return
     */
    @PostMapping("/clue/importData")
    @Log(title = "线索模块", businessType = BusinessType.IMPORT)
    @ApiOperation("批量添加线索导入接口")
    public AjaxResult importData(MultipartFile file) throws Exception {

        ExcelUtil<TbClue> util = new ExcelUtil<>(TbClue.class);
        List<TbClue> tbClueList = util.importExcel(file.getInputStream());
        //调用insert进行批量进行插入
        String resultMsg = clueService.importData(tbClueList);

        //这个是返回数据的类，构造返回数据
        return AjaxResult.success(resultMsg);
    }


    //=======================================下面是线索跟进记录管理相关接口====================================
    /**
     * 线索跟进记录管理相关接口
     */


    @Resource
    private ClueTrackRecordService clueTrackRecordService;

    /**
     * 新增线索跟进记录接口
     *
     * @param tbClueTrackRecord
     */
    @Transactional
    @PostMapping("/record")
    public AjaxResult record(TbClueTrackRecord tbClueTrackRecord) {
        Integer insertNum = clueTrackRecordService.addRecord(tbClueTrackRecord);
        //还要在clues表补充subject和level
        Integer updateNum = clueService.updateByClueId(tbClueTrackRecord);
        if (insertNum > 0 && updateNum > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 获取线索根据记录详细信息接口
     *
     * @param id
     * @return
     */
//    @ApiOperation("获取线索根据记录详细信息接口")
//    @GetMapping("/record/{id}")
//    public AjaxResult recordById(@PathVariable Integer id) {
//        TbClueTrackRecord tbClueTrackRecord = clueTrackRecordService.selectById(id);
//        if (ObjectUtil.isEmpty(tbClueTrackRecord)) {
//            return AjaxResult.error();
//        }
//        return AjaxResult.success(tbClueTrackRecord);
//    }

    /**
     * 查询线索跟进记录列表
     *
     * @param clueId
     * @return
     */
    @GetMapping("record/list")
    public TableDataInfo recordList(@RequestParam Integer clueId) {
        log.info("接收到的线索id为:{}", clueId);
        startPage();
        List<TbClueTrackRecord> result = clueTrackRecordService.list(clueId);
        return getDataTable(result);
    }


    //================================下面是课程管理相关接口=================================
    @Resource
    private CourseService courseService;

    /**
     * 新增课程管理接口
     *
     * @param tbCourse
     * @return
     */
    @PostMapping("/course")
    @ApiOperation("新增课程管理接口")
    public AjaxResult addCourse(@RequestBody TbCourse tbCourse) {
        boolean result = courseService.addCourse(tbCourse);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 修改课程管理接口
     *
     * @param tbCourse
     * @return
     */
    @PutMapping("/course")
    public AjaxResult updateCourse(@RequestBody TbCourse tbCourse) {
        boolean result = courseService.updateCourse(tbCourse);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success(result);
        }
        return AjaxResult.error();
    }

    /**
     * 查询课程管理列表接口
     *
     * @param tbCourse
     * @return
     */
    @GetMapping("/course/list")
    @ApiOperation("查询课程管理列表接口")
    public TableDataInfo courseList(TbCourse tbCourse) {
        log.info("课程管理接口接收到:{}", tbCourse);
        startPage();
        List<TbCourse> result = courseService.courseList(tbCourse);
        return getDataTable(result);
    }

    /**
     * 课程下拉列表接口
     *
     * @return
     */
    @GetMapping("/course/listselect")
    @ApiOperation("课程下拉列表接口")
    public AjaxResult listSelect(@RequestParam String subject) {
        List<TbCourse> result = courseService.selectBySubject(subject);
        return AjaxResult.success(result);
    }

    /**
     * 删除课程管理接口
     *
     * @return
     */
    @DeleteMapping("/course/{ids}")
    @ApiOperation("删除课程管理接口")
    public AjaxResult courseDelete(@PathVariable String ids) {
        log.info("删除课程的接收数据为:{}", ids);
        boolean result = courseService.courseDelete(ids);
        if (BooleanUtil.isTrue(result)) {
            return AjaxResult.success();

        }
        return AjaxResult.error();
    }

    /**
     * 获取课程管理详细信息
     * @param id
     * @return
     */
    @GetMapping("course/{id}")
    public AjaxResult courseGetById(@PathVariable Integer id) {
        TbCourse tbCourse = courseService.courseGetById(id);
        return AjaxResult.success(tbCourse);
    }
}
