<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .td-wid {
        width: 100px;
    }

    .info-table table {
        padding: 20px;
        border-bottom: 1px solid #000;
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
            <td rowspan="7" style="width:500px;background:#eee"></td>
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
            <td class="td-wid">最&nbsp;&nbsp;高&nbsp;&nbsp;学&nbsp;&nbsp;历：</td>
            <td><span id="education"></span></td>
        </tr>
        <tr>
            <td class="td-wid">注&nbsp;&nbsp;册&nbsp;&nbsp;渠&nbsp;&nbsp;道：</td>
            <td><span id="channelName"></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class="td-wid">婚&nbsp;&nbsp;姻&nbsp;&nbsp;状&nbsp;&nbsp;况：</td>
            <td><span id="maritalStatus"></span></td>
            <td class="td-wid">住&nbsp;&nbsp;房&nbsp;&nbsp;性&nbsp;&nbsp;质：</td>
            <td><span id="houseType"></span></td>
        </tr>
        <tr>
            <td class="td-wid">户&nbsp;口&nbsp;所&nbsp;在&nbsp;地：</td>
            <td><span id="homeAddress"></span></td>
            <td class="td-wid">居&nbsp;&nbsp;住&nbsp;&nbsp;地&nbsp;&nbsp;址：</td>
            <td><span id="liveAddress"></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class="td-wid">公&nbsp;&nbsp;司&nbsp;&nbsp;名&nbsp;&nbsp;称：</td>
            <td><span id="companyName"></span></td>
            <td class="td-wid">公&nbsp;&nbsp;司&nbsp;&nbsp;电&nbsp;&nbsp;话：</td>
            <td><span id="companyPhone"></span></td>
        </tr>
        <tr>
            <td class="td-wid">公&nbsp;&nbsp;司&nbsp;&nbsp;性&nbsp;&nbsp;质：</td>
            <td><span id="companyNature"></span></td>
            <td class="td-wid">现单位工作年限：</td>
            <td><span id="workYears"></span></td>
        </tr>
        <tr>
            <td class="td-wid">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
            <td><span id="jobTitle"></span></td>
            <td class="td-wid">月&nbsp;&nbsp;&nbsp;&nbsp;收&nbsp;&nbsp;&nbsp;&nbsp;入：</td>
            <td><span id="income"></span></td>
        </tr>
        <tr>
            <td class="td-wid">公&nbsp;&nbsp;司&nbsp;&nbsp;地&nbsp;&nbsp;址：</td>
            <td><span id="companyAddress"></span></td>
        </tr>
    </table>
</form>
<script>

    /**
     * 格式化贷款用户工作年限
     */
    function fixedWorkYears(t) {
        if (-1 == t.toFixed(1).toString().indexOf(".")) {
            return (t.toFixed(1));
        } else {
            t = (t.toFixed(1).toString().replace(/(.+?)(0{1,})$/, "$1").replace(/(.+)\.$/, "$1"));
            return parseFloat(t).toFixed(1);
        }
    }


    /**
     * 加载数据
     */
    function renderDivData() {
        var url = "<%=basePath%>account/getUserInfo";
        var data = {userId: mUserId};
        //alert(123);
        $.ajax({
            url: url,
            data: data,
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.errorMsg) {
                    $.messager.alert('错误', result.errorMsg);
                    /*  $.messager.show({
                     title: 'Error',
                     msg: result.errorMsg
                     }); */
                } else {
                    $("#userName").text(result.nickname);
                    $("#realName").text(result.realName);
                    var gender = result.gender;
                    var realGender = "";
                    if (gender == 1) {
                        realGender = "男";
                    } else {
                        realGender = "女";
                    }
                    $("#sex").text(realGender);
                    var idNo = result.idNo;
                    var realIdNo = idNo.split("_");
                    $("#idCard").text(realIdNo[0]);
                    $("#email").text(result.email);
                    $("#mobile").text(result.mobile);
                    $("#channelName").text(result.channelName);
                    $("#education").text(result.education);
                    $("#maritalStatus").text(result.maritalStatus);
                    $("#houseType").text(result.houseType);

                    var homeAddress = (!!result.homeProvince ? result.homeProvince : "") + (!!result.homeCity ? result.homeCity : "") + (!!result.homeAddress ? result.homeAddress : "");

                    $("#homeAddress").text(!!homeAddress ? homeAddress : "");


                    var liveAddress = (!!result.liveProvince ? result.liveProvince : "") + (!!result.liveCity ? result.liveCity : "") + (!!result.liveAddress ? result.liveAddress : "");

                    $("#liveAddress").text(!!liveAddress ? liveAddress : "");

                    $("#companyName").text(result.companyName);
                    $("#companyPhone").text(result.companyPhone);
                    $("#companyNature").text(result.companyNature);
                    /*  var workYears="";
                     if(result.workYears!=null || result.workYears!=""){
                     workYears=fixedWorkYears(parseFloat(result.workYears));
                     } */
                    $("#workYears").text(result.workYears);
                    $("#jobTitle").text(result.jobTitle);
                    $("#income").text(result.income);

                    var companyAddress = (!!result.workProvince ? result.workProvince : "") + (!!result.workCity ? result.workCity : "") + (!!result.companyAddress ? result.companyAddress : "");
                    $("#companyAddress").text(!!companyAddress ? companyAddress : "");
                }
            }
        });
    }

    renderDivData();
</script>
