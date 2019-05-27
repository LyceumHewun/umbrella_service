package cc.lyceum.umbrella.config;

import cc.lyceum.umbrella.ResponseMessage;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;


/**
 * @author lamze
 * @version 1.0
 * @date 2019-03-07 14:26
 */

@ControllerAdvice
@ResponseBody
@ResponseStatus(code = HttpStatus.OK)
public class ExceptionHandle {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseMessage handle2(MethodArgumentNotValidException e) {
        e.printStackTrace();

        final ArrayList<String> list = Lists.newArrayList();
        e.getBindingResult().getAllErrors().forEach(i -> list.add(i.getDefaultMessage()));
        return ResponseMessage.createByError(list);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseMessage handle3(BindException e) {
        e.printStackTrace();

        final ArrayList<String> list = Lists.newArrayList();
        e.getBindingResult().getAllErrors().forEach(i -> list.add(i.getDefaultMessage()));
        return ResponseMessage.createByError(list);
    }

    @ExceptionHandler(Exception.class)
    public ResponseMessage handle(Exception e) {
        e.printStackTrace();

//        return ResponseMessage.createByErrorMessage(e.toString());
        return ResponseMessage.createByErrorMessage(e.getMessage());
    }
}
