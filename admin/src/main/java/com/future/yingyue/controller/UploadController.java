package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.base.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by cuishiying on 2017/6/27.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private StorageService storageService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(path = "/uploadImg", method = RequestMethod.POST)
    public AjaxResponse<?> testUpload(MultipartFile file) {
        if (file!=null&&!file.isEmpty()) {
            String postfix = "";
            if (file.getContentType().equalsIgnoreCase("image/jpeg") || file.getContentType().equals("image/pjpeg")) {
                postfix = ".jpg";
            } else if (file.getContentType().equalsIgnoreCase("image/png")) {
                postfix = ".png";
            } else if (file.getContentType().equalsIgnoreCase("image/gif")) {
                postfix = ".gif";
            }
            String folderName = "business";
            String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
            try {
                String url = storageService.uploadImg(folderName, fileName, postfix, file.getSize(),
                        file.getInputStream(), false);
                return AjaxResponse.success(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AjaxResponse.fail("上传文件出错");
        }
        return AjaxResponse.fail("请选择一个文件");
    }
    /**
     * 上传附件
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(path = "/uploadAnnex", method = RequestMethod.POST)
    public AjaxResponse<?> uploadAnnex(MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) return AjaxResponse.fail("请选择一个文件");
        String postfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        String folderName = "annex";
        try {
            request.getSession();
            String url = storageService.uploadFile(folderName, fileName, postfix, file.getSize(), file.getInputStream(),
                    false, request.getSession());
            return AjaxResponse.success(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResponse.success();
    }

}
