package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageApkRef;
import com.ifhz.core.po.PackageInfo;
import com.ifhz.core.service.pkgmng.PackageInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/packageInfo")
public class PackageInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageInfoController.class);
    @Autowired
    private PackageInfoService packageInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String type = request.getParameter("type");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", type);
        return new ModelAndView("packageInfo/index", result);
    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject list(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String packageNameCondition = request.getParameter("packageNameCondition");
        String groupId = request.getParameter("groupId");
        String type = request.getParameter("type");
        PackageInfo pi = new PackageInfo();
        pi.setType(type);
        pi.setActive(JcywConstants.ACTIVE_Y);
        if (StringUtils.isNotEmpty(groupId)) {
            pi.setGroupId(Long.parseLong(groupId));
        }
        pi.setPackageNameCondition(packageNameCondition);
        List<PackageInfo> list = packageInfoService.queryByVo(page, pi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        String type = request.getParameter("type");
        String packageName = request.getParameter("packageName");
        String batchId = request.getParameter("batchId");
        String batchCode = request.getParameter("batchCode");
        String remark = request.getParameter("remark");
        String errorMsg = null;
        if (StringUtils.isEmpty(type)) {
            errorMsg = "系统错误，请联系管理员";
        } else if (StringUtils.isEmpty(packageName)) {
            errorMsg = "请填写安装包名称！";
        } else if (StringUtils.isEmpty(batchId) || StringUtils.isEmpty(batchCode)) {
            errorMsg = "请匹配正确的批次号！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        PackageInfo pi = new PackageInfo();
        pi.setActive(JcywConstants.ACTIVE_Y);
        pi.setPackageName(packageName.trim());
        List<PackageInfo> piList = packageInfoService.queryByVo(page, pi);
        if (CollectionUtils.isNotEmpty(piList)) {
            result.put("errorMsg", "安装包名称重复，请重新填写！");
            return result;
        }
        pi = new PackageInfo();
        pi.setActive(JcywConstants.ACTIVE_Y);
        if (StringUtils.isNotEmpty(groupId)) {
            pi.setGroupId(Long.parseLong(groupId));
            piList = packageInfoService.queryByVo(page, pi);
            if (CollectionUtils.isNotEmpty(piList)) {
                result.put("errorMsg", "每个渠道组只能有一个通用包！不能重复添加！");
                return result;
            }
        }
        pi.setPackageName(packageName.trim());
        pi.setBatchCode(batchCode);
        pi.setBatchId(Long.parseLong(batchId));
        pi.setRemark(remark);
        pi.setType(type);
        String[] apkIdList = request.getParameterValues("apkId");
        String[] apkNameList = request.getParameterValues("apkName");
        String[] apkTypeList = request.getParameterValues("apkType");
        String[] autoRunList = request.getParameterValues("autoRun");
        String[] desktopIconList = request.getParameterValues("desktopIcon");
        if (apkIdList != null && apkIdList.length > 0) {
            List<PackageApkRef> packageApkRefList = new ArrayList<PackageApkRef>();
            for (int i = 0; i < apkIdList.length; i++) {
                if (StringUtils.isNotEmpty(apkIdList[i]) && StringUtils.isNumeric(apkIdList[i])) {
                    PackageApkRef packageApkRef = new PackageApkRef();
                    packageApkRef.setApkId(Long.parseLong(apkIdList[i]));
                    packageApkRef.setApkName(apkNameList[i]);
                    if (StringUtils.isNotEmpty(apkTypeList[i])) {
                        packageApkRef.setApkType(Integer.parseInt(apkTypeList[i]));
                    } else {
                        packageApkRef.setApkType(1);
                    }
                    packageApkRef.setAutoRun(autoRunList[i]);
                    packageApkRef.setDesktopIcon(desktopIconList[i]);
                    packageApkRef.setActive(JcywConstants.ACTIVE_Y);
                    packageApkRef.setSort(i);
                    packageApkRefList.add(packageApkRef);
                }
            }
            pi.setPackageApkRefList(packageApkRefList);
        }
        packageInfoService.insert(pi);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String packageId = request.getParameter("packageId");
        String remark = request.getParameter("remark");
        String errorMsg = null;
        if (StringUtils.isEmpty(packageId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        PackageInfo packageInfo = packageInfoService.getById(Long.parseLong(packageId));
        if (packageInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        packageInfo.setRemark(remark);
        String[] apkIdList = request.getParameterValues("apkId");
        String[] apkNameList = request.getParameterValues("apkName");
        String[] apkTypeList = request.getParameterValues("apkType");
        String[] autoRunList = request.getParameterValues("autoRun");
        String[] desktopIconList = request.getParameterValues("desktopIcon");
        if (apkIdList != null && apkIdList.length > 0) {
            List<PackageApkRef> packageApkRefList = new ArrayList<PackageApkRef>();
            for (int i = 0; i < apkIdList.length; i++) {
                if (StringUtils.isNotEmpty(apkIdList[i]) && StringUtils.isNumeric(apkIdList[i])) {
                    PackageApkRef packageApkRef = new PackageApkRef();
                    packageApkRef.setApkId(Long.parseLong(apkIdList[i]));
                    packageApkRef.setApkName(apkNameList[i]);
                    if (StringUtils.isNotEmpty(apkTypeList[i])) {
                        packageApkRef.setApkType(Integer.parseInt(apkTypeList[i]));
                    } else {
                        packageApkRef.setApkType(1);
                    }
                    packageApkRef.setAutoRun(autoRunList[i]);
                    packageApkRef.setDesktopIcon(desktopIconList[i]);
                    packageApkRef.setActive(JcywConstants.ACTIVE_Y);
                    packageApkRef.setSort(i);
                    packageApkRefList.add(packageApkRef);
                }
            }
            packageInfo.setPackageApkRefList(packageApkRefList);
        }
        packageInfoService.update(packageInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String packageId = request.getParameter("packageId");
        String errorMsg = null;
        if (StringUtils.isEmpty(packageId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        PackageInfo pi = packageInfoService.getById(Long.parseLong(packageId));
        if (pi == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            packageInfoService.delete(pi);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}