<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .td-wid {
        width: 100px;
    }

    .info-table table {
        padding: 20px;
        width: 98%;
    }

    .info-table table tr {
        height: 20px;
        line-height: 20px;
    }

</style>
<form class="info-table">
    <table>
        <tr>
            <td class="td-wid"> 用&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
            <td><span id="userName"></span></td>
            <td rowspan="7" style="width:500px;background:#eee">
                <img id="headimage" src=""/>
            </td>
        </tr>
        <tr>
            <td class="td-wid">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
            <td><span id="realName"></span></td>
        </tr>
        <tr>
            <td class="td-wid"> 性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
            <td><span id="sex"></span></td>
        </tr>
        <tr>
            <td class="td-wid"> 身&nbsp;&nbsp;&nbsp;&nbsp;份&nbsp;&nbsp;&nbsp;&nbsp;证：</td>
            <td><span id="idCard"></span></td>
        </tr>
        <tr>
            <td class="td-wid">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
            <td><span id="email"></span></td>
        </tr>
        <tr>
            <td class="td-wid">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td><span id="mobile"></span></td>
        </tr>
        <tr>
            <td class="td-wid">注&nbsp;&nbsp;册&nbsp;&nbsp;渠&nbsp;&nbsp;道：</td>
            <td><span id="channelName"></span></td>
        </tr>
    </table>
</form>
<script>

    /**
     * 加载数据
     */
    function renderDivData() {
        var url = "<%=basePath%>account/getUserInfo";
        var data = {userId: mUserId};
        $.ajax({
            url: url,
            data: data,
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.errorMsg) {

                    $.messager.alert('Error', result.errorMsg);

                } else {

                    var head_image = result.headImage;
                    var src = "data:image/png;base64," + head_image;
                    $('#headimage').attr("src", src);
                    var n = result.nickname;
                    $("#userName").text(result.nickname);
                    $("#realName").text(result.realName);
                    var sex = result.gender;
                    if (sex == 1) {
                        $("#sex").text("男");
                    }
                    if (sex == 2) {
                        $("#sex").text("女");
                    }

                    $("#idCard").text(result.idNo);
                    $("#email").text(result.email);
                    $("#mobile").text(result.mobile);
                    //$("#regWay").text(result.utmSource);

                    //注册渠道名称


                    $("#channelName").text(result.channelName);

                    //注册渠道
                    var uType = result.uType;
                    //alert(uType);
                    $("#uType").text(result.uType);


                }
            }
        });
    }

    renderDivData();
</script>
