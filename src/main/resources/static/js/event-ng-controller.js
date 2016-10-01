angular.module('EventApp', [])
    .controller('EventController', function($scope, $http) {

    $scope.user = {};
    $scope.myEvent = {};
    $scope.loginUser = {};
    $scope.myUser;
    $scope.createdEvent;

    $scope.createUser = function () {
        console.log("about to create user");
        $http.post("/createUser.json", $scope.user)
        .then(
            function successCallBack(response) {
                console.log(response.data);
                console.log("Adding data to scope...");
                $scope.myUser = response.data;
                $scope.user = {};
            },
            function errorCallBack(response) {
                console.log("Unable to create user");
            });
        console.log("done with the callback");
    };

    $scope.login = function() {
        console.log("about to login");
        console.log(JSON.stringify($scope.loginUser));
        $http.post("/login.json", $scope.loginUser)
        .then(
            function successCallBack (response) {
                console.log(response.data);
                console.log("logging in...");
                $scope.myUser = response.data;
                $scope.loginUser = {};
            },
            function errorCallBack (response){
                console.log("unable to login")
            });
         console.log("done with the callback")
    };

    $scope.newEvent = function() {
        console.log("Going to try to create an event");
        console.log("Creating event" + JSON.stringify($scope.myEvent));
        $http.post("createEvent.json", $scope.myEvent)
        .then (
            function successCallBack(response) {
                console.log(response.data);
                console.log("creating new event, please wait...");
                $scope.createdEvent = response.data;
                $scope.myEvent = {};
            },
            function errorCallBack(response) {
                console.log("unable to create event");
            });
         console.log("Done with the callback");
    };

//    $scope.logout = function() {
//        console.log("logging out...");
//        $http.post("/logout")
//        .then(
//            function successCallBack(response) {
//                console.log(response.data);
//                console.log("logging out");
////                $scope.user = {};
//            },
//            function errorCallBack(response) {
//                console.log("Could not log out");
//            });
//         console.log("done with the callback");
//    };


});