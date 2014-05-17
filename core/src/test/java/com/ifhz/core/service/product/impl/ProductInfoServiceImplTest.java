package com.ifhz.core.service.product.impl;

import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.product.ProductInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductInfoServiceImplTest extends BaseTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void testGetById() throws Exception {
        log(productInfoService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        ProductInfo po = new ProductInfo();
        po.setActive("Y");
        log(productInfoService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        ProductInfo po = new ProductInfo();
        po.setProductName("百度地图");
        po.setPartnerId(2L);
        po.setQueryDataSource("Y");
        log(productInfoService.insert(po));
    }

    @Test
    public void testUpdate() throws Exception {
        ProductInfo po = new ProductInfo();
        po.setProductId(2L);
        po.setProductName("tengxunditu");
        productInfoService.update(po);
    }

    @Test
    public void testDelete() throws Exception {
        ProductInfo po = new ProductInfo();
        po.setProductId(2L);
        productInfoService.delete(po);
    }
}