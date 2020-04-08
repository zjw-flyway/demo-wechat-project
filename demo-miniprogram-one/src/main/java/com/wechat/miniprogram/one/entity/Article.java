package com.standard.demo.webapp.one.entity;

import com.standard.demo.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @Description
 * @Author zhangjw
 * @Date 2020/3/17 15:52
 */
@Data
@FieldDefaults(level = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ApiModel("文章")
public class Article extends BaseEntity {

    /**
     * 文章内容
     */
    @ApiModelProperty("文章内容")
    String content;
}
