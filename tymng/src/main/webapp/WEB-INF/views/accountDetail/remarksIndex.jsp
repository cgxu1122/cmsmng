<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--添加跟踪记录 层开始-->
<div id="remarks-dialog" class="easyui-dialog" style="width:550px;height:300px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">

    <div>
        <textarea type="" id="remarks" name="remarks" style="width: 490px; height: 138px;resize:none"></textarea>
    </div>
    <div style="text-align: center;margin-top: 20px;">
        <a id="btn-confirm" class="easyui-linkbutton" iconcls="icon-ok">确定</a>
        <a id="btn-cancel" class="easyui-linkbutton" iconcls="icon-cancel">取消</a>
        <!--<input type="button" id="btn-confirm" name="btn-confirm" value="确定"/>
        <input type="button" id="btn-cancel" name="btn-cancel" value="取消"/>-->
    </div>
</div>
<!--添加跟踪记录 层结束-->


<div id="toolBar">
    <table>
        <tr>
            <th>
                备注人：<input type="text" id="remarkName"/>
            </th>
            <th>
                开始时间: <input id="startTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"
                             style="width: 120px"/>
                结束时间: <input id="endTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"
                             style="width: 120px"/>
            </th>
            <th>
                <a href="#" id="searchLink" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                <a href="#" id="addLink" class="easyui-linkbutton" iconCls="icon-add">添加</a>
            </th>
        </tr>
    </table>
</div>
<div id="remarkDataGrid"></div>

<script>
    $(function () {

        renderRemarksDataGrid();


        $("#searchLink").click(function () {
            doSearch();
        });
        $("#addLink").click(function () {
            showPop();
        });

        $("#btn-confirm").click(function () {
            var remarks = $("#remarks").val();
            if (remarks.length > 125) {
                $.messager.alert('提示', "备注信息长度过长，超出合理范围！");
                return -1;
            }
            else if (remarks == null || remarks == "") {
                $.messager.alert('提示', "备注信息不能为空！");
                return -1;
            } else {
                click_confirm();
            }

        });

        $("#btn-cancel").click(function () {
            click_cancel();
        });


    })

    function click_confirm() {
        url = "<%=basePath%>account/addRemark";
        var data = {
            userId: mUserId,
            content: $("#remarks").val()
        }
        $.post(url, data, function (resp) {
            if (resp.code == -1) {
                $.messager.alert('错误', "跟踪信息添加失败！");
                /* $.messager.show({
                 title: 'Error',
                 msg: '跟踪信息添加失败！'
                 }); */
            } else {
                $.messager.alert('成功', "跟踪信息添加成功！");
                /*  $.messager.show({
                 title: '成功',
                 msg: '跟踪信息添加成功！'
                 }); */
            }

            click_cancel();
            reload();

        }, "json");
    }

    function reload() {
        doSearch()

    }

    function click_cancel() {
        $('#remarks-dialog').dialog('close');
    }
    function doSearch() {

        var remarkName = $("#remarkName").val();
        var startTime = $('#startTime').datebox("getValue");
        var endTime = $('#endTime').datebox("getValue");
        if (startTime != "" && endTime != "") {

            startDate = new Date(startTime);
            endDate = new Date(endTime);
            if ((endDate - startDate) <= 0) {
                $.messager.alert('提示', "结束时间必须大于开始时间！");
                return -1;
            }


        } else if (startTime == "" && endTime == "") {

        } else {
            $.messager.alert('提示', "请填写开始时间或者结束时间！");
            return;
        }

        var data = { userId: mUserId,
            staffName: remarkName,
            startTime: startTime,
            endTime: endTime};
        $('#remarkDataGrid').datagrid('reload', data);

    }
    function showPop() {

        $('#remarks-dialog').dialog('open').dialog("setTitle", "添加备注");
        $('#remarks-dialog').form("clear");
    }

    /**
     * 渲染用户表格
     *
     */
    function renderRemarksDataGrid() {
        var url = "<%=basePath%>account/queryRemarkList?userId=" + mUserId;
        $("#remarkDataGrid").datagrid({
            width: 880,
            height: 440,
            url: url,
            loadMsg: "loading data...",
            pagination: false,
            rownumbers: false,
            pageList: [1, 10, 20],
            columns: [
                [
                    {field: 'staffName', title: '备注人', align: 'center', width: 120},
                    {field: 'comment', title: '备注内容', align: 'center', width: 600},
                    {field: 'createTime', title: '备注时间', align: 'center', width: 150}
                ]
            ],
            onLoadSuccess: function (data) {
                if (data.errorMsg) {
                    $.messager.alert('错误', data.errorMsg);
                    /*  $.messager.show({
                     title: 'Error',
                     msg: data.errorMsg
                     }); */
                }
            },
            onLoadError: function () {
                $.messager.alert('错误', "错误");
                /*  $.messager.show({
                 title: 'Error',
                 msg: "错误"
                 }); */
            },
            toolbar: "#toolBar"
        })
    }


</script>
