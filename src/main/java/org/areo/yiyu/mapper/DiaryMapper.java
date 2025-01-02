package org.areo.yiyu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.areo.yiyu.entity.Diary;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DiaryMapper {


    void insertDiary(Diary diary,Integer userId,LocalDateTime now);


    void diaryFakeDeleteByIds(Integer diaryIds, Integer userId,LocalDateTime now);


    void updateDiary(Diary diary, LocalDateTime now,Integer userId);


    List<Diary> listDiaryByCondition(String text, String tag, LocalDateTime beginTime, LocalDateTime endTime, Integer userId, String sortBy, String order);


    Diary getDiaryById(Integer diaryId);

    boolean isDiaryDeleted(Integer diaryId);
}
