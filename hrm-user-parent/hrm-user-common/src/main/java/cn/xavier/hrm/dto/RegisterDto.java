package cn.xavier.hrm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Zheng-Wei Shui
 * @date 12/25/2021
 */
@Data
public class RegisterDto {
    @NotBlank(message = "mobile can't be blank")
    private String mobile;
    @NotBlank(message = "password can't be blank")
    private String password;
    @NotBlank(message = "smsCode can't be blank")
    private String smsCode;
}
