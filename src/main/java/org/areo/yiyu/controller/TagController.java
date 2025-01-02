package org.areo.yiyu.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.areo.yiyu.request.UpdateTagAllRequest;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.request.AddTagRequest;
import org.areo.yiyu.response.Result;
import org.areo.yiyu.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Slf4j
@RequestMapping("/diary")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@io.swagger.v3.oas.annotations.tags.Tag(name="标签控制器",description = "标签CRUD接口")
public class TagController {

    private final TagService tagService;

    //新增标签
    @PostMapping("/tag")
    @Operation(summary = "新增标签接口",description = "在指定的日记上新增标签,支持批量新增," +
            "需传入要添加的tag和对应日记的id:diaryId")
    public Result addTag(@RequestBody AddTagRequest addTagRequest) {

        log.info("在日记id: {},新增标签: {} ",addTagRequest.getDiaryId(),addTagRequest.getTag());
        tagService.addTag(addTagRequest);
        return Result.success();
    }

    //批量删除标签
    @DeleteMapping("/tag/{tagIds}")
    @Operation(summary = "删除标签接口",description = "删除指定日记上的标签,支持批量删除," +
            "需传入要删除的标签id和对应日记的id:diaryId")
    public Result deleteTag(@PathVariable List<Integer> tagIds, Integer diaryId) {
        log.info("在日记id: {},删除标签id:{}",diaryId,tagIds);
        tagService.deleteTag(tagIds,diaryId);
        return Result.success();
    }

    //更改标签
    @PutMapping("/tagAll")
    @Operation(summary = "更改标签内容接口",description = "更改一个标签的内容,从而使所有原有关联此标签的日记上的标签内容都改动," +
            "需传入更改的tag对象,重点传入tag的id和tagContent")
    public Result updateTagAll(@RequestBody UpdateTagAllRequest updateTagAllRequest) {
        log.info("更改全部标签: {}",updateTagAllRequest);
        tagService.updateTagAll(updateTagAllRequest);
        return Result.success();
    }

    //删除标签及其下的日记
    @DeleteMapping("/tagAll/{tagIds}")
    @Operation(summary = "删除标签及关联日记接口",description = "删除标签,以及所有关联该标签的日记," +
            "需传入要删除的tag的id列表:tagIds")
    public Result deleteTagAll(@PathVariable List<Integer> tagIds) {
        log.info("删除标签:{}以及标签所有的日记",tagIds);
        tagService.deleteTagAll(tagIds);
        return Result.success();
    }
}
