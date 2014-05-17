<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .loan-count {
        width: 100%;
        background: #ddd;
        margin-top: 10px;
    }

    .loan-count td {
        background: #eee;
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
<div class="easyui-tabs" style="width:850px;height:328px">
    <div title="借入统计" style="padding:10px">
        <table class="loan-count">
            <tr>
                <td><span>总借款金额</span></td>
                <td><span id="totalLoanAmount"></span></td>
                <td><span>待还本息金额</span></td>
                <td><span id="waitInterestAmount"></span></td>
                <td><span>已还本息金额</span></td>
                <td><span id="asloInterestAmount"></span></td>
            </tr>
        </table>
        <table class="loan-count">
            <tr>
                <td><span>总借款次数</span></td>
                <td><span id="totalLoanNum"></span></td>
                <td><span>总成功借款次数</span></td>
                <td><span id="totalLoanNumSucc"></span></td>
                <td><span>正常还清期数</span></td>
                <td><span id="normalNum"></span></td>
            </tr>
            <tr>
                <td><span>未还清期数</span></td>
                <td><span id="abnormalNum"></span></td>
                <td><span></span></td>
                <td></td>
                <td><span></span></td>
                <td></td>
            </tr>
        </table>
        <table class="loan-count">
            <tr>
                <td><span>逾期期数</span></td>
                <td><span id="overdueNum"></span></td>
                <td><span>逾期还清期数</span></td>
                <td><span id="overdueNumAslo"></span></td>
                <td><span>逾期本息总额</span></td>
                <td><span id="overdueInterestAmount"></span></td>
            </tr>
            <tr>
                <td><span>待付逾期费用</span></td>
                <td><span id="overdueWaitAmount"></span></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </div>
    <div title="借入记录" style="padding:10px">
        <table id="loanborrow" class="easyui-datagrid">
            <thead>
            <tr>
                <th data-options="field:'id',width:100">ID</th>
                <th data-options="field:'title',width:100">名称</th>
                <th data-options="field:'amount',width:100,formatter:fixedNumber">借入金额</th>
                <th data-options="field:'insterest',width:100">利率</th>
                <th data-options="field:'period',width:100">期限</th>
                <th data-options="field:'passTime',width:100">放款日期</th>
                <th data-options="field:'statusName',width:100">状态</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script>

    // 借入记录页面初始化
    function borrowIndexInit(b_userid) {
        renderLoanborrowStatistics(b_userid); // 借入统计
        renderLoanborrow(b_userid); // 借入记录
    }

    /**
     * [借入统计]初始化
     */
    function renderLoanborrowStatistics(b_userid) {
        var url = '<%=basePath%>account/getBorrowStatistics?userId=' + b_userid;
        var data = {userId: b_userid};
        $.ajax({
            url: url,
            data: data,
            success: function (result) {
                var result = $.parseJSON(result);
                if (result.errorMsg) {
                    $.messager.alert('错误', result.errorMsg);

                } else {
                    for (var k in result) {
                        $("#" + k).text(result[k]);
                    }
                }
            }
        });
    }

    /**
     * [借入记录]初始化
     */
    function renderLoanborrow(b_userid) {
        $('#loanborrow').datagrid({url: '<%=basePath%>account/getBorrowRecord?userId=' + b_userid});
    }
</script>