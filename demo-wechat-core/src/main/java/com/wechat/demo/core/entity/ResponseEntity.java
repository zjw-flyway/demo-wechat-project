package com.wechat.demo.core.entity;

import java.io.Serializable;

import com.wechat.demo.core.constant.CommonConstants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 响应信息主体
 */
@ApiModel(value = "ResponseEntity", description = "响应对象")
@Builder
@ToString
@Accessors(chain = true)
@Data
@AllArgsConstructor
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "响应标记")
	@Builder.Default
	private int code = CommonConstants.SUCCESS;

	@ApiModelProperty(value = "响应信息")
	@Builder.Default
	private String msg = "success";

	@ApiModelProperty(value = "响应数据")
	private T data;

	public ResponseEntity() {
		super();
	}

	public ResponseEntity(T data) {
		super();
		this.data = data;
	}

	public ResponseEntity(String msg, int code) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResponseEntity(T data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public ResponseEntity(T data, int code) {
		super();
		this.data = data;
		this.code = code;
	}

	public ResponseEntity(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = CommonConstants.FAIL;
	}

	public static <T> ResponseEntity<T> of() {
		return new ResponseEntity<>();
	}

	public static <T> ResponseEntity<T> of(T data) {
		return new ResponseEntity<>(data);
	}

	public static <T> ResponseEntity<T> of(String msg, int code) {
		return new ResponseEntity<>(msg, code);
	}

	public static <T> ResponseEntity<T> of(T data, String msg) {
		return new ResponseEntity<>(data, msg);
	}

}
