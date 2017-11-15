;(function($, window, document,undefined) {
    var Fetch = function (ele) {
        this.$element = ele
    }
    Fetch.prototype = {
        getData:function (url,callback) {
            this.$element.click(function () {
                $.ajax({
                    url:url,
                    type:"get",
                    success:function(result) {
                        callback(result);
                    },
                    error:function(e) {
                        callback(e);
                    }
                });
            });
        },
        postJson:function (url, json, callback) {
            this.$element.click(function () {
                $.ajax({
                    url: url,
                    data: JSON.stringify(json),
                    type: "post",
                    contentType: "application/json;charset=utf-8",
                    success: function (result) {
                        callback(result);
                    },
                    error:function(e) {
                        callback(e);
                    }
                });
            });
        },
        postParams: function(url, params, callback) {
            this.$element.click(function () {
                $.ajax({
                    url: url,
                    data: params,//{a:b}键值对
                    type: "post",
                    success: function (result) {
                        callback(result);
                    },
                    error:function(e) {
                        callback(e);
                    }
                });
            })
        }
    }
    $.fn.getData = function (url, callback) {
        var fetch = new Fetch(this);
        fetch.getData(url, callback)
    }
    $.fn.postJson = function (url, json, callback) {
        var fetch = new Fetch(this);
        fetch.postJson(url, json, callback)
    }
    $.fn.postParams = function (url, params, callback) {
        var fetch = new Fetch(this);
        fetch.postParams(url, params, callback)
    }
})(jQuery, window, document);