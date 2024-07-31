
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.vo;

import com.imooc.pojo.DataDictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPointsVO {

    private List<DataDictionary> advantageList;
    private List<DataDictionary> benefitsList;
    private List<DataDictionary> bonusList;
    private List<DataDictionary> subsidyList;
}
