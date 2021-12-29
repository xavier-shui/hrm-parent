package cn.xavier.hrm.vo;

import lombok.Data;

/**
 * @author Zheng-Wei Shui
 * @date 12/29/2021
 */
@Data
public class UserInfo {
    private Long tenantId;
	private String tenantName;
	private Long userId;
	private String userName;
}
