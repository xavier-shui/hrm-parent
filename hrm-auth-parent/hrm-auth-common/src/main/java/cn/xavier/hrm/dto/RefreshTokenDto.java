package cn.xavier.hrm.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Zheng-Wei Shui
 * @date 12/30/2021
 */
@Data
public class RefreshTokenDto {
    @NotBlank
    private String refreshToken;
    @NotNull
    private Integer type;
}
