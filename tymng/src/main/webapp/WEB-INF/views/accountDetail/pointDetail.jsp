<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!--

//-->
</script>
<style>
.getMoney{width: 100%;}
.getMoney td:nth-child(1){width: 100%;}
.getMoney input[type='text']{width:200px;}
.fund span{
margin-right:30px;
}
</style>
<div>
<%@include file="chooseBank.jsp" %>
</div>
<!--奖励充值 层开始-->
<div id="reward-dialog" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px" closed="true" buttons="#dlg-buttons" data-options="modal:true">
<form id="rewardform" method="post" novalidate>
奖励充值金额：<input name="reward" id="reward" class="easyui-validatebox" min="0" required="true" missingMessage="请填写充值金额！" validType="vermoneyNum">
<p/>
<a href="#" id="rconfirm" name="rconfirm" class="easyui-linkbutton" iconcls="icon-ok">确定</a>
<a href="#" id="rcancel" name="rcancel"class="easyui-linkbutton" iconcls="icon-cancel">取消</a>
</form>
</div>
<!--奖励充值 层结束-->

<!--人工充值 层开始-->
<div id="recharge-dialog" class="easyui-dialog" style="width:300px;height:170px;padding:10px 20px" closed="true" buttons="#dlg-buttons" data-options="modal:true">
<form id="rechargeform" method="post" novalidate>
当前余额：<span id="cam">0.00</span>
<p/>
充值金额：<input name="money" id="money" class="easyui-validatebox" min="0" required="true" missingMessage="请填写充值金额！" validType="vermoneyNum">
<p/>
<a href="#" id="confirm" name="confirm" class="easyui-linkbutton" iconcls="icon-ok">确定</a>
<a href="#" id="cancel" name="cancel"class="easyui-linkbutton" iconcls="icon-cancel">取消</a>
</form>
</div>
<!--人工充值 层结束-->

<!--提现层的开始-->
<div id="withdraw-dialog" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px" closed="true" buttons="#dlg-buttons"data-options="modal:true">
<!-- 开户名：<input name="name" id="name" class="easyui-validatebox" required="true"><p/>-->
<h3>填写银行卡信息：</h3>
<form id="withdrawform" method="post" novalidate>
<table class="getMoney" style="width:80%;margin:0 auto; line-height:25px;">

<tr>
<td>选择银行：</td>
<td>
<input id="bankName" style="margin-left:-1px;" readonly="readonly" />
<!-- -- <select id="bankName" class="easyui-combobox" style="width:100px;height:20px;">
<option value="ICBC">工商银行</option>
<option value="ABC">农业银行</option>
</select>
-->
</td>
</tr>
<tr>
<td>银行卡号：</td>
<td><input type="text" name="bankNo" id="bankNo" class="easyui-validatebox" missingMessage="银行卡号" required="true" validType="verbankcard" style="margin-left: 50px"/></td>
</tr>
<tr>
<td>开户地区：</td>
<td>
<input id="areaName" style="margin-left:-1px;" readonly="readonly" />
</td>
<!-- <td>
<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="seachBtn" >查询</a>
</td> -->
</tr>
<tr>
<td>开户行：</td>
<td><input type="text" name="bankAdress" id="bankAdress" class="easyui-validatebox" missingMessage="开户行" required="true" style="margin-left: 50px"/></td>
</tr>
</table>
<h3 style="margin-left: 15px">填写提现信息：</h3>
<table class="getMoney" style="width:80%;margin:0 auto; line-height:25px; margin-left: 15px">
<tr>
<td>账户余额：</td>
<td><span id="wam"></span></td>
</tr>
<tr>
<td>提现金额：</td>
<td><input id="moneyNum" type="text" class="easyui-validatebox" required="true" missingMessage="提现金额" validType="vermoneyNum" /></td>
</tr>
<!-- <tr>
<td>手续费：</td>
<td><span id="fee">0</span>元</td>
</tr> -->
<tr>
<td>实际提现金额：</td>
<td><span id="awmoney">0.00</span>元</td>
</tr>
<tr>
<td colspan="2" style="text-align:center;">
<a id="wconfirm" class="easyui-linkbutton" iconcls="icon-ok">确定</a>
<a id="wcancel" class="easyui-linkbutton" iconcls="icon-cancel">取消</a>
<!--<input type="button" id="wconfirm" name="confirm" value="确定"/>
<input type="button" id="wcancel" name="cancel" value="取消"/>-->
</td>
</tr>
</table>
</form>
</div>

