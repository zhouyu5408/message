package com.message.invoker;

import java.io.File;

/**
 * 调用远程服务器的接口
 * 
 * @Date: 2017年12月29日 上午11:09:14 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public interface IFileRemoteService {
	/**
	 * 远程必须实现的接口，用于从邮件服务器获取file
	 * 
	 * @param key
	 * @return
	 */
	File getFileByKey(String key);
}
