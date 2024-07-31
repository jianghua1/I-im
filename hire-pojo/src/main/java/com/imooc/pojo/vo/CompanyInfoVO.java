
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfoVO {

    private String companyId;
    private String companyName;
    private String shortName;
    private String logo;

    private String province;
    private String city;
    private String district;
    private String address;


    private String peopleSize;
    private String nature;
    private String industry;
    private String financStage;

    private String workTime;
    private String introduction;

    private String advantage;
    private String benefits;
    private String bonus;
    private String subsidy;

    private Integer reviewStatus;
    private String reviewReplay;

    private LocalDate commitDate;
    private String commitUserId;
    private String commitUser;
    private String commitMobile;
    private String authLetter;

    private String bizLicense;

    private LocalDate buildDate;
    private String registCapital;
    private String registPlace;
    private String legalRepresentative;
}
