package com.ifhz.core.service.batch.impl;

import com.ifhz.core.adapter.BatchInfoAdapter;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchInfo;
import com.ifhz.core.po.BatchProductRef;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.batch.BatchInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("batchInfoService")
public class BatchInfoServiceImpl implements BatchInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchInfoServiceImpl.class);

    @Resource(name = "batchInfoAdapter")
    private BatchInfoAdapter batchInfoAdapter;
    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;

    @Override
    public BatchInfo getById(Long id) {
        return batchInfoAdapter.getById(id);
    }

    @Override
    public List<BatchInfo> queryByVo(Pagination page, BatchInfo record) {
        return batchInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(BatchInfo record) {
        int flag = batchInfoAdapter.insert(record);
        List<ProductInfo> productInfoList = record.getProductInfoList();
        String batchProductName = "";
        Integer batchProductNum = 0;
        if (CollectionUtils.isNotEmpty(productInfoList)) {
            for (ProductInfo productInfo : productInfoList) {
                BatchProductRef ref = new BatchProductRef();
                ref.setBatchId(record.getBatchId());
                ref.setBatchCode(record.getBatchCode());
                ref.setProductId(productInfo.getProductId());
                batchProductRefAdapter.insert(ref);
                batchProductName += productInfo.getProductName() + ",";
            }
            if (batchProductName.endsWith(",")) {
                batchProductName = batchProductName.substring(0, batchProductName.lastIndexOf(","));
            }
            batchProductNum = productInfoList.size();
        }
        record.setBatchProductNum(batchProductNum);
        record.setBatchProductName(batchProductName);
        batchInfoAdapter.update(record);
        return flag;
    }

    @Override
    public int update(BatchInfo record) {
        //先删除
        BatchProductRef delBpRef = new BatchProductRef();
        delBpRef.setBatchId(record.getBatchId());
        batchProductRefAdapter.delete(delBpRef);
        //后添加
        String batchProductName = "";
        Integer batchProductNum = 0;
        List<ProductInfo> productInfoList = record.getProductInfoList();
        if (CollectionUtils.isNotEmpty(productInfoList)) {
            for (ProductInfo productInfo : productInfoList) {
                BatchProductRef ref = new BatchProductRef();
                ref.setBatchId(record.getBatchId());
                ref.setBatchCode(record.getBatchCode());
                ref.setProductId(productInfo.getProductId());
                batchProductRefAdapter.insert(ref);
                batchProductName += productInfo.getProductName() + ",";
            }
            if (batchProductName.endsWith(",")) {
                batchProductName = batchProductName.substring(0, batchProductName.lastIndexOf(","));
            }
            batchProductNum = productInfoList.size();
        }
        record.setBatchProductNum(batchProductNum);
        record.setBatchProductName(batchProductName);
        return batchInfoAdapter.update(record);
    }

    @Override
    public int delete(BatchInfo record) {
        //先删除关联表信息
        BatchProductRef delBpRef = new BatchProductRef();
        delBpRef.setBatchId(record.getBatchId());
        batchProductRefAdapter.delete(delBpRef);
        return batchInfoAdapter.delete(record);
    }

    @Override
    public Long getSeqByGroupId(Long groupId) {
        return batchInfoAdapter.getSeqByGroupId(groupId);
    }
}
