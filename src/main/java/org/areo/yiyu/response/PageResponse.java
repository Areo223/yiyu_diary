package org.areo.yiyu.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResponse<T> {
    private List<T> content;//当前页内容
    private Integer totalPages;//总页数
    private Long totalElements;//总数据数



}
