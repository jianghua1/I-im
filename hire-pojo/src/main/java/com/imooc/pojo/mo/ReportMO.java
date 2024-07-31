
//	此资源由 58学课资源站 收集整理
//	想要获取完整课件资料 请访问：58xueke.com
//	百万资源 畅享学习
package com.imooc.pojo.mo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document("report_job")
public class ReportMO {

    @Id
    private String id;

    @Field("job_id")
    private String jobId;

    @Field("job_name")
    private String jobName;

    @Field("company_name")
    private String companyName;

    @Field("report_user_id")
    private String reportUserId;

    @Field("report_user_name")
    private String reportUserName;

    @Field("report_reason")
    private String reportReason;

    @Field("deal_status")
    private Integer dealStatus;

    @Field("remark")
    private String remark;

    @Field("created_time")
    private LocalDateTime createdTime;
    @Field("updated_time")
    private LocalDateTime updatedTime;

}