<!--提现层的结束-->
<div id="pointDetailDataGrid"></div>
<div id="pointToolBar">
<div class="fund" style="line-height:30px;">
可用资金：<span id="am">0.00</span> 冻结资金：<span id="fm">0.00</span>奖励资金：<span id="awm">0.00</span>
</div>
<table >
<tr>
<th>
操作类型：
<select id="optStatus"class="easyui-combobox" style="width:150px;">
<option value="0" SELECTED>全部</option>
<option value="1">充值</option>
<option value="10">提现冻结</option>
<option value="11">提现成功</option>
<option value="12">提现失败</option>
<option value="20">投标奖励资金冻结</option>
<option value="21">投标可用资金冻结</option>
<option value="22">投标成功</option>
<option value="23">投标失败退款，可用资金</option>
<option value="24">投标失败退款，奖励资金</option>
<option value="30">正常回收本金</option>
<option value="31">正常回收利息</option>
<option value="32">提前回收本金</option>
<option value="33">提前回收罚息</option>
<option value="34">逾期回收本金</option>
<option value="35">逾期回收利息</option>
<option value="36">逾期回收罚息</option>
<option value="37">坏帐回收本金</option>
<option value="38">坏帐回收利息</option>
<option value="39">坏帐回收罚息</option>
<option value="40">正常还款本金</option>
<option value="41">正常还款利息</option>
<option value="42">提前还款本金</option>
<option value="43">提前还款罚息</option>
<option value="44">逾期还款本金</option>
<option value="45">逾期还款利息</option>
<option value="46">逾期还款罚息</option>
<option value="47">坏帐还款本金</option>
<option value="48">坏帐还款利息</option>
<option value="49">坏帐还款罚息</option>
<option value="50">充值服务费</option>
<option value="51">提现服务费</option>
<option value="52">担保费用</option>
<option value="53">借款服务费</option>
<option value="54">风险金服务费</option>
<option value="55">平衡金</option>
<option value="56">加入理财计划服务费</option>
<option value="60">借款成功</option>
<option value="2">资金垫付</option>
<option value="3">奖励</option>
<option value="70">理财计划资金流入</option>
<option value="71">理财计划资金流出</option>
<option value="72">理财计划奖励消耗</option>
<option value="73">理财计划可用资金消耗</option>
<option value="74">理财计划回收利息</option>

</select>
</th>
<th style="width:100px;"></th>
<th>
<!-- <a href="#" id="searchLink" class="easyui-linkbutton" iconCls="icon-search">搜索</a>-->
<shiro:hasPermission name="A0703">
    <a href="#" id="withdrawLink" class="easyui-linkbutton" >人工提现</a>
</shiro:hasPermission>
<shiro:hasPermission name="A0702">
    <a href="#" id="rechargeLink" class="easyui-linkbutton" >人工充值</a>
</shiro:hasPermission>
<span id="rewardLinked" />
</th>
</tr>
</table>
</div>
<input type="hidden" id="wam_" />
<script>
$(function () {
//renderHeader();
//renderPointDataGrid();
/*
*[20140301]wangshaofen
*/

//alert("accountDetail");

$("#optStatus").combobox({
onSelect:function(record){
var val = $("#optStatus").combobox('getValue');
//alert(val);
onSelectOptStatus(val);
}
});


/* $("#optStatus").change(function(){
$(this).css("background-color","#FFFCCC");

onSelectOptStatus($(this));
}); */

$("#rechargeLink").click(function(){
click_recharge();
});

$("#rewardLink").click(function(){
click_reward();
});

$("#withdrawLink").click(function(){
click_withdraw();
});

$("#confirm").click(function(){
confirm();
});

$("#cancel").click(function(){
cancel();
});

//奖励充值
$("#rconfirm").click(function(){
rconfirm();
});

//奖励充值取消
$("#rcancel").click(function(){
rcancel();
});

$("#wconfirm").click(function(){
wconfirm();
});

$("#wcancel").click(function(){
wcancel();
});


$("#moneyNum").keyup(function(){
moneyShow();
});


//点击选择银行
$("#bankName").click(function(){
$("#chooseBank").dialog('open');
$("#definite").click(function(){
if($(".choose_bank input").is(":checked")){
var valbank= $(".choose_bank input:checked").val();
$("#bankName").val(valbank);
$("#chooseBank").dialog('close');
}else{
alert("请选择银行");
}

});

});
//地区
$("#areaName").click(function(){
$("#chooseArea").dialog('open');
$("#chooseArea a").click(function(){
event.preventDefault();
var area=$(this).html();
$("#areaName").val(area);


$("#chooseArea").dialog('close');

});
});

});

