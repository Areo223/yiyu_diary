package org.areo.yiyu.service;

import org.areo.yiyu.entity.Tag;
import org.areo.yiyu.request.AddTagRequest;
import org.areo.yiyu.request.UpdateTagAllRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    void addTag(AddTagRequest addTagRequest);

    void deleteTag(List<Integer> tags, Integer diaryId);

    void updateTagAll(UpdateTagAllRequest tag);

    void deleteTagAll(List<Integer> tagIds);
}
