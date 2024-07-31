
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.co;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataDictionaryCO {

    private String id;
    private String type_code;
    private String type_name;
    private String item_key;
    private String item_value;
    private Integer sort;
    private String icon;
    private Boolean enable;

}