function moneyShow(){
var moneyNum = $("#moneyNum");
var awmoney = $("#awmoney");
awmoney.text(fixedNumber(parseFloat(moneyNum.val())));
// awmoney.text(moneyNum.val());
/* 含手续费
var moneyNum = $("#moneyNum");
var fee = $("#fee");
var awmoney = $("#awmoney");
var tmpfee = fixedNumber(moneyNum.val()*0.01);
if(!isNaN(tmpfee)){
fee.text(tmpfee);
}else{
fee.text(0);
}

if(!isNaN(tmpfee)){
awmoney.text(fixedNumber(moneyNum.val()-tmpfee));
}else{
awmoney.text(0);
} */
}

/* function NOShow(){
var bankNo=$("#bankNo");

if(!isNaN(bankNo.val())){

}else{
alert("请输入合法的银行卡号！")
}
} */

/**
* 提现确认
*
*/
function wconfirm() {

var bankName= $('#bankName') .val();
var bankAdress=$("#bankAdress").val();
var bankNo = $("#bankNo").val();
var withDrawMoney=$("#moneyNum").val();
var _memo={};
var data ={userId:mUserId,bankName:bankName,bankAdress:bankAdress,bankNo:bankNo,withDrawMoney:withDrawMoney,memo:_memo};
var url = "<%=basePath%>account/withdrawals";
var wam=$('#wam_').val();
if((wam-withDrawMoney)<0){
$.messager.alert('提示',"账户余额不足！");
return -1;
}
if($("#withdrawform").form('validate')){

$.ajax({
data: data,
url: url,
type:"post",
success: function(result){
var result = eval('('+result+')');
if (result.code === 1){
renderHeader();
renderPointDataGrid();
cancel();
/* $.messager.show({
title: 'trips',
msg: "提现成功"
}); */
$.messager.alert('成功',result.message);
$('#withdraw-dialog').dialog('close');

} else {
/* $.messager.show({
title: 'trips',
msg: "提现失败！"
}); */
$.messager.alert('失败',result.message);
}
cancel();
}
});

}
}
/**
*
* 提现取消
*/
function wcancel() {
$('#withdraw-dialog').dialog('close');
}
/**
* 确认充值
*/
function confirm() {

var money = $("#money").val();
if(money>10000000){//最多只能冲1千万
alert("最多只能一次充值1千万");
return;
}

var url = "<%=basePath%>account/recharge";
var data = {userId:mUserId,money:money};
if($(rechargeform).form('validate')){
$.ajax({
data: data,
url: url,
success: function(result){
var result = eval('('+result+')');
if (result.code === 1){
renderHeader();
renderPointDataGrid();
$.messager.alert('成功',"充值成功");
/* $.messager.show({
title: '成功',
msg: "充值成功"
}); */

} else {
$.messager.alert('失败',"充值失败");
/* $.messager.show({
title: '失败',
msg: "充值失败！"
}); */
}
cancel();
}
});
}

}


/**
* 确认取消
*/
function cancel() {
$('#recharge-dialog').dialog('close');
}


/**
* 确认奖励充值
*/
function rconfirm() {
var url = "<%=basePath%>account/reward";
var reward = $("#reward").val();
var data = {userId:mUserId,reward:reward};
if($(rewardform).form('validate')){
$.ajax({
data: data,
url: url,
success: function(result){
var result = eval('('+result+')');
if (result.code === 1){
renderHeader();
renderPointDataGrid();
$.messager.alert('成功',"奖励充值成功");
/* $.messager.show({
title: '成功',
msg: "充值成功"
}); */

} else {
$.messager.alert('失败',"奖励充值失败");
/* $.messager.show({
title: '失败',
msg: "充值失败！"
}); */
}
rcancel();
}
});
}

}


