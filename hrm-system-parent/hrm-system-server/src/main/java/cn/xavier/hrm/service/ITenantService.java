package cn.xavier.hrm.service;

import cn.xavier.hrm.domain.Tenant;
import cn.xavier.hrm.dto.SettlementDto;
import cn.xavier.hrm.query.TenantQuery;
import cn.xavier.hrm.util.AjaxResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengwei-shui
 * @since 2021-12-15
 */
public interface ITenantService extends IService<Tenant> {

    /**
     * 带高级查询的分页列表
     *
     * @param page  page
     * @param query query
     * @return the page
     */
    Page<Tenant> queryTenantList(Page<Tenant> page, TenantQuery query);

    AjaxResult settlement(SettlementDto dto);
}
