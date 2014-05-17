package com.ifhz.core.service.mail;


import com.ifhz.core.service.mail.enums.MailType;

import java.util.List;
import java.util.Map;

/**
 * 邮件发送服务
 *
 * @author chenggang.xu@qunar.com created at 13-10-22 上午10:23
 * @version 1.0.0
 */
public interface MailSenderService {

    /**
     * 异步发送邮件服务
     *
     * @param mailType 邮件类型 必选
     * @param params   模板填充参数 非必选
     * @throws org.apache.commons.mail.EmailException
     */
    public void asyncSendMail(MailType mailType, Map<String, Object> params) throws Exception;

    /**
     * 同步发送邮件服务
     *
     * @param mailType 邮件类型 必选
     * @param params   模板填充参数 非必选
     * @throws org.apache.commons.mail.EmailException
     */
    public void sendMail(MailType mailType, Map<String, Object> params) throws Exception;

    /**
     * 同步发送邮件服务 自定义收件人 + 主题
     *
     * @param mailType
     * @param tos
     * @param subject
     * @param params
     * @throws Exception
     */
    public void sendMail(MailType mailType, List<String> tos, String subject, Map<String, Object> params) throws Exception;

    /**
     * 异步发送邮件服务 自定义收件人 + 主题
     *
     * @param mailType
     * @param tos
     * @param subject
     * @param params
     * @throws Exception
     */
    public void asyncSendMail(MailType mailType, List<String> tos, String subject, Map<String, Object> params) throws Exception;
}
