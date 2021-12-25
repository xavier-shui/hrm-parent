package cn.xavier.hrm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Zheng-Wei Shui
 * @date 12/25/2021
 */
@Data
public class SmsCodeDto {
    @NotBlank(message = "imageCode can't be blank")
    private String imageCode;
    @NotBlank(message = "imageCodeKey can't be blank")
    private String imageCodeKey;
    @NotBlank(message = "mobile can't be blank")
    private String mobile;
}
