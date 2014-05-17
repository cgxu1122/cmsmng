<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .loan-count {
        width: 100%;
        background: #ddd;
        margin-top: 10px;
    }

    .loan-count td {
        background: #fff;
        width: 2%;
        height: 30px;
        line-height: 30px;
    }

    .loan-count td span {
        background: #eee;
        height: 30px;
        display: block;
        padding-left: 10px;
    }
</style>

<div class="easyui-tabs" style="width:850px;height:500px">
    <div title="借出统计" style="padding:10px">
        <table class="loan-count">
            <tr>
                <td><span>总借出金额</span></td>
                <td><span id="lendMoneyTotal"></span></td>
                <td><span>总借出笔数</span></td>
                <td colspan="3"><span id="totalCnt"></span></td>
            </tr>
            <!--
            <tr>
                 <td><span>待回收笔数</span></td>
                 <td><span id="dueinCnt"></span></td>
                 <td><span>已回收笔数</span></td>
                 <td colspan="3"><span id="receivedCnt"></span></td>
            </tr>
             -->
            <tr>
                <td><span>已回收本息</span></td>
                <td><span id="moneyReceivedTotal"></td>
                <td><span>待回收本息</span></td>
                <td colspan="3"><span id="moneyDueinTotal"></td>
            </tr>
            <tr>
                <td><span>已赚利息</span></td>
                <td><span id="intrestReceivedTotal"></td>
                <!--
                <td><span>已赚提前还款罚息</span></td>
                <td><span id="penalizedReceivedEarly"></td>
                <td><span>已赚罚息</span></td>
                <td><span id="penalizedEarned"></td>
                 -->
            </tr>
        </table>
    </div>
    <div title="散标借出记录" style="padding:10px">
        <table id="loanmess" class="easyui-datagrid">
            <thead>
            <tr>
                <th data-options="field:'id',width:100">ID</th>
                <th data-options="field:'title',width:100">名称</th>
                <th data-options="field:'userName',width:100">借款人</th>
                <th data-options="field:'period',width:100">期限</th>
                <th data-options="field:'insterest',width:100">年利率</th>
                <th data-options="field:'amount',width:100">借出金额</th>
                <th data-options="field:'createTime',width:100">借出日期</th>
                <th data-options="field:'statusName',width:100">状态</th>
            </tr>
            </thead>
        </table>
    </div>
    <div title="理财计划记录">
        <table id="financePlan" class="easyui-datagrid">
            <thead>
            <tr>
                <th data-options="field:'planId',width:100">ID</th>
                <th data-options="field:'issue',width:100">期数</th>
                <th data-options="field:'amount',width:100,formatter:fixedNumber">加入金额</th>
                <th data-options="field:'minInsterest',width:100">利率上限</th>
                <th data-options="field:'maxInsterest',width:100">利率下限</th>
                <th data-options="field:'period',width:100">锁定期时长</th>
                <th data-options="field:'insterest',width:100">锁定期到期时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script type="text/javascript" src="<%= basePath %>/common/js/numberFormat.js"></script>
<script>
    // userid_parent 从父窗体传递过来的用户ID
    function renderLendIndexInit(userid_parent) {
        renderDivData(userid_parent); 	// 借出统计
        renderLoanMess(userid_parent);	// 散标借出记录
        financePlanList(userid_parent);	// 理财计划记录
    }

    /**
     * [借出统计]记录初始化
     */
    function renderDivData(userid_parent) {
        var url = "<%=basePath%>account/getUserStaticstcsInfo";
        var data = {userId: userid_parent};
        $.ajax({
            url: url,
            data: data,
            success: function (result) {
                var result = $.parseJSON(result);
                if (result.errorMsg) {
                    $.messager.alert('错误', result.errorMsg);
                    var result = $.parseJSON(result);
                }
                if (result.code == -1) {
                    $.messager.alert('错误 ', result.message);
                } else {
                    for (var k in result) {
                        $("#" + k).text(result[k]);
                    }
                }
            }
        });
    }

    /**
     * [散标借出记录]初始化
     */
    function renderLoanMess(userid_parent) {
        $('#loanmess').datagrid({url: '<%=basePath%>account/getLengingRecord?userId=' + userid_parent});
    }

    /**
     * [理财计划记录]初始化
     */
    function financePlanList(userid_parent) {
        $('#financePlan').datagrid({url: '<%=basePath%>account/getPlanRecord?userId=' + userid_parent});
    }

</script>
