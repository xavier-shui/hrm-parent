package cn.xavier.hrm.dto;

import cn.xavier.hrm.domain.Employee;
import cn.xavier.hrm.domain.Tenant;
import lombok.Data;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
@Data
public class SettlementDto {
    private Employee employee;
    private Long mealId;
    private Tenant tenant;
}
