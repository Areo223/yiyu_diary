package org.areo.yiyu.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tag {

    private Integer id;
    @NotBlank
    private String tagContent;

    private Integer userId;

}
