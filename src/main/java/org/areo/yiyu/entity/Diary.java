package org.areo.yiyu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Diary {

    private Integer id;//主键id
    private Integer userId;//用户id
    private String text;//日记文本
    private LocalDateTime createTime;//创建时间
    private LocalDateTime lastModifyTime;//最近修改时间
    private List<Tag> tag;//标签
    private Short status;//删除标识
}
