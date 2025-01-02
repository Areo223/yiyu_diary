package org.areo.yiyu.service;

import org.areo.yiyu.exception.DiaryNotFoundException;
import org.areo.yiyu.request.AddDiaryRequest;
import org.areo.yiyu.request.UpdateDiaryRequest;
import org.areo.yiyu.response.PageResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface DiaryService {
    void addDiary(AddDiaryRequest addDiaryRequest);

    void deleteDiary(List<Integer> diaryIds);

    void updateDiary(UpdateDiaryRequest updateDiaryRequest) throws DiaryNotFoundException;

    PageResponse queryDiaries(Integer pageNo, Integer pageSize, String text, String tag, LocalDateTime beginTime, LocalDateTime endTime, String sortBy, String order);
}
