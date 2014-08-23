package com.ifhz.core.po.stat;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/23
 * Time: 15:23
 */
public class LogArriveStatTemp {

    private Long id;
    private String ua;
    private String modelName;
    private Long groupId;
    private Long channelId;
    private Date arriveDate;
    private Long laowuId;
    private Date createTime;
    /**
     * 设备当天到达总数
     */
    private Long totalNum = 0L;
    /**
     * 设备当天到达-有效总数
     */
    private Long validNum = 0L;
    /**
     * 设备当天到达-无效总数
     */
    private Long invalidNum = 0L;
    /**
     * 设备当天到达-替换总数
     */
    private Long invalidReplaceNum = 0L;
    /**
     * 设备当天到达-卸载总数
     */
    private Long invalidUninstallNum = 0L;
    /**
     * 设备当天到达-无效卸载+替换总数
     */
    private Long invalidUnAndReNum = 0L;


    private String md5Key;
    private Integer version;
    private String active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getLaowuId() {
        return laowuId;
    }

    public void setLaowuId(Long laowuId) {
        this.laowuId = laowuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getValidNum() {
        return validNum;
    }

    public void setValidNum(Long validNum) {
        this.validNum = validNum;
    }

    public Long getInvalidNum() {
        return invalidNum;
    }

    public void setInvalidNum(Long invalidNum) {
        this.invalidNum = invalidNum;
    }

    public Long getInvalidReplaceNum() {
        return invalidReplaceNum;
    }

    public void setInvalidReplaceNum(Long invalidReplaceNum) {
        this.invalidReplaceNum = invalidReplaceNum;
    }

    public Long getInvalidUninstallNum() {
        return invalidUninstallNum;
    }

    public void setInvalidUninstallNum(Long invalidUninstallNum) {
        this.invalidUninstallNum = invalidUninstallNum;
    }

    public Long getInvalidUnAndReNum() {
        return invalidUnAndReNum;
    }

    public void setInvalidUnAndReNum(Long invalidUnAndReNum) {
        this.invalidUnAndReNum = invalidUnAndReNum;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
