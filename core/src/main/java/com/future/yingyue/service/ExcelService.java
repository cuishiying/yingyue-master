package com.future.yingyue.service;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.entity.Admin;
import com.future.yingyue.repository.AdminRepository;
import com.future.yingyue.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class ExcelService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * 导出客户数据
     * @param response
     */
    public void exportAdmin(HttpServletResponse response){
        response.reset(); //清除buffer缓存
        String fileName = "客户数据";
        String sheetName = "客户数据";
        List<Admin> list = new ArrayList<>();
        Admin admin = new Admin();
        admin.setAccountNumber("accountNumber");
        admin.setAdminName("adminName");
        list.add(admin);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("帐号","accountNumber");
        map.put("姓名","adminName");
        ExcelUtils.export(fileName,sheetName,Admin.class,list,map,response);
    }

    /**
     * 导入客户数据
     */
    public AjaxResponse importAdmin(HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("upfile");
        if(file.isEmpty()){
            return AjaxResponse.fail("文件不存在！");
        }
        InputStream in = file.getInputStream();
        List<List<Object>> listob = ExcelUtils.getBankListByExcel(in,file.getOriginalFilename());
        List<Admin> list=new ArrayList<Admin>();
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            Admin admin = new Admin();
            admin.setAccountNumber(String.valueOf(lo.get(0)));
            admin.setAdminName(String.valueOf(lo.get(1)));
            list.add(admin);
        }
        adminRepository.save(list);
        return AjaxResponse.success();
    }
}
