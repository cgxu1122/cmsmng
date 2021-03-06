package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.progress.ProgressModel;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.imei.ZipUploadService;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.ifhz.core.utils.FileHandle;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 14:47
 */
@Controller
@RequestMapping("/tymng/zipUpload")
public class ZipUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUploadController.class);

    @Resource(name = "zipUploadService")
    private ZipUploadService zipUploadService;
    @Resource(name = "channelInfoService")
    private ChannelInfoService channelInfoService;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("zipUpload/index");
    }

    @RequestMapping(value = "/importZip.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject importZip(@RequestParam(value = "zipFile", required = true) MultipartFile file,
                         @RequestParam(value = "channelId", required = true) Long channelId,
                         @RequestParam(value = "processDateStr", required = true) String processDateStr,
                         HttpServletRequest request) {
        JSONObject result = new JSONObject();
        result.put("ret", true);
        if (file == null || file.isEmpty()) {
            result.put("ret", false);
            result.put("errorMsg", "非法请求，请选择需要上传的文件");
            return result;
        }
        String originFileName = file.getOriginalFilename();
        if (!checkFileType(originFileName)) {
            result.put("ret", false);
            result.put("errorMsg", "非法文件类型，只允许上传Excel文件");
            return result;
        }
        try {
            Date processDate = DateFormatUtils.parse(processDateStr, "yyyy-MM-dd");
            LOGGER.info("用户上传ZIP文件fileName={} ----------开始处理", originFileName);
            String fileExt = FileHandle.getFileExt(originFileName);
            String newFileName = UUID.randomUUID() + "." + fileExt.toLowerCase();
            String toFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
            if (StringUtils.isBlank(toFilePath)) {
                throw new Exception("文件保存到本地失败！！！");
            }
            LOGGER.info("用户上传ZIP文件fileName={},保存到本地成功,路径为{}", toFilePath);
            Map<ImeiStatus, Integer> map = zipUploadService.processFile(toFilePath, channelId, processDate);
            if (MapUtils.isNotEmpty(map)) {
                for (Map.Entry<ImeiStatus, Integer> entry : map.entrySet()) {
                    result.put(entry.getKey().name(), entry.getValue());
                }
            }
            for (ImeiStatus imeiStatus : ImeiStatus.values()) {
                if (!result.containsKey(imeiStatus.name())) {
                    result.put(imeiStatus.name(), 0);
                }
            }
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "处理上传文件失败，请重新操作");
            LOGGER.error("importZip error ", e);
        } finally {
            LOGGER.info("importZip:returnObj={}", result);
        }

        return result;
    }

    private boolean checkProcessDate(Date processDate) {
        Date startTime = DateFormatUtils.addDay(new Date(), -3);
        startTime = DateHandler.getStartTime(startTime);
        Date endTime = new Date();
        if (processDate.before(endTime) && processDate.after(startTime)) {
            return true;
        }

        return false;
    }


    @RequestMapping(value = "/channelList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject channelList(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String channelNameCondition = request.getParameter("channelNameCondition");
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        if (StringUtils.isNotBlank(channelNameCondition))
            ci.setChannelNameCondition(channelNameCondition.trim());
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ci);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }


    public boolean checkFileType(String fileName) {
        if (StringUtils.endsWithIgnoreCase(fileName, ".zip")) {
            return true;
        }

        return false;
    }


    /**
     * 前端每隔固定毫秒数请求后台得到session当中保存的上传进度
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getProgress", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getProgress(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            ProgressModel ps = (ProgressModel) request.getSession().getAttribute("upload_progress");
            Double percent = 0d;
            if (ps.getContentLength() != 0L) {
                percent = (double) ps.getBytesRead() / (double) ps.getContentLength();  //百分比
                if (percent != 0d) {
                    DecimalFormat df = new DecimalFormat("0.0");
                    percent = Double.parseDouble(df.format(percent));
                }
            }
            result.put("ret", true);
            result.put("percent", percent);
        } catch (NumberFormatException e) {
            result.put("ret", false);
            LOGGER.error("getProgress error", e);
        } finally {
            LOGGER.info("returnObj={}", result.toJSONString());
        }

        return result;
    }

}
