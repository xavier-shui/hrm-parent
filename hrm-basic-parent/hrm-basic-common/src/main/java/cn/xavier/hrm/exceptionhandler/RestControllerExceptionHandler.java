package cn.xavier.hrm.exceptionhandler;

import cn.xavier.hrm.exception.HrmException;
import cn.xavier.hrm.util.AjaxResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zheng-Wei Shui
 * @date 12/16/2021
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public AjaxResult exceptionHandler(RuntimeException e) {
        return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
    }

    /**
     * 全局异常处理，返回前端格式相同
     *
     * @param e e
     * @return the ajax result
     */
    @ExceptionHandler(HrmException.class)
    public AjaxResult exceptionHandler(HrmException e) {
        return AjaxResult.me()
                .setSuccess(false)
                .setMessage("业务异常: " + e.getMessage())
                .setCode(e.getCode());
    }


    /**
     * JSR303 校验
     *
     * @param e e
     * @return the ajax result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // 异常信息重新封装map
        Map<String, String> errorMap = fieldErrors.stream().collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage));
        return AjaxResult.me()
                .setSuccess(false)
                .setMessage(errorMap.toString())
                .setCode(HttpStatus.BAD_REQUEST.value());
    }
}
