package com.future.yingyue.service;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.dto.AdminQueryDTO;
import com.future.yingyue.entity.Admin;
import com.future.yingyue.entity.AdminRole;
import com.future.yingyue.enums.AuditStatus;
import com.future.yingyue.repository.AdminRepository;
import com.future.yingyue.repository.AdminRoleRepository;
import com.future.yingyue.utils.StrUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiying on 2017/5/10.
 */
@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;


    /**
     * 用户注册
     * @param admin
     * @return
     */
    public AjaxResponse regist(Admin admin){

        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(4)));
        AdminRole adminRole = adminRoleRepository.findByRoleName("Customer");
        admin.setAdminRole(adminRole);
        try{
            adminRepository.save(admin);
        }catch (Exception e){
            //手机号或者帐号唯一字段
            return AjaxResponse.fail("您的帐号已存在");
        }
        return AjaxResponse.success("注册成功");
    }

    /**
     * 分页获取用户列表
     * @param queryDTO
     * @param pageable
     * @return
     */
    public Page<Admin> getCustomers(AdminQueryDTO queryDTO,Pageable pageable){
        Specification<Admin> spec = this.getWhereClause(queryDTO);
        try{
            Page<Admin> page = adminRepository.findAll(spec,pageable);
            return page;
        }catch (Exception e){
            return new PageImpl<Admin>(new ArrayList<>());
        }
    }

    /**
     * 查询条件
     * @param queryVo
     * @return
     */
    private Specification<Admin> getWhereClause(AdminQueryDTO queryVo) {
        return (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();

            //关键词
            if(queryVo!=null&&StringUtils.isNotBlank(queryVo.getKeyword())){
                predicate.add(cb.or(cb.like(root.<String>get("accountNumber"), "%" + queryVo.getKeyword().trim() + "%"),
                        cb.like(root.<String>get("adminName"), "%" + queryVo.getKeyword().trim() + "%")));

            }

            //角色
            if(queryVo!=null&&queryVo.getAdminRoleId()!=null){
                Predicate roleQuery = cb.equal(root.get("adminRole").get("id").as(Integer.class), queryVo.getAdminRoleId());
                predicate.add(roleQuery);
            }

            //创建时间
            if (queryVo!=null&&queryVo.getBeginDate() != null) {
                LocalDateTime begin = LocalDateTime.of(queryVo.getBeginDate(), LocalTime.MIN);
                Predicate dateBeginQuery = cb.greaterThanOrEqualTo(root.<LocalDateTime>get("createTime"), begin);
                predicate.add(dateBeginQuery);
            }
            if (queryVo!=null&&queryVo.getEndDate() != null) {
                LocalDateTime end = LocalDateTime.of(queryVo.getEndDate(), LocalTime.MAX);
                Predicate dateEndQuery = cb.lessThanOrEqualTo(root.<LocalDateTime>get("createTime"), end);
                predicate.add(dateEndQuery);
            }

            return query.where(predicate.toArray(new Predicate[predicate.size()])).getRestriction();
        };
    }

    /**
     * 客户登录，可手机号、邮箱、帐号登录
     * @param username
     * @param password
     * @return
     */
    public AjaxResponse login(String username, String password){

        Admin admin = null;
        if(StrUtils.isMobile(username)){
            admin = StringUtils.isNotBlank(username) ? adminRepository.findByPhone(username) : null;
        }else if(StrUtils.isEmail(username)){
            admin = StringUtils.isNotBlank(username) ? adminRepository.findByEmail(username) : null;
        }else{
            admin = StringUtils.isNotBlank(username) ? adminRepository.findByAccountNumber(username) : null;
        }
        if (admin == null) return AjaxResponse.fail("帐号不存在");
        if (!BCrypt.checkpw(password, admin.getPassword()))
            return AjaxResponse.fail("密码错误");
        return AjaxResponse.success(admin.getId());
    }

    /**
     * 修改密码
     * @param phone
     * @param password
     * @return
     */
    public AjaxResponse updatePassword(String phone, String password) {
        Admin admin = adminRepository.findByPhone(phone);
        if (admin == null) return AjaxResponse.fail("修改失败！此手机号未注册。");
        if (StringUtils.isBlank(password)) return AjaxResponse.fail("密码不能为空");
        admin.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(4)));
        return AjaxResponse.success();
    }

    public AjaxResponse updatePhone(Integer adminId, String phone) {
        Admin a = adminId != null ? adminRepository.findOne(adminId) : null;
        if (a == null) return AjaxResponse.fail("用户不存在。");
        Admin admin = adminRepository.findByPhone(phone);
        if (admin != null) return AjaxResponse.fail("此手机号已注册。");
        a.setPhone(phone);
        return AjaxResponse.success();
    }

    /**
     * 初始密码重置
     * @param phone
     * @return
     */
    public AjaxResponse resetPassword(String phone){
        AjaxResponse ajaxResponse = this.updatePassword(phone, "111111");
        return ajaxResponse;
    }
    /**
     * 获取用户详情
     */
    public Admin findById(Integer id) {
        return adminRepository.findOne(id);
    }

    /**
     * 更新用户信息
     * @param id
     * @param updateAdmin
     * @return
     */
    public AjaxResponse updateAdmin(Integer id,Admin updateAdmin){
        try{
            Admin admin = adminRepository.findById(id);
            admin.getAdminRole().getAuthorities().size();
            admin.setAdminName(updateAdmin.getAdminName());
            admin.setPhone(updateAdmin.getPhone());
            admin.setQqNumber(updateAdmin.getQqNumber());
            admin.setWeixin(updateAdmin.getWeixin());
            admin.setEmail(updateAdmin.getEmail());
            admin.setBirth(updateAdmin.getBirth());
            admin.setHeadImg(updateAdmin.getHeadImg());
            return AjaxResponse.success("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.fail("修改失败");
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public AjaxResponse deleteAdmin(int id){
        Admin admin = adminRepository.findById(id);
        if(admin.getAccountNumber().equals("admin")){
            return AjaxResponse.fail("管理员不能被删除");
        }
        try {
            adminRepository.delete(id);
            return AjaxResponse.success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.fail("删除失败");
        }

    }

    /**
     * 获取所有角色
     * @return
     */
    public List<AdminRole> findAllAdminRoles(){
        List<AdminRole> list = adminRoleRepository.findAll();
        return list;
    }
}
