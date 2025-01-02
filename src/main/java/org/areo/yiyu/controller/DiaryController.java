package org.areo.yiyu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.exception.DiaryNotFoundException;
import org.areo.yiyu.request.AddDiaryRequest;
import org.areo.yiyu.request.UpdateDiaryRequest;
import org.areo.yiyu.response.PageResponse;
import org.areo.yiyu.response.Result;
import org.areo.yiyu.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/diary")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Tag(name="日记控制器",description = "日记CRUD接口")
public class DiaryController {
    private final DiaryService diaryService;


    //新增日记
    @PostMapping
    @Operation(summary = "新增日记接口",description = "请以json格式输入diary对象" +
            "重点传入text和tag,其中tag为list<tag>形式,重点传入tagcontent值")
    public Result addDiary(@RequestBody AddDiaryRequest addDiaryRequest) {//传入一个diary对象

        log.info("新增日记:{}", addDiaryRequest);
        diaryService.addDiary(addDiaryRequest);
        return Result.success();
    }


    //批量删除日记
    @DeleteMapping("/{diaryIds}")
    @Operation(summary = "批量删除接口",description = "请在url中加入要删除的diary的id,支持批量删除")
    public Result deleteDiary(@PathVariable List<Integer> diaryIds) {

        log.info("批量删除操作,ids:{}", diaryIds);
        diaryService.deleteDiary(diaryIds);
        return Result.success();

    }


    //修改日记
    @PutMapping
    @Operation(summary = "修改日记接口",description = "请以json形式输入diary对象" +
            "重点传入id,text和tag,其中tag为list<tag>形式,重点传入tagcontent值")
    public Result updateDiary(@RequestBody UpdateDiaryRequest updateDiaryRequest) throws DiaryNotFoundException {

        log.info("修改日记:{}",updateDiaryRequest);
        diaryService.updateDiary(updateDiaryRequest);
            return Result.success();
    }


    //查询日记
    @GetMapping
    @Operation(summary = "查询日记接口",description = "请传入当前页码:pageNo(默认为1),每页数据量:pageSize(默认为5),查询的text内容(支持模糊查询)," +
            "查询的tag内容(支持模糊查询),时间起止日期beginTime和endTime," +
            "以哪一列排序(create_time或last_modify_time),以什么顺序排列(asc或desc)")
    public Result pageDiary(@RequestParam(defaultValue = "1") Integer pageNo,
                            @RequestParam(defaultValue = "5") Integer pageSize,
                            @RequestParam(required = false) String text,
                            @RequestParam(required = false) String tag,
                            @RequestParam(required = false) String beginTime,
                            @RequestParam(required = false) String endTime,
                            @RequestParam(defaultValue = "create_time") String sortBy,
                            @RequestParam(defaultValue = "asc") String order) {

        LocalDateTime theBeginTime = null;
        LocalDateTime theEndTime = null;

        //校验排序列表和方式

        if (!"create_time".equalsIgnoreCase(sortBy) &&!"last_modify_time".equalsIgnoreCase(sortBy)) {
            return Result.error("无效的排序字段，只支持 create_time 或 last_modify_time");
        }
        if (!"asc".equalsIgnoreCase(order) &&!"desc".equalsIgnoreCase(order)) {
            return Result.error("无效的排序方式，只支持 asc 或 desc");
        }
        if (beginTime != null&&endTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            theBeginTime = LocalDateTime.parse(beginTime, formatter);
            theEndTime = LocalDateTime.parse(endTime, formatter);

        }


        log.info("分页查询日记,参数{},{},{},{},{},{}排序列表:{},排序方式:{}",pageNo,pageSize,text,tag,beginTime,endTime,sortBy,order);

        PageResponse pageResponse = diaryService.queryDiaries(pageNo,pageSize,text,tag,theBeginTime,theEndTime,sortBy,order);
        return Result.success(pageResponse);


    }




}
