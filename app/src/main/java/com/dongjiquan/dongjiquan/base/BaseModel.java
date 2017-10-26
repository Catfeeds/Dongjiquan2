package com.dongjiquan.dongjiquan.base;

import java.io.Serializable;

/**
 * Created by wlx on 2017/9/28.
 */

public class BaseModel<T> implements Serializable {
    public int code;
    public String msg;
    public T data;
    public boolean success(){
        return code==0;
    }
}
