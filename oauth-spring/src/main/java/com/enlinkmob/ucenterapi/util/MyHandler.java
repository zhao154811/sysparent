/**
 * @Title: MyHandler.java
 * @Package com.enlinkmob.uenterapi.util
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-28 下午3:11:24
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.enlinkmob.ucenterapi.exception.DataExistException;
import com.enlinkmob.ucenterapi.exception.ParamException;
import com.enlinkmob.ucenterapi.exception.ResponseException;
import com.enlinkmob.ucenterapi.model.ResultMessage;
import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Zhaowy
 * @ClassName: MyHandler
 * @Description: (异常处理类，用户输出json格式数据)
 * @date 2014-4-28 下午3:11:24
 */
@EnableWebMvc
@ControllerAdvice
public class MyHandler {

    @ExceptionHandler({OAuth2AccessDeniedException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processUnauthenticatedException(NativeWebRequest request, OAuth2AccessDeniedException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setStatus("307");
        jem.setMessage(e.getMessage());
        jem.setJsonResult(null);
        return jem;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, HttpRequestMethodNotSupportedException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setStatus("306");
        jem.setMessage(e.getMessage());
        jem.setJsonResult(null);

        return jem;
    }

    @ExceptionHandler({ParamException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, ParamException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setStatus(e.getExp_code());
        jem.setMessage(e.getError_desception());
        jem.setJsonResult(e.getExtendJson());


        return jem;
    }

    @ExceptionHandler({DataExistException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, DataExistException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setStatus(e.getExp_code());
        jem.setMessage(e.getError_desception());
        jem.setJsonResult(e.getExtendJson());

        return jem;
    }

    @ExceptionHandler({OAuth2Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, OAuth2Exception e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setStatus("308");
        jem.setMessage(e.getMessage());
        jem.setJsonResult(null);

        return jem;
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, MaxUploadSizeExceededException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setStatus("1024");
        jem.setMessage("file is too big");
        jem.setJsonResult(null);
        return jem;
    }

    @ExceptionHandler({MongoException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, MongoException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        JSONObject jb = JSONObject.parseObject(e.getMessage());
        jem.setStatus("309");
        jem.setMessage((String) jb.get("err"));
        jem.setJsonResult(null);
        return jem;
    }


    @ExceptionHandler({ResponseException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, ResponseException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        JSONObject jb = JSONObject.parseObject(e.getMessage());
        jem.setStatus(e.getExp_code());
        jem.setMessage(e.getError_desception());
        jem.setJsonResult(e.getExtendJson());
        return jem;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processHttpRequestMethodNotSupportedException(NativeWebRequest request, Exception e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setMessage(e.getMessage());
        jem.setJsonResult(null);
        jem.setStatus("311");
        return jem;
    }

    @ExceptionHandler({JSONException.class})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultMessage processJSONException(NativeWebRequest request, JSONException e) {

//	        ModelAndView mv = new ModelAndView();
//	        mv.addObject("error", e.getMessage());
//	        mv.setViewName("error/exception");
        ResultMessage jem = new ResultMessage();
        jem.setMessage(e.getMessage());
        jem.setJsonResult(null);
        jem.setStatus("312");
        return jem;
    }


}
