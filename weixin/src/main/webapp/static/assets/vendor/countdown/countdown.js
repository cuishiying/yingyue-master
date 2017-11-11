var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount = 0;//当前剩余秒数
var btn;
function countDown($obj) {
    if(curCount!=0){
        return "-1";
    }
    btn = $obj;
    curCount = count;
    btn.attr("disabled", "disabled");
    InterValObj = setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    return curCount;
}

//timer处理函数
function SetRemainTime() {

    if (curCount == 0) {
        window.clearInterval(InterValObj);//停止计时器
        btn.removeAttr("disabled");//启用按钮
        if(btn[0].tagName=="INPUT"){
            btn.val("获取验证码");
        }else{
            btn.html("获取验证码");
        }
    }else {
        curCount--;
        if(btn[0].tagName=="INPUT"){
            btn.val( + curCount + "秒再获取");
        }else{
            btn.html( + curCount + "秒再获取");
        }
    }
}