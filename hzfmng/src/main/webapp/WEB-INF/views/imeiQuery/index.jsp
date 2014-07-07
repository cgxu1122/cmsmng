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
            initPage();
        });

        function searchEvt() {
            var value = $('#searchValue').val();
            $('#dg').datagrid({
                url: "<%=basePath%>/hzfmng/imeiQuery/list",
                queryParams: {apkNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/hzfmng/imeiQuery/list',
                queryParams: {},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'imei', title: 'imei', align: 'center', width: 150},
                        {field: 'modelName', title: '机型', align: 'center', width: 200},
                        {field: 'channelName', title: '安装仓', align: 'center', width: 400},
                        {field: 'processDate', title: '安装日期', align: 'center', width: 140,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'uploadDate', title: '上传日期', align: 'center', width: 140,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
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
                    <span><font color="red">*导入文件为Excel文件，Imei最大查询数量为3000条</font></span>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">导入imei查询</a>
                </td>
                <td align="center">
                </td>
                <td align="center">
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delrow()">删除</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#dlg-buttons">
    <div class="ftitle">apk</div>
    <br/>

    <form id="fm" method="post" enctype="multipart/form-data" novalidate>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>产品名称:</label>
            <input id="apkName" name="apkName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>上传文件:</label>
            <input type="file" name="file"/>
        </div>
        <div class="fitem">
            <label>是否计数器:</label>
            <select class="easyui-combobox" name="type" id="type" style="width:150px;">
                <option value="1">否</option>
                <option value="2">是</option>
            </select>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#update-buttons">
    <form id="upfm" method="post" enctype="multipart/form-data" novalidate>
        <input type="hidden" id="apkId" name="apkId"/>

        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>产品名称:</label>
            <input type="text" name="apkName" class="easyui-validatebox" required="true"
                    >
        </div>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>上传文件:</label>
            <input type="file" name="file"/>
        </div>
        <div class="fitem">
            <label>是否计数器:</label>
            <select class="easyui-combobox" name="type" id="upType" style="width:150px;">
                <option value="1">否</option>
                <option value="2">是</option>
            </select>
        </div>
</div>
</form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">关闭</a>
</div>

</body>
</html>