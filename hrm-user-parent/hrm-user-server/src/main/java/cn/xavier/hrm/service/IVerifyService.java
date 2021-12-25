package cn.xavier.hrm.service;

import cn.xavier.hrm.dto.SmsCodeDto;
import cn.xavier.hrm.util.AjaxResult;

/**
 * @author Zheng-Wei Shui
 * @date 12/25/2021
 */
public interface IVerifyService {
    AjaxResult imageCode(String key);

    AjaxResult sendSmsCode(SmsCodeDto dto);
}
