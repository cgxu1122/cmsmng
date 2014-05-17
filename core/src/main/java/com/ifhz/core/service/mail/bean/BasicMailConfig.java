package com.ifhz.core.service.mail.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;

import javax.mail.Authenticator;

/**
 * 类描述
 *
 * @author chenggang.xu@qunar.com created at 13-10-28 下午5:39
 * @version 1.0.0
 */
public class BasicMailConfig {

    private String from;
    private String user;
    private String pwd;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String vmName;
    private String hosts;
    private boolean isAttachment = false;
    private String attachmentPath;

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String[] genTosArray() {
        if (StringUtils.isNotBlank(this.to)) {
            return StringUtils.split(this.to, "\\,");
        }
        return new String[0];
    }

    public String[] genCcArray() {
        if (StringUtils.isNotBlank(this.cc)) {
            return StringUtils.split(this.cc, "\\,");
        }
        return new String[0];
    }

    public String[] genBccArray() {
        if (StringUtils.isNotBlank(this.bcc)) {
            return StringUtils.split(this.bcc, "\\,");
        }
        return new String[0];
    }

    public boolean isAttachment() {
        return isAttachment;
    }

    public void setAttachment(boolean isAttachment) {
        this.isAttachment = isAttachment;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public Authenticator getAuth() {
        return new DefaultAuthenticator(this.user, this.pwd);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BasicMailConfig{" +
                "from='" + from + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", to='" + to + '\'' +
                ", cc='" + cc + '\'' +
                ", bcc='" + bcc + '\'' +
                ", subject='" + subject + '\'' +
                ", vmName='" + vmName + '\'' +
                ", hosts='" + hosts + '\'' +
                ", isAttachment=" + isAttachment +
                ", attachmentPath='" + attachmentPath + '\'' +
                '}';
    }
}
