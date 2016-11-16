package com.reborn.dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 *
 * ajax 请求的返回类型封装JSON结果
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResult<T> implements Serializable {
	public static final int STATUS_SUCCESS=200;
	public static final int STATUS_ERROR=500;

	private static final long serialVersionUID = -4185151304730685014L;

	private int status;

    private T data;

    private String message="";

    public BaseResult(int status, String message) {
    	
        this.status = status;
        this.message = message;
    }

    
    public BaseResult(int status, T data,String message) {
    	this.status = status;
        this.data = data;
        this.message=message;
    }
    
    public BaseResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    
    
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "BaseResult [status=" + status + ", data=" + data + ", message=" + message + "]";
	}

}
