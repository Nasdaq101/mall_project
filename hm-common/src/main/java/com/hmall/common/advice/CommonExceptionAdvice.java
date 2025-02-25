package com.hmall.common.advice;

import com.hmall.common.domain.R;
import com.hmall.common.exception.BadRequestException;
import com.hmall.common.exception.CommonException;
import com.hmall.common.exception.DbException;
import com.hmall.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import java.net.BindException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

    @ExceptionHandler(DbException.class)
    public Object handleDbException(DbException e) {
        log.error("mysql database exception -> ", e);
        return processResponse(e);
    }

    @ExceptionHandler(CommonException.class)
    public Object handleBadRequestException(CommonException e) {
        log.error("exception -> {} , reason：{}  ",e.getClass().getName(), e.getMessage());
        log.debug("", e);
        return processResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("|"));
        log.error("validation exception -> {}", msg);
        log.debug("", e);
        return processResponse(new BadRequestException(msg));
    }
    @ExceptionHandler(BindException.class)
    public Object handleBindException(BindException e) {
        log.error("handle bind exception ->BindException， {}", e.getMessage());
        log.debug("", e);
        return processResponse(new BadRequestException("wrong format"));
    }

    @ExceptionHandler(NestedServletException.class)
    public Object handleNestedServletException(NestedServletException e) {
        log.error("parameter exception -> NestedServletException，{}", e.getMessage());
        log.debug("", e);
        return processResponse(new BadRequestException("wrong format"));
    }

    @ExceptionHandler(Exception.class)
    public Object handleRuntimeException(Exception e) {
        log.error("other exception uri : {} -> ", WebUtils.getRequest().getRequestURI(), e);
        return processResponse(new CommonException("fwq exception", 500));
    }

    private ResponseEntity<R<Void>> processResponse(CommonException e){
        return ResponseEntity.status(e.getCode()).body(R.error(e));
    }
}
