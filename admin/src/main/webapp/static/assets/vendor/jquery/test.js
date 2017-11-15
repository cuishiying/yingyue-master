$.fn.changeStyle = function(colorStr){
    this.css("color",colorStr);
}
$.extend({
    sayHello: function(name) {
        console.log('Hello,' + (name ? name : 'Dude') + '!');
    }
})
// $.sayHello(); //调用
// $.sayHello('Wayou'); //带参调用