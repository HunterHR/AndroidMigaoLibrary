
package com.szlanyou.common.validation.avalidations;

import com.szlanyou.common.validation.avalidations.util.ValidationUtil;

import android.content.Context;

/**
 * 
 * 校验执行者，可以调用父类的校验工具中的方法，该类会被其他的子类调用重写doValidate方法
 */
public abstract class ValidationExecutor extends ValidationUtil {

	/**
	 * 
	 * 这里做校验处理
	 * 
	 * @return 校验成功返回true 否则返回false
	 */
	public abstract boolean doValidate(Context context, String text);
	
}
