package org.areo.yiyu.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTagAllRequest {

    @NotEmpty(message = "修改的标签id不能为空")
    @Pattern(regexp = "[0-9]+$",message = "标签id应仅包含数字")
    @Min(value = 1,message = "标签id应为正数")
    private Integer id;
    @NotBlank
    private String tagContent;

}
