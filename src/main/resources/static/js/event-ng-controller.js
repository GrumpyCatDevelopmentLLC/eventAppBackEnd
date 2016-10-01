angular.module('EventApp', [])
    .controller('EventController', function($scope, $http) {

    $scope.user = {};

    $scope.loginUser = {};

    $scope.myUser;

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