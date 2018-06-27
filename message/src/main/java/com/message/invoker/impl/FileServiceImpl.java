package com.message.invoker.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.message.invoker.IFileRemoteService;
import com.message.invoker.IFileService;
import com.message.spring.SpringManager;

@Service
public class FileServiceImpl implements IFileService {

	@Override
	public File getFileByKey(String key) {
		return getFileRemoteService().getFileByKey(key);
	}

	public IFileRemoteService getFileRemoteService() {
		return SpringManager.getBeanByType(IFileRemoteService.class);
	}
}
