package com.graduation.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {


    private String code;
    private String msg;
    private Object data;


    /**
     * 请求成功无参返回
     * @return
     */
    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    /**
     * 请求成功有参返回
     * @param obj
     * @return
     */
    public static Result success(Object obj){
        return new Result(Constants.CODE_200,"",obj);
    }

    /**
     * 请求失败自定义错误类型
     * @param code
     * @param msg
     * @return
     */
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    /**
     * 请求失败默认返状态码
     * @return
     */
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}
