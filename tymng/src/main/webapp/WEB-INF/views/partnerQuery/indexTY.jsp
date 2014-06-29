<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>
    <title>Demo</title>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#startDate").datebox({
                value: getCurrrentDateStr()
            });
            $("#endDate").datebox({
                value: getCurrrentDateStr()
            });
            initPage();
        });
        function searchEvt() {
            var startDate = $('#startDate').datebox('getValue');
            var endDate = $('#endDate').datebox('getValue');
            var ua = $('#ua').val();
            $('#dg').datagrid({
                url: "<%=basePath%>/tymng/partnerQuery/listLogStat",
                queryParams: {groupId: 1, startDate: startDate, endDate: endDate, ua: ua}
            });
        }

        function initPage() {
            var startDate = $('#startDate').datebox('getValue');
            var endDate = $('#endDate').datebox('getValue');
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/partnerQuery/listLogStat',
                queryParams: {groupId: 1, startDate: startDate, endDate: endDate},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'processDate', title: '日期', align: 'center', width: 200,
                            formatter: function (value) {
                                if (value == null) {
                                    return "合计"
                                }
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'deviceCode', title: '设备编码', align: 'center', width: 200},
                        {field: 'channelName', title: '仓库名称', align: 'center', width: 300},
                        {field: 'modelName', title: '机型名称', align: 'center', width: 200},
                        {field: 'devicePrsDayNum', title: '装机数量', align: 'center', width: 200,
                            formatter: function (value, row, index) {
                                //return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "')>"+value+"</a>";
                                return "<a href='javascript:void(0)'>" + value + "</a>";
                            }
                        }
                    ]
                ]
            });
        }
        function showModelDialog() {
            $('#modeldlg').dialog('open').dialog('setTitle', '选择机型');
            $('#modeldg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/modelInfo/list',
                queryParams: {groupId: 1},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'modelName', title: '机型名称', align: 'center', width: 150},
                        {field: 'ua', title: 'UA', align: 'center', width: 150},
                        {field: 'action', title: '操作', align: 'center', width: 100,
                            formatter: function (value, row, index) {
                                return "<a href='javascript:void(0)' onclick=javascript:selectModel('" + row.ua + "','" + row.modelName + "')>选择</a>";
                            }
                        }
                    ]
                ]
            });
        }
        function searchModelEvt() {
            var value = $('#searchModelValue').val();
            $('#modeldg').datagrid({
                url: "<%=basePath%>/tymng/modelInfo/list",
                queryParams: {modelNameCondition: value, groupId: 1}
            });
        }
        function selectModel(ua, modelName) {
            $("#modelName").val(modelName);
            $("#ua").val(ua);
            $('#modeldlg').dialog('close');
        }
        function exportData() {
            var startDate = $('#startDate').datebox('getValue');
            var endDate = $('#endDate').datebox('getValue');
            var ua = $('#ua').val();
            window.location.href = "<%=basePath%>/tymng/partnerQuery/exportData?groupId=1&startDate=" + startDate + "&endDate=" + endDate + "&ua=" + ua;
        }
    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="modelName" id="modelName" placeholder="选择机型" readonly="readonly"
                           onclick="showModelDialog()"/>
                    <input type="hidden" name="ua" id="ua"/>
                </td>
                <td>
                    <input type="text" name="startDate" id="startDate" placeholder="开始时间"/>
                </td>
                <td>
                    <input type="text" name="endDate" id="endDate" placeholder="结束时间"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">查询</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportData()">导出</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="modeldlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     buttons="#modeldlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchModelValue" id="searchModelValue" placeholder="机型名称"/>
                    </td>
                    <td align="center">
                        <a id="searchModelBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchModelEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="modeldg"></div>
</div>
<div id="modeldlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#modeldlg').dialog('close')">关闭</a>
</div>
</body>
</html>