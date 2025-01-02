package org.areo.yiyu.exception;



import org.areo.yiyu.response.Result;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DiaryNotFoundException.class)
    public ResponseEntity<String> handleDiaryNotFoundException(DiaryNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("方法参数不正确",e);
        return Result.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

}
