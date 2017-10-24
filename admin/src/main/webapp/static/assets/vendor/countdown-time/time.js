$(function(){
    ;(function($, window, document,undefined) {
        //定义main_body的构造函数
        var main_body = function(ele, opt) {
            this.$element = ele,
            this.defaults = {
                nameL: $(".code"),/*输入框class*/
    	        time_overplus:60,/*倒计时时间多少秒*/
    	        innerfont: '获取验证码',/*右边按钮初始内容*/
    	        ifPass:1/*验证结果，控制右边按钮是否可以点击*/  
            },
            this.options = $.extend({}, this.defaults, opt)
        }
        //定义main_body的方法
        main_body.prototype = {
            beautify: function() {
                var NameL= this.options.nameL,
    	        Time_overplus=this.options.time_overplus,
    	        Innerfont= this.options.innerfont,
                ele = this.$element,
    	        IfPass=this.options.ifPass, 
                SendM=this.options.sendM;  
    			if(IfPass){
    				if(ele.html()==Innerfont){	
                        ele.html('正在发送...');
    					timer00 = setInterval(function(){
    						if(Time_overplus==0){
    							ele.html(Innerfont);
    							clearInterval(timer00); 
                                SendM();
    						}else{
    							ele.html("重新发送(" + Time_overplus + ")");
    					        Time_overplus--;
    						}
    					}, 1000);
    					NameL.focus();
    				}
    			}
            }
        }
        //在插件中使用main_body对象
        $.fn.SMS_Verification = function(options){
            //创建main_body的实体
            var beautifier = new main_body(this, options);
            //调用其方法
            return beautifier.beautify();       
        }
    })(jQuery, window, document);
})