package org.areo.yiyu.request.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.areo.yiyu.entity.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPack {

    private Integer id;
    private String tagContent;
    public Tag getTag(){

        Tag newTag = new Tag();
        newTag.setId(this.id);
        newTag.setTagContent(this.tagContent);
        return newTag;
    }

}
