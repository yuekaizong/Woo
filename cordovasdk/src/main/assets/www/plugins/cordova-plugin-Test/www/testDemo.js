cordova.define("cordova-plugin-Test.TestPlugin",
    function(require, exports, module) {
        var exec = require("cordova/exec");
        module.exports = {
            lbs: function(successCallback, errorCallback, content){
                exec(successCallback, errorCallback, "TestPlugin", "lbs", [content]);
            },
            contacts: function(successCallback, errorCallback, content){
                exec(successCallback, errorCallback, "TestPlugin", "contacts", [content]);
            },
            liveDetect: function(successCallback, errorCallback, content){
                exec(successCallback, errorCallback, "TestPlugin", "liveDetect", [content]);
            },
            edApplInfoAndRiskInfo: function(successCallback, errorCallback, content){
                exec(successCallback, errorCallback, "TestPlugin", "edApplInfoAndRiskInfo", [content]);
            },
        }
//var myFunc = function(){};
//myFunc.prototype.lbs = function(successCallback, errorCallback, content){
//        var win = function(data){
//           testLog(data);
//           data = "开始LBS 成功回调1";
//           testLog(data);
//           successCallback();
//           data = "结束LBS 成功回调";
//           testLog(data);
//        };
//        var fail = function(data){
//           testLog(data);
//           data = "失败";
//           successCallback(data);
//        };
//        exec(win, fail,  "TestPlugin", "lbs", []);
//};
//
//myFunc.prototype.contacts = function(successCallback, errorCallback, content){
//        exec(successCallback, errorCallback,  "TestPlugin", "contacts", []);
//};
//
//myFunc.prototype.liveDetect = function(successCallback, errorCallback, content){
//        exec(successCallback, errorCallback,  "TestPlugin", "liveDetect", [content]);
//};
//
//myFunc.prototype.edApplInfoAndRiskInfo = function(successCallback, errorCallback, content){
//        exec(successCallback, errorCallback,  "TestPlugin", "edApplInfoAndRiskInfo", [content]);
//};
//
//function testLog(data){
//       var str;
//       if (typeof data == "object"){
//           str = JSON.stringify(data);
//           console.log("testLog object "+str);
//       }else{
//           str = data;
//           console.log("testLog other "+str);
//       }
//}
//var obj = new myFunc();
//module.exports = obj;
});