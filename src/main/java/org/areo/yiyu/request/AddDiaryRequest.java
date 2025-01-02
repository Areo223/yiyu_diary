package org.areo.yiyu.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class AddDiaryRequest {

    @NotBlank(message = "日记内容不能为空")
    private String text;//文本

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
        diary.setTag(this.getTag());
        return diary;
    }
}
