package com.halfsummer.service;


import com.halfsummer.common.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片 服务类
 */
public interface PictureService {
	public PictureResult uploadFile(MultipartFile uploadFile) throws Exception;
}
