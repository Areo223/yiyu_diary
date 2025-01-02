package org.areo.yiyu.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.areo.yiyu.entity.Diary;
import org.areo.yiyu.entity.Tag;
import org.areo.yiyu.request.pack.TagPack;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDiaryRequest {

    @NotEmpty(message = "关联的日记id不能为空")
    @Pattern(regexp = "[0-9]+$",message = "日记id应仅包含数字")
    @Min(value = 1,message = "日记id应为正数")
    private Integer id;//主键id

    @NotBlank(message = "日记内容不能为空")
    private String text;//日记文本
    private List<TagPack> tag;//标签
    
    public List<Tag> getTag(){
        List<Tag> tags = new ArrayList<>();
        for(TagPack pack : tag){
            tags.add(pack.getTag());
        }
        return tags;
    }
    public Diary getDiary(){
        Diary diary = new Diary();
        diary.setText(this.text);
        diary.setId(this.id);
        diary.setTag(this.getTag());
        return diary;
    }
}
