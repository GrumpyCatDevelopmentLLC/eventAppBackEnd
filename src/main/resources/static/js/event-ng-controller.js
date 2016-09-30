angular.module('EventApp', [])
    .controller('EventController', function($scope, $http) {

    $scope.newUser = {};

    $scope.user;

    $scope.createUser = function () {
        console.log("about to create user " + JSON.stringify(newUser));
        $http.post("/createUser", $scope.newUser)
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


});