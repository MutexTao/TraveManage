package com.halfsummer.controller.backend;


import com.halfsummer.common.PictureResult;
import com.halfsummer.common.ServerResponse;
import com.halfsummer.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/pic")
public class PictureController  {
	
	@Autowired
	private PictureService pictureService;

	@RequestMapping("/upload")
	@ResponseBody
	public PictureResult uploda(MultipartFile uploadFile) throws Exception {
		//调用service上传图片
		PictureResult pictureResult = pictureService.uploadFile(uploadFile);
		//返回上传结果,包括上传结果和上传后图片URL

		return pictureResult;
		
	}

	@RequestMapping("/upload1")
	@ResponseBody
	public ServerResponse<String> uploda1(MultipartFile uploadFile) throws Exception {
		//调用service上传图片
		PictureResult pictureResult = pictureService.uploadFile(uploadFile);
		//返回上传后图片URL
		return ServerResponse.createBySuccess(pictureResult.getUrl());


	}
}
