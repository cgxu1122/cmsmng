package com.ifhz.core.service.mail.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.mail.bean.BasicMailConfig;
import com.ifhz.core.service.mail.enums.MailType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.List;
import java.util.Map;

/**
 * apache commons mail 邮件服务
 *
 * @author chenggang.xu@qunar.com created at 13-10-28 下午3:24
 * @version 1.0.0
 */
public class CommonsMailEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonsMailEngine.class);

    private static final String USERNAME = "内置业务系统";
    private static final String DEFAULT_IMAGE_PATH = "/template/image001.png";

    private Map<String, BasicMailConfig> templateContainer = null;
    private VelocityEngine velocityEngine;

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setTemplateContainer(Map<String, BasicMailConfig> templateContainer) {
        this.templateContainer = templateContainer;
    }

    public void sendMail(MailType mailType) throws Exception {
        BasicMailConfig template = this.getBasicMailBean(mailType);
        if (template == null) {
            return;
        }
        HtmlEmail email = translateMail(template, Maps.<String, Object>newHashMap());
        LOGGER.info("sendMail HtmlEmail : {}", email);
        String result = email.send();

        LOGGER.info("sendMail return value={}", result);
    }

    public void sendMail(MailType mailType, Map<String, Object> params) throws Exception {
        BasicMailConfig template = this.getBasicMailBean(mailType);
        if (template == null) {
            return;
        }
        HtmlEmail email = translateMail(template, params);
        LOGGER.info("sendMail HtmlEmail : {}", email);
        String result = email.send();

        LOGGER.info("sendMail return value={}", result);
    }

    public void sendMail(MailType mailType, List<String> tos, Map<String, Object> params) throws Exception {
        BasicMailConfig template = this.getBasicMailBean(mailType);
        if (template == null) {
            return;
        }
        HtmlEmail email = translateMail(template, params);
        if (CollectionUtils.isNotEmpty(tos)) {
            String[] toArray = new String[tos.size()];
            email.addTo(tos.toArray(toArray));
        }
        LOGGER.info("sendMail HtmlEmail : {}", email);
        String result = email.send();

        LOGGER.info("sendMail return value={}", result);
    }

    public void sendMail(MailType mailType, List<String> tos, String subject, Map<String, Object> params) throws Exception {
        BasicMailConfig template = this.getBasicMailBean(mailType);
        if (template == null) {
            return;
        }
        HtmlEmail email = translateMail(template, params);
        if (CollectionUtils.isNotEmpty(tos)) {
            String[] toArray = new String[tos.size()];
            email.addTo(tos.toArray(toArray));
        }
        email.setSubject(subject);
        String result = email.send();

        LOGGER.info("sendMail return value={}", result);
    }


    private BasicMailConfig getBasicMailBean(MailType type) {
        BasicMailConfig result = null;
        if (type != null) {
            result = templateContainer.get(type.VALUE);
        }

        if (result == null) {
            LOGGER.error("getBasicMailBean error  not config info");
        }

        return result;
    }


    private HtmlEmail translateMail(BasicMailConfig config, Map<String, Object> params) throws Exception {
        HtmlEmail email = new HtmlEmail();

        email.setCharset(GlobalConstants.DEFAULT_CHARSET);

        email.setHostName(config.getHosts());
        if (config.isAttachment()) {
            email.setFrom(config.getFrom(), USERNAME);
        } else {
            email.setFrom(config.getFrom());
        }
        email.setAuthenticator(config.getAuth());

        if (StringUtils.isNotBlank(config.getSubject())) {
            email.setSubject(config.getSubject());
        }
        if (StringUtils.isNotBlank(config.getBcc())) {
            email.addBcc(config.genBccArray());
        }

        if (StringUtils.isNotBlank(config.getCc())) {
            email.addCc(config.genCcArray());
        }

        if (StringUtils.isNotBlank(config.getTo())) {
            email.addTo(config.genTosArray());
        }
        /*if (config.isAttachment()) {
            String path = getImagePath(config.getAttachmentPath());
            LOGGER.info("image path :{}", path);
            email.embed(new File(path), IMG_CID);
        }*/

        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, config.getVmName(), params);
        email.setHtmlMsg(content);

        return email;
    }

    public String getImagePath(String attachmentPath) {
        String path = null;
        if (StringUtils.isNotBlank(attachmentPath)) {
            path = this.getClass().getResource(attachmentPath).getPath();
        }
        if (StringUtils.isBlank(path)) {
            path = this.getClass().getResource(DEFAULT_IMAGE_PATH).getPath();
        }
        return path;
    }
}
