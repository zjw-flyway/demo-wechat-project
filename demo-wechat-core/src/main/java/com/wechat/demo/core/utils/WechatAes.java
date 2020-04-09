package com.wechat.demo.core.utils;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.wechat.demo.core.config.exception.SessionKeyTimeoutException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 微信aes加密
 * @Author zhangjw
 * @Date 2020/4/9 10:15
 */
@Slf4j
public class WechatAes {

	//public static boolean initialized = false;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * AES解密
	 *
	 * @param content
	 *            密文
	 * @return
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchProviderException
	 */
	public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {
		//initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			log.error("WechatAes.decrypt失败，原因为：", e);
			//解密失败的话，就统一往前端报错吧
			throw new SessionKeyTimeoutException();
		}
	}

	//public static void initialize() {
	//	if (initialized)
	//		return;
	//	Security.addProvider(new BouncyCastleProvider());
	//	initialized = true;
	//}

	/**
	 * 生成VI
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}
}
