/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function () {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function () {
        this.receivedEvent('deviceready');
        document.addEventListener('batterystatus', onBatteryStatus, false);
        document.addEventListener("volumeupbutton", callbackFuntion, false);
//        document.addEventListener("backbutton", onBackKeyDown, false);

        document.getElementById('cameraTakePicture').addEventListener('click', cameraTakePicture);
        document.getElementById('cameraGetPicture').addEventListener('click', cameraGetPicture);
        document.getElementById("createContact").addEventListener("click", createContact);
        document.getElementById("findContact").addEventListener("click", findContacts);
        document.getElementById("deleteContact").addEventListener("click", deleteContact);
        document.getElementById("cordovaDevice").addEventListener("click", cordovaDevice);
        document.getElementById("getAcceleration").addEventListener("click", getAcceleration);
        document.getElementById("watchAcceleration").addEventListener("click", watchAcceleration);
        document.getElementById("getOrientation").addEventListener("click", getOrientation);
        document.getElementById("watchOrientation").addEventListener("click", watchOrientation);
    },

    // Update DOM on a Received Event
    receivedEvent: function (id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();

document.getElementById("setLocalStorage").addEventListener("click", setLocalStorage);
document.getElementById("showLocalStorage").addEventListener("click", showLocalStorage);
document.getElementById("removeProjectFromLocalStorage").addEventListener("click", removeProjectFromLocalStorage);
document.getElementById("getLocalStorageByKey").addEventListener("click", getLocalStorageByKey);

document.getElementById("getOrientation").addEventListener("click", getOrientation);
document.getElementById("createContact").addEventListener("click", createContact);
var localStorage = window.localStorage;
var count = 0;

document.getElementById('demo1').addEventListener('click', demo1);

function demo1(){
//  testPlugin.testPluginFunction("HelloWorld");
         window.cordova.exec(null, null, 'TestPlugin', 'testPluginFunction', null);
}

function setLocalStorage() {
    if (!localStorage.getItem("Name")) {
        localStorage.setItem("Name", "Kaizone");
    }
    if (!localStorage.getItem("Job")) {
        localStorage.setItem("Job", "Developer");
    }
    if (!localStorage.getItem("Project")) {
        localStorage.setItem("Project", "Cordova Project");
    }
    count++;
    localStorage.setItem("Count", count);
    alert(document.getElementById("input1").value);
}

function showLocalStorage() {
    console.log(localStorage.getItem("Name"));
    console.log(localStorage.getItem("Job"));
    console.log(localStorage.getItem("Project"));
    alert(localStorage.getItem("Name") + "," + localStorage.getItem("Job") + "," + localStorage.getItem("Project") + "," + localStorage.getItem("Count"))

}

function removeProjectFromLocalStorage() {
    localStorage.removeItem("Project");

}

function getLocalStorageByKey() {
    console.log(localStorage.key(0));
    alert(localStorage.key(0) + "," + localStorage.getItem("Count"));

}

function callbackFuntion() {
    alert("Volume Up Button is Pressed!")
}

function onBackKeyDown(e) {
    e.preventDefault();
    alert("Back Button is Pressed!");
}

function onBatteryStatus(info) {
    alert("Battery Status: Level: " + info.level + " isPlugged: " + info.isPlugged)
}

function cameraTakePicture() {
    navigator.camera.getPicture(onSuccess, onFail, {
        quality: 50,
        destinationType: Camera.DestinationType.DATA_URL
    });

    function onSuccess(imageData) {
        var image = document.getElementById('myImage');
        image.src = "data:image/jpeg;base64," + imageData
    }

    function onFail(message) {
        alert('Failed because: ' + message)
    }
}

function cameraGetPicture() {
    navigator.camera.getPicture(onSuccess, onFail, {
        quality: 50,
        destinationType: Camera.DestinationType.FILE_URI,
        sourceType: Camera.PictureSourceType.PHOTOLIBRARY
    });

    function onSuccess(imageURL) {
        var image = document.getElementById('myImage');
        image.src = imageURL;
    }

    function onFail(message) {
        alert('Failed because: ' + message);
    }
}

function createContact() {
    var name = document.getElementById("input1").value;
    var contact = navigator.contacts.create({"displayName": name});
    contact.save(contactSucess, contactError);

    function contactSucess() {
        alert("Contact is saved!");
    }

    function contactError(message) {
        alert('Failed because :' + message);
    }
}

function findContacts() {
    var options = new ContactFindOptions();
    options.filter = "";
    options.multiple = true;

    var name = document.getElementById("input1").value;
    fields = [name];
    navigator.contacts.find(fields, contactFindSuccess, contactFindError, options);

    function contactFindSuccess(contacts) {
        for (var i = 0; i < contacts.length; i++) {
            alert("Display Name =" + contacts[i].displayName);
        }
    }

    function contactFindError(message) {
        alert("Failed because: " + message);
    }
}

function deleteContact() {
    var options = new ContactFindOptions();
    options.filter = document.getElementById("input1").value;
    options.multiple = false;
    fields = ["displayName"];
    navigator.contacts.find(fields, contactFindSuccess, contactFindError, options);

    function contactFindSuccess(contacts) {
        var contact = contacts[0];
        contact.remove(contactRemoveSuccess, contactRemoveError)

        function contactRemoveSuccess(contact) {
            alert("Contact Deleted");
        }

        function contactRemoveError(message) {
            alert("Failed because: " + message)
        }
    }

    function contactFindError(message) {
        alert("Failed because :" + message);
    }
}

function cordovaDevice() {
    alert("Cordova version" + device.cordova + "\n" +
        "Device model: " + device.model + "\n" +
        "Device platform: " + device.uuid + "\n" +
        "Device version: " + device.version);
}

function getAcceleration() {
    navigator.accelerometer.getCurrentAcceleration(accelerometerSuccess, accelerometerError);

    function accelerometerSuccess(acceleration) {
        alert('Acceleration X:' + acceleration.x + "\n" +
            "Acceleration Y: " + acceleration.y + '\n' +
            'Accceleration Z: ' + acceleration.z + '\n' +
            'Timestamp: ' + acceleration.timestamp + '\n');
    };

    function accelerometerError() {
        alert('onError');
    }
}

function watchAcceleration() {
    var accelerometerOptions = {
        frequency: 3000
    };

    var watchID = navigator.accelerometer.watchAcceleration(
        accelerometerSuccess,
        accelerometerError,
        accelerometerOptions
    );

    function accelerometerSuccess(acceleration) {
        alert('Acceleration X:' + acceleration.x + "\n" +
            "Acceleration Y: " + acceleration.y + '\n' +
            'Acceleration Z: ' + acceleration.z + '\n' +
            'Timestamp: ' + acceleration.timestamp + '\n');
        setTimeout(function () {
            navigator.accelerometer.clearWatch(watchID);
        }, 10*1000);
    };

    function accelerometerError() {
        alert('onError!');
    }
}

function getOrientation() {
    navigator.companies.getCurrentHeading(compassSuccess, compassError);
    function compassSuccess(heading) {
        alert('Heading: '+heading.magneticHeading);
    }
    function compassError(error) {
        alert('CompassError: '+error.code);
    }
    alert("hello2");
}

function watchOrientation() {
    var compassOptions ={
        frequency:3000
    }

    var watchID = navigator.compass.watchHeading(compassSuccess, compassError, compassOptions)

    function compassSuccess(heading) {
        alert('Heading: '+heading.magneticHeading);
        setTimeout(function () {
            navigator.compass.clearWatch(watchID);
        }, 10*1000);
    }

    function compassError(error) {
        alert('CompassError: '+error.code);
    };
}