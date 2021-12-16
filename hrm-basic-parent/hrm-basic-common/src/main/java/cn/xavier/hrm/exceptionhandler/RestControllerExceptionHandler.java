package cn.xavier.hrm.exceptionhandler;

import cn.xavier.hrm.util.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {


    @ExceptionHandler(ArithmeticException.class)
    public AjaxResult mathHandler(ArithmeticException e) {
        return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
    }

    /**
     * 全局异常处理，返回前端格式相同
     *
     * @param e e
     * @return the ajax result
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult exceptionHandler(RuntimeException e) {
        return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
    }
}
