/**
 * 银行卡号的校验 create by wangshaofen
 *
 * @param s
 * @returns {Boolean}
 */
function verbankcard(s) {
    if (/^\d{10,19}$/.test(s)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 金额的校验
 *
 * @param moneyNum
 * @returns {Boolean}
 */
function isvermoneyNum(s) {
    var p = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;

    if (p.test(s)) {
        return true;
    } else {
        return false;
    }

}
// 密码校验：只能输入6-20个字母、数字、下划线
function isPasswd(s) {
    var patrn = /^[\w]{6,20}$/;
    if (!patrn.test(s)) {
        return false;
    } else {
        return true;
    }

}

$.extend($.fn.validatebox.defaults.rules, {
    verbankcard: {
        validator: function (value, param) {
            return verbankcard(value);
        },
        message: '请输入正确的银行卡号'
    },
    vermoneyNum: {
        validator: function (value, param) {
            return isvermoneyNum(value);
        },
        message: '请输入正确的金额'
    },
    checkIdcard: {
        validator: function (value, param) {
            return checkIdcard(value);
        },
        message: '请输入正确的身份证号码'
    },
    isPasswd: {
        validator: function (value, param) {
            return isPasswd(value);
        },
        message: '只能输入6-20个字母、数字、下划线'
    },
    hequals: {
        validator: function (value, param) {
            return (value === $(param[0]).val());
        },
        message: '两次输入的密码不一致'
    }

});

/**
 * checkIDcard: 判断身份证是否正确 num: 接收参数。 return: true:正确，false:错误。
 */
checkIdcard = function (num) {
    num = num.toUpperCase();
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}[\d|X]$)/.test(num))) {

        console.log('进入。。。。。。。。。。。。。。。。。。');
        // alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
        return false;
    }
    // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
    // 下面分别分析出生日期和校验位
    var len, re;
    len = num.length;
    if (len === 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);

        // 检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/'
            + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() === Number(arrSplit[2]))
            && ((dtmBirth.getMonth() + 1) === Number(arrSplit[3]))
            && (dtmBirth.getDate() === Number(arrSplit[4]));
        if (!bGoodDay) {
            // alert('输入的身份证号里出生日期不对！');
            return false;
        } else {
            // 将15位身份证转成18位
            // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
                '3', '2');
            var nTemp = 0, i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return true;
        }
    }
    if (len === 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var _arrSplit = num.match(re);

        // 检查生日日期是否正确
        var _dtmBirth = new Date(_arrSplit[2] + "/" + _arrSplit[3] + "/"
            + _arrSplit[4]);
        var _bGoodDay;
        _bGoodDay = (_dtmBirth.getFullYear() === Number(_arrSplit[2]))
            && ((_dtmBirth.getMonth() + 1) === Number(_arrSplit[3]))
            && (_dtmBirth.getDate() === Number(_arrSplit[4]));
        if (!_bGoodDay) {
            // alert(dtmBirth.getYear());
            // alert(arrSplit[2]);
            // alert('输入的身份证号里出生日期不对！');
            return false;
        } else {
            // 检验18位身份证的校验码是否正确。
            // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var valnum;
            var _arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                8, 4, 2);
            var _arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
                '3', '2');
            var _nTemp = 0, j;
            for (j = 0; j < 17; j++) {
                _nTemp += num.substr(j, 1) * _arrInt[j];
            }
            valnum = _arrCh[_nTemp % 11];
            if (valnum !== num.substr(17, 1)) {
                // alert('18位身份证的校验码不正确！应该为：' + valnum);
                return false;
            }
            return true;
        }
    }
    return false;
};

// $.extend($.fn.validatebox.defaults.rules, {
// vermoneyNum: {
// validator: function(value, param){
// return isvermoneyNum(value);
// },
// message: '请输入正确的金额'
// }
// });
