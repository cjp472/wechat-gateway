/*
 * @Title: UUID.java
 * @Package com.huiquan.common.util
 * @Description: 
 * @author JiangGuoSheng 466209673@qq.com
 * @date 2014年7月23日 下午3:36:19
 * @version V4.0
 *
 * Modification History:
 * Date         Author      Version     Description
 * --------------------------------------------------------------
 * 2014年7月23日
 */
package com.wangxiaobao.wechatgateway.utils;

import java.util.UUID;

/**
 * <p>
 * </p>
 * 
 * @author JiangGuoSheng Create at:2014年7月23日 下午3:36:19
 */
public class CreateUUID {

	public String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉"-"符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 获得一个去掉"-"符号的UUID
	 * 
	 * @return
	 */
	public static String getUuid() {
		String s = UUID.randomUUID().toString();
		// 去掉"-"符号
		return s.replace("-", "");
	}

	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public  String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			if (i % 2 == 0) {
				ss[i] = getUuid();
			} else {
				ss[i] = getUUID();
			}
		}
		return ss;
	}

	public static void main(String[] args) {
		// System.out.println(getUUID());
	}

}
