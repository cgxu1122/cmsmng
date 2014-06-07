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
        function getNoActiveReason() {
            var active = document.getElementById("active").value;
            var noActiveReason = document.getElementById("noActiveReason");
            if (active == '0') {
                noActiveReason[0] = new Option("选择全部", "");
                noActiveReason[1] = new Option("无效替换", "2");
                noActiveReason[2] = new Option("无效卸载", "3");
            } else {
                noActiveReason.length = 1;
                noActiveReason[0] = new Option("选择全部", "");
            }

        }


        function dispArriveImei(index) {
            $('#dg').datagrid('selectRow', index);
            var row = $('#dg').datagrid('getSelected');
            var countTime = row.countTime;
            var deviceCode = row.deviceCode;
            var modleName = row.modleName;
            var channelId = row.channelId;
            var active = row.active;
            window.open(basePath + "/reportCount/downArriveImei?countTime=" + countTime + "&modleName=" + modleName + "&channelId=" + channelId + "&active=" + active + "&groupId=1");
        }
        function dispUploadImei(index) {
            $('#dg').datagrid('selectRow', index);
            var row = $('#dg').datagrid('getSelected');
            var countTime = row.countTime;
            var deviceCode = row.deviceCode;
            var modleName = row.modleName;
            var channelId = row.channelId;
            window.open(basePath + "/reportCount/downUploadImei?countTime=" + countTime + "&modleName=" + modleName + "&channelId=" + channelId + "&groupId=1");
        }
        function searchEvt() {
            var modleName = $('#modleName').val();
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            var channelId = $('#channelId').val();
            var active = $('#active').val();
            var noActiveReason = $('#noActiveReason').val();
            $('#dg').datagrid({
                url: basePath + "/reportCount/warehouseQueryList",
                queryParams: {modleName: modleName, startDate: startDate, endDate: endDate, channelId: channelId, active: active, noActiveReason: noActiveReason, groupId: 1}
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
                        {field: 'modleName', title: '机型', align: 'center', width: 150},
                        {field: 'channelName', title: '仓库名称', align: 'center', width: 150},
                        {field: 'processDayCount', title: '装机数量', align: 'center', width: 150},
                        {field: 'arriveCount', title: '装机到达数量', align: 'center', width: 150,
                            formatter: function (value, row, index) {
                                return '<a href="#" onclick="dispArriveImei(' + index + ')">' + value + '</a>';
                            }
                        },
                        {field: 'deviceUploadDayCount', title: '累计到达数量', align: 'center', width: 150,
                            formatter: function (value, row, index) {
                                return '<a href="#" onclick="dispUploadImei(' + index + ')">' + value + '</a>';
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
                    <input type="text" name="modleName" id="modleName" placeholder="机型全称"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="channelId" id="channelId" placeholder="仓库名称"/>
                </td>
            </tr>
            <tr>
                <td>

                    <select name="active" id="active" onchange="getNoActiveReason()">
                        <option value="">选择全部</option>
                        <option value="1">有效到达</option>
                        <option value="0">无效到达</option>
                    </select>
                    <select id="noActiveReason" name="noActiveReason">
                        <option value="">选择全部</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="startDate" id="startDate" placeholder="开始时间"/>
                </td>
                <td>
                    <input type="text" name="endDate" id="endDate" placeholder="结束时间"/>
                </td>
            </tr>
            <tr>
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