/**
* 确认奖励取消
*/
function rcancel() {
$('#reward-dialog').dialog('close');
}
/**
* 选择加载
*/
function onSelectOptStatus(selectObj) {
var type = selectObj;
var data = { userId:mUserId,type:type}
$('#pointDetailDataGrid').datagrid('reload',data);
}

function click_recharge() {

$('#recharge-dialog').dialog('open').dialog("setTitle","人工充值");
$('#rechargeform').form('clear');
}

function click_reward() {
$('#reward-dialog').dialog('open').dialog("setTitle","奖励充值");
$('#rewardform').form('clear');
}

function click_withdraw() {
$('#withdrawform').form('clear');
$('#fee').text(0.00);
$('#awmoney').text(0.00);
$('#withdraw-dialog').dialog('open').dialog("setTitle","人工提现");

}


function doSearch() {
alert(1);
}


/**
*
* 渲染表头
*
*/
function renderHeader() {
var url = "<%=basePath%>account/getPointInfo";
var data = {userId:mUserId};
$.ajax({
data: data,
url: url,
success: function(result){
var result = eval('('+result+')');
if (result.errorMsg){
$.messager.alert('失败',result.errorMsg);
/* $.messager.show({
title: 'Error',
msg: result.errorMsg
}); */
} else {
var am=fixedNumber(result.am);
var fm=fixedNumber(result.fm);
var awm=fixedNumber(result.awm);
$("#wam").text(am);
$("#cam").text(am);
$("#am").text(am);
$("#fm").text(fm);
$("#awm").text(awm);
$("#wam_").val(am);
}
}
});
}
/**
* 渲染用户表格
*
*/
function renderPointDataGrid() {
var url = "<%=basePath%>account/queryPointDetailList";
var data = {userId:mUserId,type:0};
$("#pointDetailDataGrid").datagrid({
url: url,
queryParams:data,
loadMsg: "loading data...",
pagination: true,
rownumbers: false,

columns: [
[
{field: 'id', title: 'ID', align: 'center', width: 120},
{field: 'businessId', title: '相关交易号', align: 'center', width: 100},
{field: 'createTime', title: '交易时间', align: 'center', width: 120},
{field: 'typeName', title: '操作类型', align: 'center', width: 100},
{field: 'changeMoney', title: '金额', align: 'center', width: 100,formatter:fixedNumber},
{field: 'afterAvailableMoney', title: '可用余额', align: 'center', width: 100,formatter:fixedNumber},
{field: 'freezeMoney', title: '冻结余额', align: 'center', width: 100, formatter:getFreezeMoney},
{field: 'afterAwardMoney', title: '奖励余额', align: 'center', width: 100,formatter:fixedNumber}
]
],
onLoadSuccess:function(data){
if (data.errorMsg){
$.messager.alert('错误',data.errorMsg);

/* $.messager.show({
title: 'Error',
msg: data.errorMsg
}); */
}else{
if(data.showFlag){
var $rewardLink = $('<a href="#" id="rewardLink" class="easyui-linkbutton l-btn" group ><span class="l-btn-left"><span class="l-btn-text">奖励充值</span></span></a>');
$rewardLink.click(click_reward);
$('#rewardLinked').html($rewardLink);
}
}
},
onLoadError:function(){
$.messager.alert('错误',"错误");
/* $.messager.show({
title: 'Error',
msg: "错误"
}); */
},
toolbar: "#pointToolBar"
});
//设置分页控件
var p = $('#pointDetailDataGrid').datagrid('getPager');
$(p).pagination({
pageSize: 10,//每页显示的记录条数，默认为10
pageList: [10,20,30,40,50],//可以设置每页记录条数的列表
beforePageText: '第',//页数文本框前显示的汉字
afterPageText: '页 共 {pages} 页',
displayMsg: '当前显示 {from} - {to} 条记录 共 {total} 条记录',
/*onBeforeRefresh:function(){
$(this).pagination('loading');
alert('before refresh');
$(this).pagination('loaded');
}*/
});
}

function getFreezeMoney(value,row,index){
var freezeMoney= row.afterWithdrawFreezeMoney + row.afterBidFreezeMoney;
return fixedNumber(freezeMoney);
}
</script>
