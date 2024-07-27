package com.lld.im.common.model;

import lombok.Data;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
@Data
public class RequestBase {
    //因为我们的项目是一个sdk，需要供其他公司接入，所以请求中肯定有一个参数区分不同的客户，在绝大多数项目中没这个字段。
    private Integer appId;

    private String operater;

    private Integer clientType;

    private String imei;
}
