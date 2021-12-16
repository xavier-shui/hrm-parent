package cn.xavier.hrm.constant;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
public interface LoginUserTypeConstant {
    /**
     * 平台普通员工
     */
    Integer NORMAL_EMPLOYEE = 1;
    /**
     * 平台客服人员
     */
    Integer CUSTOMER_SERVICE = 2;
    /**
     * 平台管理员
     */
    Integer PLATFORM_ADMIN = 3;
    /**
     * 机构员工
     */
    Integer TENANT_EMPLOYEE = 4;
    /**
     * 机构管理员或其他
     */
    Integer TENANT_ADMIN = 5;
}
