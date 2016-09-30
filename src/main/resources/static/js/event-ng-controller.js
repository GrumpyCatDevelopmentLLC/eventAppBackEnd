angular.module('EventApp', [])
    .controller('EventController', function($scope, $http) {

    $scope.newUser = {};

    $scope.user;

    $scope.createUser = function (email, displayName, password) {
        console.log("about to create user");
        $http.post("/createUser?email=" + email + "&displayName=" + displayName + "&password=" + password)
        .then(
            function successCallBack(response) {
                console.log(response.data);
                console.log("Adding data to scope...");
                $scope.user = response.data;

            },
            function errorCallBack(response) {
                console.log("Unable to create user");
            });
        console.log("done with the callback");
    };

    $scope.login = function() {
        console.log("about to login")
        $http.
    }


});