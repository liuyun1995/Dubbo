package com.alibaba.dubbo.rpc.service;

//泛化服务接口
public interface GenericService {

    //泛化调用
    Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException;

}