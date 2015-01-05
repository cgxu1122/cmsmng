package com.ifhz.core.po.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 11:05
 */
public class SysUserProductRef implements Serializable {
    private static final long serialVersionUID = -5790231504663879253L;

    private Long id;
    private Long userId;
    private Long productId;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysUserProductRef{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", createTime=" + createTime +
                '}';
    }
}
