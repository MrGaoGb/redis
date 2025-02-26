package com.xbqx.mrgao.redisopt.config;

import com.xbqx.mrgao.redisopt.exception.BizException;
import com.xbqx.mrgao.redisopt.pojo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mr.Gao
 * @date 2023/4/4 14:50
 * @apiNote:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired(required = false)
    private HttpServletRequest request;

    /**
     * 异常响应
     *
     * @param handler
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseData<Object> exception(Exception handler) {
        log.error("请求接口: {}", Optional.ofNullable(request).map(HttpServletRequest::getRequestURI).orElse("无法获取"));
        if (handler instanceof BizException) {
            BizException exception = (BizException) handler;
            log.error("请求处理异常: errCode:{} errMsg:{}", exception.getErrCode(), exception.getErrMsg());
            return new ResponseData<>(exception.getErrCode(), exception.getErrMsg());
        }
        log.error("系统错误:", handler);
        return new ResponseData<>("500", handler.getMessage());
    }

    /**
     * 拦截不支持媒体类型异常
     *
     * @author suixince
     * @date 2020/12/16 14:26
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseData<?> httpMediaTypeNotSupport(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
        log.error("参数格式传递异常，具体信息为：{}", httpMediaTypeNotSupportedException.getMessage());
        return new ResponseData<>("403", "请求方式不支持");
    }

    /**
     * 404找不到资源
     *
     * @author suixince
     * @date 2020/12/16 14:58
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseData<?> notFound(NoHandlerFoundException e) {
        log.error("参数错误", e);
        return new ResponseData<>("404", "请求URL非法");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        return new ResponseEntity<>("Unsupported media type in Accept header.", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * 参数校验
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String fieldErrorMessage = Optional.of(result)
                .map(Errors::getFieldErrors).orElse(Collections.emptyList()).stream()
                .map(error -> String.format("'%s' %s;", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining());
        String globalErrorMessage = Optional.of(result)
                .map(Errors::getGlobalErrors).orElse(Collections.emptyList()).stream()
                .map(error -> String.format("%s;", error.getDefaultMessage()))
                .collect(Collectors.joining());
        log.error("参数错误", ex);
        //根据系统自定义 返回码
        return new ResponseData<>("400", fieldErrorMessage + globalErrorMessage);
    }
}
