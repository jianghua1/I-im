
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditJobBO {

    private String id;
    private String hrId;
    private String companyId;
    private String jobName;
    private String jobType;
    private String expYears;
    private String edu;
    private Integer beginSalary;
    private Integer endSalary;
    private Integer monthlySalary;
    private String jobDesc;
    private String tags;
    private String city;
    private String address;

}
