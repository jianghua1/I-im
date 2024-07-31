
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCompanyBO {

    @NotBlank
    private String hrUserId;
    private String realname;
    private String hrMobile;
    @NotBlank
    private String companyId;
    private String authLetter;

//    以下在审核的时候使用到
    private Integer reviewStatus;
    private String reviewReplay;

}
