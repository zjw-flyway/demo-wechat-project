package com.standard.demo.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @Description 删除标识，1为正常，2为删除
 * @Author zhangjw
 * @Date 2020/3/17 14:08
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum DelFlag {

    NORMAL(1),

    DELETE(2);

    Integer code;
}
