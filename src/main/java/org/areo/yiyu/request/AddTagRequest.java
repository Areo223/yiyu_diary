package org.areo.yiyu.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.areo.yiyu.entity.Tag;
import org.areo.yiyu.request.pack.TagPack;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTagRequest {

    @NotBlank(message = "不能添加空标签")
    private List<TagPack> tag;


    @NotEmpty(message = "关联的日记id不能为空")
    @Pattern(regexp = "[0-9]+$",message = "日记id应仅包含数字")
    @Min(value = 1,message = "日记id应为正数")
    private Integer diaryId;


    public List<Tag> getTag(){
        List<Tag> tags = new ArrayList<>();
        for(TagPack pack : this.tag){
            tags.add(pack.getTag());
        }
        return tags;
    }
}
