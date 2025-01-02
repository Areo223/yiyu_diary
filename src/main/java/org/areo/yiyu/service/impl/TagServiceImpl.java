package org.areo.yiyu.service.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.yiyu.mapper.DiaryMapper;
import org.areo.yiyu.mapper.DiaryTagMapper;
import org.areo.yiyu.mapper.TagMapper;
import org.areo.yiyu.entity.Tag;
import org.areo.yiyu.request.AddTagRequest;
import org.areo.yiyu.request.UpdateTagAllRequest;
import org.areo.yiyu.service.TagService;
import org.areo.yiyu.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TagServiceImpl implements TagService {


    private final TagMapper tagMapper;
    private  final DiaryTagMapper diaryTagMapper;
    private  final DiaryMapper diaryMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTag(@NotBlank AddTagRequest addTagRequest) {

        Integer userId = UserHolder.getUser().getId();

        List<Tag> tags = addTagRequest.getTag();
        for (Tag tag : tags) {
            //判断tag是否已经存在
            Tag isAlreadyExist = tagMapper.getTagByContent(tag.getTagContent(), UserHolder.getUser().getId());
            if (isAlreadyExist != null) {//已经存在直接建立联系
                diaryTagMapper.insertDiaryTagWithDiaryIdAndTagId(addTagRequest.getDiaryId(), isAlreadyExist.getId(), userId, LocalDateTime.now());
            }
            tagMapper.insertTag(tag,UserHolder.getUser().getId(),LocalDateTime.now());
            diaryTagMapper.insertDiaryTagWithDiaryIdAndTagId(addTagRequest.getDiaryId(),tag.getId(), userId, LocalDateTime.now());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(List<Integer> tagIds, Integer diaryId) {
        int userId = UserHolder.getUser().getId();

        for (Integer tagId : tagIds) {
            tagMapper.tagFakeDeleteByIds(tagId, userId, LocalDateTime.now());
            diaryTagMapper.diaryTagFakeDeleteById(tagId,diaryId,userId,LocalDateTime.now());
        }

    }

    @Override
    public void updateTagAll(UpdateTagAllRequest updateTagAllRequest) {
        tagMapper.updateTagAll(updateTagAllRequest.getTagContent(),updateTagAllRequest.getId(),UserHolder.getUser().getId(),LocalDateTime.now());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTagAll(List<Integer> tagIds) {
        int userId = UserHolder.getUser().getId();

        // 存储需要删除的标签 ID
        List<Integer> tagsToDelete = new ArrayList<>();

        for (Integer tagId : tagIds) {

            //获取tag对应的所有diary
            List<Integer> diaryIdByTagId = diaryTagMapper.getDiaryIdsByTagId(tagId,userId);
            for (Integer releventTagId :diaryTagMapper.getTagIdsByDiaryId(tagId,userId)) {
                tagsToDelete.add(releventTagId);
            }

            for (Integer diaryId : diaryIdByTagId) {
                //删除diary_tag关系
                diaryTagMapper.diaryTagFakeDeleteById(tagId,diaryId,userId,LocalDateTime.now());

                //查找删除的diary对应的tag是否只有他关联,若是则删除


                //删除tag
                tagMapper.tagFakeDeleteByIds(tagId, userId, LocalDateTime.now());
                //删除diary
                diaryMapper.diaryFakeDeleteByIds(diaryId, userId, LocalDateTime.now());
            }

        }
        // 检查其他标签是否仅与已删除的日记关联
        for (Integer tagId : tagsToDelete) {
            boolean isTagOnlyUsedByDeletedDiaries = true;
            List<Integer> diaryIdsByTagId = diaryTagMapper.getDiaryIdsByTagId(tagId, userId);
            for (Integer diaryId : diaryIdsByTagId) {
                // 如果存在与该标签关联的日记未被删除，则该标签不应被删除
                if (!diaryMapper.isDiaryDeleted(diaryId)) {
                    isTagOnlyUsedByDeletedDiaries = false;
                    break;
                }
            }

            // 如果该标签仅与已删除的日记关联，则将其删除
            if (isTagOnlyUsedByDeletedDiaries) {
                tagMapper.tagFakeDeleteByIds(tagId, userId, LocalDateTime.now());
            }
        }
    }
}
