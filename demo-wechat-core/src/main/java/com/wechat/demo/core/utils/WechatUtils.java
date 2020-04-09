package com.wechat.demo.core.utils;

import com.wechat.demo.core.config.exception.OpenIdMissingException;
import com.wechat.demo.core.config.exception.SessionKeyTimeoutException;
import com.wechat.demo.core.constant.WechatConstants;
import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 统一封装微信小程序对外工具类
 * @Author zhangjw
 * @Date 2020/4/9 10:20
 */
@Slf4j
public class WechatUtils {

	private static final String WATERMARK = "watermark";

	private static final String APPID = "appid";

	/**
	 * 解密数据
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String appId, String encryptedData, String sessionKey, String iv) {
		String result = "";
		WechatAes aes = new WechatAes();
		byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey),
				Base64.decodeBase64(iv));
		if (null != resultByte && resultByte.length > 0) {
			result = new String(WechatPKCS7Encoder.decode(resultByte));
			JSONObject jsonObject = JSONObject.fromObject(result);
			String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
			if (!appId.equals(decryptAppid)) {
				result = "";
			}
		}

		return result;
	}

	/**
	 * 获取session中的sessionkey
	 * @param request
	 * @return
	 */
	public static String getSessionKey(HttpServletRequest request) {
		String sessionKey = (String) request.getSession().getAttribute(WechatConstants.sessionKey);
		if (StringUtils.isBlank(sessionKey)) {
			log.error("session中不存在sessionkey");
			throw new SessionKeyTimeoutException();
		}

		return sessionKey;
	}

	/**
	 * 获取openId
	 * @param request
	 * @return
	 */
	public static String getOpenId(HttpServletRequest request) {
		String openId = (String) request.getSession().getAttribute(WechatConstants.openId);
		if (StringUtils.isBlank(openId)) {
			log.error("session中不存在openId");
			throw new OpenIdMissingException();
		}

		return openId;
	}

	public static void main(String[] args) {
		String appId = "wx4f4bc4dec97d474b";
		String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
		String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
		String iv = "r7BXXKkLb8qrSNn05n0qiA==";
		System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
	}
}
