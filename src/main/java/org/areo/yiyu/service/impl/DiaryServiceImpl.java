package org.areo.yiyu.service.impl;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.exception.DiaryNotFoundException;
import org.areo.yiyu.mapper.DiaryMapper;
import org.areo.yiyu.mapper.DiaryTagMapper;
import org.areo.yiyu.mapper.TagMapper;
import org.areo.yiyu.entity.Diary;
import org.areo.yiyu.request.AddDiaryRequest;
import org.areo.yiyu.request.UpdateDiaryRequest;
import org.areo.yiyu.response.PageResponse;
import org.areo.yiyu.entity.Tag;
import org.areo.yiyu.service.DiaryService;
import org.areo.yiyu.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DiaryServiceImpl implements DiaryService {


    private final DiaryMapper diaryMapper;
    private final TagMapper tagMapper;
    private final DiaryTagMapper diaryTagMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)//事务化处理
    public void addDiary(AddDiaryRequest addDiaryRequest) {


        //转换成diary类处理
        Diary diary = addDiaryRequest.getDiary();
        //插入日记
        diaryMapper.insertDiary(diary,UserHolder.getUser().getId(),LocalDateTime.now());

        Integer userId = UserHolder.getUser().getId();


        for (Tag tag:diary.getTag()){//对每个tag都执行一次插入和相关表建立
            Tag isTagHad = tagMapper.getTagByContent(tag.getTagContent(),userId);

            if (isTagHad == null){

                tagMapper.insertTag(tag,userId,LocalDateTime.now());//如果tag不存在则插入tag
                diaryTagMapper.insertDiaryTagWithDiaryIdAndTagId(diary.getId(),tag.getId(),userId,LocalDateTime.now());
            }else {
                diaryTagMapper.insertDiaryTagWithDiaryIdAndTagId(diary.getId(),isTagHad.getId(), userId, LocalDateTime.now());//如果tag存在则直接建立关联
            }

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)//事务化处理
    public void deleteDiary(List<Integer> diaryIds) {
        Integer userId = UserHolder.getUser().getId();

        for (Integer diaryId:diaryIds){


            //从diary_tag表中获取diary对应的tag
            List<Integer> tagIds = diaryTagMapper.getTagIdsByDiaryId(diaryId,userId);

            //在diary表中假删除
            diaryMapper.diaryFakeDeleteByIds(diaryId,userId,LocalDateTime.now());


            for (Integer tagId:tagIds){
                //检查该tag是否只由被删除的diary使用
                boolean isOnlyUsedByThisDiary = diaryTagMapper.isTagOnlyUsedByThisDiary(tagId,diaryId,userId);
                if (isOnlyUsedByThisDiary){//只由它使用则删除
                    log.info("tagid:{}只由被删除的diary使用,执行删除",tagId);
                    tagMapper.tagFakeDeleteByIds(tagId,userId,LocalDateTime.now());
                }
            }

            //在diary_tag表中进行假删除
            for (Integer tagId:tagIds){
                diaryTagMapper.diaryTagFakeDeleteById(tagId,diaryId,userId,LocalDateTime.now());
            }


        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDiary(UpdateDiaryRequest updateDiaryRequest) throws DiaryNotFoundException{

        if (updateDiaryRequest.getId() == null ) {
            throw new DiaryNotFoundException("修改日记:未找到日记");
        }

        Diary diary = updateDiaryRequest.getDiary();
        Integer userId = UserHolder.getUser().getId();

        try {
            //先更新diary表
            diaryMapper.updateDiary(diary,LocalDateTime.now(),userId);

            //然后获取当前日记原有的tag列表

            List<Integer> originalTagsIds = diaryTagMapper.getTagIdsByDiaryId(diary.getId(),userId);
            List<Tag> originalTags = tagMapper.getTagsByIds(originalTagsIds,userId);

            //获取当前日记更改后的tag列表
            List<Tag> newTags = diary.getTag();

            //处理0->1的tag
            for (Tag newTag : newTags) {
                boolean found = false;
                for (Tag originalTag : originalTags) {
                    if (newTag.getTagContent().equals(originalTag.getTagContent())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    tagMapper.insertTag(newTag, userId, LocalDateTime.now());
                    diaryTagMapper.insertDiaryTagWithDiaryIdAndTagId(diary.getId(),newTag.getId(), userId, LocalDateTime.now());
                }
            }

            //处理1->0的tag
            for (Tag originalTag : originalTags) {
                boolean stillExists = false;
                for (Tag newTag : newTags) {
                    if (originalTag.getTagContent().equals(newTag.getTagContent())) {
                        stillExists = true;
                        break;
                    }
                }
                if (!stillExists) {
                    tagMapper.tagFakeDeleteByIds(originalTag.getId(), userId, LocalDateTime.now());
                    diaryTagMapper.diaryTagFakeDeleteById(originalTag.getId(), diary.getId(),userId,LocalDateTime.now());
                }
            }
            log.info("日记更新成功,日记id:{}", diary.getId());
        }catch (Exception e){
            log.error("日记更新失败,日记id:{}", diary.getId());
            throw e;
        }

    }

    @Override
    @Transactional
    public PageResponse queryDiaries(Integer pageNo, Integer pageSize, String text, String tag, LocalDateTime beginTime, LocalDateTime endTime, String sortBy, String order ) {

        Integer userId = UserHolder.getUser().getId();

        //设置分页参数
        PageHelper.startPage(pageNo, pageSize);

        //执行查询
        List<Diary> diaryList = diaryMapper.listDiaryByCondition(text,tag,beginTime,endTime,userId,sortBy,order);

        //处理每个日记的标签
        for (Diary diary:diaryList){
            diary.setTag(tagMapper.getTagsByIds(diaryTagMapper.getTagIdsByDiaryId(diary.getId(),userId), userId));
        }
        PageInfo<Diary> pageInfo = new PageInfo<>(diaryList);

        PageResponse pageResponse = new PageResponse(pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());

        return pageResponse;
    }

}
