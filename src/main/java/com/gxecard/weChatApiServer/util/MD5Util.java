/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gxecard.weChatApiServer.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 功能说明:MD5签名工具类
 *
 *
 */
@Slf4j
public class MD5Util {

	/**
	 * 私有构造方法,将该工具类设为单例模式.
	 */
	private MD5Util() {
	}

	private static final String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String encode(String password) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = md5.digest(password.getBytes("utf-8"));
			String passwordMD5 = byteArrayToHexString(byteArray);
			return passwordMD5;
		} catch (Exception e) {
			log.error(e.toString());
		}
		return password;
	}

	public static String encode(String password, String enc) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = md5.digest(password.getBytes(enc));
			String passwordMD5 = byteArrayToHexString(byteArray);
			return passwordMD5;
		} catch (Exception e) {
			log.error(e.toString());
		}
		return password;
	}

	private static String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		for (byte b : byteArray) {
			sb.append(byteToHexChar(b));
		}
		return sb.toString();
	}

	private static Object byteToHexChar(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hex[d1] + hex[d2];
	}


	/**
	 * sha256_HMAC加密
	 * @param message 消息
	 * @param secret  秘钥
	 * @return 加密后字符串
	 */
	public static String sha256_HMAC(String message, String secret) {
		String hash = "";
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
			hash = byteArrayToHexString(bytes);
			log.info(hash);
		} catch (Exception e) {
			log.info("Error HmacSHA256 ===========" + e.getMessage());
		}
		return hash;
	}

}
