
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchJobsVO {

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

    private UsersVO usersVO;
    private CompanyInfoVO companyInfoVO;

    // 候选人收藏职位的时间
    private LocalDateTime collectTime;
}
