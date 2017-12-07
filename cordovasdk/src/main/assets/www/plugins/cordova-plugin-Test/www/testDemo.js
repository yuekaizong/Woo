cordova.define("cordova-plugin-Test.TestPlugin",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            lbs: function(content){
                exec(
                function(message){//成功回调function
                    console.log(message);
                },
                function(message){//失败回调function
                    console.log(message);
                },
                "TestPlugin",//feature name
                "lbs",//action
                [content]//要传递的参数，json格式
                );
            }
        }
});