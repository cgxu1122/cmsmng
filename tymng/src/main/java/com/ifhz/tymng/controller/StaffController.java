/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.ifhz.core.base.BaseController;
import com.ifhz.core.po.Staff;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户管理
 *
 * @author radish moon
 */
@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {

//	@Autowired
//	private StaffService iStaffService;

    /**
     * 登陆管理主页
     *
     * @return
     * @author radish
     */
    @RequestMapping("/index")
    public String indexStaff() {
        return "staff/index";
    }

    /**
     * 修改个人信息主页
     *
     * @return
     * @author radish
     */
    @RequestMapping("/updateindex")
    public String updateindex() {
        return "staff/updateindex";
    }

    /**
     * 修改密码页面
     *
     * @author radish
     * @return
     */
    /*@RequestMapping("/updatepassword")
	public String updatepasswor() {
		return "auth/updatepassword";
	}*/

    /**
     * 保存用户类型
     *
     * @param req
     * @return
     * @author radish
     */
    @RequestMapping("/insert")
    @ResponseBody()
    public String insert(HttpServletRequest req, Staff staff) {
//		boolean flag = this.checkUnique(auth.getEmail());
//		if (!flag) {
//			return JsonUtils.singleErrorMsg("邮件地址重复");
//		}
//		auth.setCreateTime(new Date());
//
//		/**
//		 * 密码加密
//		 */
//		String password = StringUtils.trim(auth.getPassword());
//		auth.setPassword(MD5keyUtil.getMD5Str(password));
//		// auth.setStatus(StaffStatusEnum.ENABLE.getStatusValue());
//		iStaffService.insertStaff(auth);
        return "[{}]";
    }

    /**
     * 更新用户类型保存
     *
     * @param staff
     * @return
     * @author radish
     */
    @RequestMapping("/update")
    @ResponseBody()
    public String update(Staff staff) {
//		Integer id = auth.getId();
//		String email = auth.getEmail();
//		Staff _staff = iStaffService.findStaff(id);
//		Result result = new Result();
//
//		if (StringUtils.equals(email, _staff.getEmail())
//				&& StringUtils
//						.equals(auth.getNickname(), _staff.getNickname())
//				&& (auth.getRoleId() == _staff.getRoleId())
//				&& (auth.getStatus() == _staff.getStatus())) {
//
//		} else {
//			// 判断邮箱是否被别的用户所占用
//			Integer otherId = iStaffService.findStaffByEmailAndNotId(email, id);
//			if (null != otherId) {
//				result.setCode(-1);
//				result.setMessage("该邮箱已经被其他用户占用！");
//				return JSON.toJSONString(result);
//			}
//			_staff.setEmail(auth.getEmail());
//			_staff.setNickname(auth.getNickname());
//			_staff.setRoleId(auth.getRoleId());
//			_staff.setStatus(auth.getStatus());
//			iStaffService.updateStaffAdmin(_staff);
//		}
//
//		result.setCode(1);
//		result.setMessage("更新成功");
        return "[{}]";
    }

    /**
     * 更新用户密码
     *
     * @param
     * @return
     * @author wangshaofen
     */
    @RequestMapping("/updatePassowrd")
    @ResponseBody()
    public String updatePassword(HttpServletRequest req) {
//		Long id=Long.parseLong(req.getParameter("id"));
//		String password=req.getParameter("password");
//		Result result = new Result();
//		boolean flag=iStaffService.updateStaffPassword(id,MD5keyUtil.getMD5Str(password));
//		if(flag == false){
//			result.setCode(-1);
//			result.setMessage("修改密码失败");
//			return JSON.toJSONString(result);
//		}
//		result.setCode(1);
//		result.setMessage("修改密码成功");
        return "[{}]";
    }


    /**
     * 获取所有用户类型
     *
     * @param req
     * @return
     * @author radish
     */
    @RequestMapping("/udpatePassword")
    @ResponseBody
    public String udpatePassword(HttpServletRequest req) {
//		String password = StringUtils.trim(request.getParameter("password"));
//		String id = StringUtils.trim(request.getParameter("id"));
//
//		Staff auth = iStaffService.findStaff(Integer.parseInt(id));
//		if (StringUtils.equals(EncryptionUtil.decryption(auth.getPassword()),
//				password)) {
//			// do nothing
//		} else {
//			auth.setPassword(password);
//			// iStaffService.updateStaffPassword(auth);
//		}

        return "";
    }

    /**
     * 删除
     *
     * @param request
     * @return
     * @author radish
     */
    @RequestMapping("/delete")
    @ResponseBody()
    public String delete(HttpServletRequest request) {
//		String id = request.getParameter("id");
//		iStaffService.deleteStaff(Integer.parseInt(id));
        return "";
    }

    /**
     * 名称唯一性验证
     *
     * @param email
     * @return true/false 有/没有
     * @author radish
     */
    private boolean checkUnique(String email) {
        boolean flag = false;
//		Staff auth = iStaffService.findStaffByEmail(email);
//		if (auth == null) {
//			flag = true;
//		}
        return flag;
    }

    /**
     * 获取所有用户类型
     *
     * @param request
     * @return
     * @author radish
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ResponseBody
    public String getAllStaff(HttpServletRequest request) {

//		String searchValue = request.getParameter("searchValue");
//		// 添加分页
//		String page = request.getParameter("page");
//		String rows = request.getParameter("rows");
//
//		Map paramMap = new HashMap();
//		if (StringUtils.isBlank(page)) {
//			paramMap.put("from", 0);
//		} else {
//			int pageNo = Integer.parseInt(page);
//			int pageSize = Integer.parseInt(rows);
//			paramMap.put("from", (pageNo - 1) * pageSize);
//		}
//
//		if (StringUtils.isBlank(rows)) {
//			paramMap.put("rows", 10);
//		} else {
//			paramMap.put("rows", Integer.parseInt(rows));
//		}
//		// 查询条件
//		if (StringUtils.isNotBlank(searchValue)) {
//			paramMap.put("param", searchValue);
//		}
//		// 查询记录
//		List<StaffDto> staffDtoList = iStaffService.findAllStaff(paramMap);
//		/**
//		 * 将dto转换为vo
//		 */
//		List<StaffVo> staffVoList = new ArrayList();
//		StaffVo staffVo = null;
//		Staff auth = null;
//		Role role = null;
//		for (int i = 0; i < staffDtoList.size(); i++) {
//			staffVo = new StaffVo();
//			auth = staffDtoList.get(i).getStaff();
//			role = staffDtoList.get(i).getRole();
//			staffVo.setCellphone(auth.getCellphone());
//			staffVo.setCreateTime(auth.getCreateTime());
//			staffVo.setEmail(auth.getEmail());
//			staffVo.setNickname(auth.getNickname());
//			staffVo.setRoleId(auth.getRoleId());
//			staffVo.setRoleName(role.getRoleName());
//			staffVo.setStatus(auth.getStatus());
//			staffVo.setTelephone(auth.getTelephone());
//			staffVo.setId(auth.getId());
//			staffVoList.add(staffVo);
//
//		}
//
//		// 记录数
//		Integer counts = iStaffService.getStaffVoCount(paramMap);
//
//		Map dataGridJsonData = new HashMap();
//		dataGridJsonData.put("total", counts);
//		dataGridJsonData.put("rows", staffVoList);
//		// return JSON.toJSONString(dataGridJsonData);
        return "";

    }

    /**
     * 获取所有用户类型
     *
     * @param req
     * @return
     * @author radish
     */
    @RequestMapping("/getByName/{name}")
    @ResponseBody
    public String getByName(HttpServletRequest req,
                            @PathVariable("name") String name) {
//		if (StringUtils.isBlank(name)) {
//			return this.getAllStaff(req);
//		} else {
//			List<StaffDto> staffList = iStaffService.findStaffByNickname(name);
//			return JsonUtils.list2JsonString(staffList);
//		}
        return "";
    }

    /**
     * 获取所有用户类型
     *
     * @param
     * @return
     * @author radish
     */
    @RequestMapping("/getAllRole")
    @ResponseBody
    public String getAllRole() {
//		List<Role> roleList = iStaffService.findAllRole();
//		return JsonUtils.list2JsonString(roleList);
        return "";
    }

}
