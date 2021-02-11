package com.aili.baseservice.exceptionHandler;

import com.aili.commonutils.ExceptionUtil;
import com.aili.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 艾力
 * @date 2021/1/12 20:02
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody   //为了返回数据类型
    public R eroor(Exception exception){
        exception.printStackTrace();
        return R.error().message("执行了全局异常处理！");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody   //为了返回数据类型
    public R eroor(ArithmeticException exception){
        exception.printStackTrace();
        return R.error().message("执行了全局特定异常处理（ArithmeticException）！");
    }

    //自定义异常处理
    //特定异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody   //为了返回数据类型
    public R eroor(GuliException exception){
        //写到本地日志文件中去
        log.error(ExceptionUtil.getMessage(exception));
        exception.printStackTrace();
        return R.error().code(exception.getCode()).message(exception.getMsg());
    }
}
