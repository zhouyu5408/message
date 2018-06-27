package com.message.invoker;

import java.io.File;

/**
 * 本地调用远程服务器接口
 * 
 * @author hasee
 *
 */
public interface IFileService {
	/**
	 * 根据key获取File
	 * 
	 * @param key
	 * @return
	 */
	File getFileByKey(String key);
}
