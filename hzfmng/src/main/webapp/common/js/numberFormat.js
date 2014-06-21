/**
 * 格式化金额 保留到小数点后两位
 *
 * @param t
 * @returns
 */
fixedNumber = function (t) {
    if (-1 === t.toFixed(2).toString().indexOf(".")) {
        return (t.toFixed(2));
    } else {
        t = (t.toFixed(2).toString().replace(/(.+?)(0{1,})$/, "$1").replace(
            /(.+)\.$/, "$1"));
        return parseFloat(t).toFixed(2);
    }
};
