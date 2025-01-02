package org.areo.yiyu.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DiaryTagMapper {

    void insertDiaryTagWithDiaryIdAndTagId(Integer diaryId, Integer tagId, Integer userId, LocalDateTime now);

    void diaryTagFakeDeleteById(Integer tagId, Integer diaryId,Integer userId, LocalDateTime now);


    boolean isTagOnlyUsedByThisDiary(Integer tagId, Integer diaryId, Integer userId);


    List<Integer> getDiaryIdsByTagId(Integer tagId, int userId);


    List<Integer> getTagIdsByDiaryId(Integer diaryId,Integer userId);
}
