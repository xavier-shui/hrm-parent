package cn.xavier.hrm.dto;

import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.domain.Tenant;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
@Data
public class SettlementDto {
    @Valid // 对bean对象校验, 不为null时才校验
    private Employee employee;
    @NotNull(message = "套餐必须选择")
    private Long mealId;
    private Tenant tenant;
}
