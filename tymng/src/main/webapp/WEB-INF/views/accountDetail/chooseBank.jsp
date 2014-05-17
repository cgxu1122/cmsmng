<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    img {
        display: block;
        float: left;
        margin: 10px;
    }

    input[type="radio"] {
        float: left;
        margin-top: 22px;
    }

    .choose_bank {
        margin: 0 20px;
        clear: both;
    }

    #chooseArea h3 {
        display: inline;
        margin-right: 10px;
    }

    #chooseArea a {
        font-size: 14px;
        padding: 0 10px;
    }
</style>
<div id="chooseBank" class="easyui-dialog" title="选择银行"
     style="width:800px;height:350px;padding:10px 20px;"
     closed="true" data-options="modal:true"
        >
    <div class="choose_bank">
        <input name="bank" type="radio" value="中国工商银行">
        <img src="<%=basePath %>/image/bank_1.png">
        <input name="bank" type="radio" value="中国建设银行">
        <img src="<%=basePath %>/image/bank_2.png">
        <input name="bank" type="radio" value="中国农业银行">
        <img src="<%=basePath %>/image/bank_3.png">
        <input name="bank" type="radio" value="中国邮政储蓄银行">
        <img src="<%=basePath %>/image/bank_4.png">
    </div>
    <div class="choose_bank">
        <input name="bank" type="radio" value="中国银行">
        <img src="<%=basePath %>/image/bank_5.png">
        <input name="bank" type="radio" value="交通银行">
        <img src="<%=basePath %>/image/bank_6.png">
        <input name="bank" type="radio" value="招商银行">
        <img src="<%=basePath %>/image/bank_7.png">
        <input name="bank" type="radio" value="中国光大银行">
        <img src="<%=basePath %>/image/bank_8.png">
    </div>
    <div class="choose_bank">
        <input name="bank" type="radio" value="浦发银行">
        <img src="<%=basePath %>/image/bank_9.png">
        <input name="bank" type="radio" value="华夏银行">
        <img src="<%=basePath %>/image/bank_10.png">
        <input name="bank" type="radio" value="广发银行ICGB">
        <img src="<%=basePath %>/image/bank_11.png">
        <input name="bank" type="radio" value="中信银行">
        <img src="<%=basePath %>/image/bank_12.png">
    </div>
    <div class="choose_bank">
        <input name="bank" type="radio" value="兴业银行">
        <img src="<%=basePath %>/image/bank_13.png">
        <input name="bank" type="radio" value="中国民生银行">
        <img src="<%=basePath %>/image/bank_14.png">
    </div>
    <p style="clear:both;margin-left:20px;"><a href="#" id="definite" class="easyui-linkbutton" iconcls="icon-ok">确定</a>
    </p>
</div>
<div id="chooseArea" class="easyui-dialog" title="选择地区"
     style="width:800px;height:350px;padding:10px 20px;"
     closed="true" data-options="modal:true"
        >
    <p>

    <h3>华北:</h3>
    <a href="#">北京市</a>
    <a href="#">天津市</a>
    <a href="#">山西省</a>
    <a href="#">河北省</a>
    <a href="#">内蒙古自治区</a>
    </p>
    <p>

    <h3>东北:</h3>
    <a href="#">辽宁省</a>
    <a href="#">黑龙江省</a>
    <a href="#">吉林省</a>
    </p>

    <p>

    <h3>华东:</h3>
    <a href="#">上海市</a>
    <a href="#">山东省</a>
    <a href="#">安徽省</a>
    <a href="#">浙江省</a>
    <a href="#">江西省</a>
    <a href="#">福建省</a>
    <a href="#">江苏省</a>
    </p>
    <p>

    <h3>中南:</h3>
    <a href="#">河南省</a>
    <a href="#">湖北省</a>
    <a href="#">湖南省</a>
    <a href="#">海南省</a>
    <a href="#">广东省</a>
    <a href="#">广西壮族自治区</a>
    </p>
    <p>

    <h3>西南:</h3>
    <a href="#">重兴省</a>
    <a href="#">云南省</a>
    <a href="#">贵州省</a>
    <a href="#">四川省</a>
    <a href="#">西藏自治区</a>
    </p>
    <p>

    <h3>西北:</h3>
    <a href="#">青海省</a>
    <a href="#">甘肃省</a>
    <a href="#">陕西省</a>
    <a href="#">宁夏回族自治区</a>
    <a href="#">新疆维吾尔自治区</a>
    </p>
</div>