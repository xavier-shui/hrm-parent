package cn.xavier.hrm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Zheng-Wei Shui
 * @date 12/30/2021
 */
@Data
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Integer type;
}
