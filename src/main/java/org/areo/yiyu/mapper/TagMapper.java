package org.areo.yiyu.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.areo.yiyu.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TagMapper {


    void insertTag(Tag tag, Integer userId, LocalDateTime now);


    Tag getTagByContent(String tagContent, Integer userId);


    void tagFakeDeleteByIds(Integer tagId, Integer userId, LocalDateTime now);


    ArrayList<Tag> getTagsByIds(List<Integer> tagIds, Integer userId);

    void updateTagAll(String tagContent, Integer tagId, Integer userId, LocalDateTime now);
}
