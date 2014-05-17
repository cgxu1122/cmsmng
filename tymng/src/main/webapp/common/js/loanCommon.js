function failLoan() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('请确认', '确定要进行流标操作？', function (r) {
            if (r) {
                var urls = '<%=basePath%>/loan/failloan/' + row.id;
                alert(urls);
                $.ajax({
                    url: urls,
                    success: function (result) {
                        var result = eval('(' + result + ')');
                        if (result.errorMsg) {
                            $.messager.alert('Error', result.errorMsg);
                            /*$.messager.show({
                             title : 'Error',
                             msg : result.errorMsg
                             });*/
                        } else {
                            $('#dg').datagrid('reload');
                        }
                    }
                });
            }
        });
    }
}