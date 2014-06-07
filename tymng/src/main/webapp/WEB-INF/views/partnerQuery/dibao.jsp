<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>
    <title>Demo</title>
    <script type="text/javascript" src="<%= basePath %>/common/js/validateExtends.js"></script>
    <script type="text/javascript">
        var basePath = "<%= basePath %>";
        $(document).ready(function () {
            initPage();
        });

        function dispImea(index) {
            $('#dg').datagrid('selectRow', index);
            var row = $('#dg').datagrid('getSelected');

            var countTime = row.countTime;
            var deviceCode = row.deviceCode;
            var modleName = row.modleName;
            var channelId = row.channelId;
            window.open(basePath + "/logCount/downImea?countTime=" + countTime + "&deviceCode=" + deviceCode + "&modleName=" + modleName + "&channelId=" + channelId + "&groupId=1");
        }
        function searchEvt() {
            var modleName = $('#modleName').val();
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();

            $('#dg').datagrid({
                url: basePath + "/logCount/partnerQueryList",
                queryParams: {modleName: modleName, startDate: startDate, endDate: endDate, groupId: 2}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '',
                queryParams: {},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'countTime', title: '日期', align: 'center', width: 100,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'deviceCode', title: '设备编码', align: 'center', width: 300},
                        {field: 'channelId', title: '渠道ID', align: 'center', width: 300, hidden: 'true'},
                        {field: 'channelName', title: '仓库名称', align: 'center', width: 300},
                        {field: 'modleName', title: '机型', align: 'center', width: 150},
                        {field: 'processDayCount', title: '装机数量', align: 'center', width: 150,
                            formatter: function (value, row, index) {
                                return '<a href="#" onclick="dispImea(' + index + ')">' + value + '</a>';
                            }
                        }
                    ]
                ]
            });
        }

    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="modleName" id="modleName" placeholder="机型名称"/>
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
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>


</body>
</html>