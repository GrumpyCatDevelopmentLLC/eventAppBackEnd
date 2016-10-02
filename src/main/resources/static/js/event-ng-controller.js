angular.module('EventApp', [])
    .controller('EventController', function($scope, $http) {

    $scope.user = {};
    $scope.myEvent = {};
    $scope.loginUser = {};
    $scope.myUser;
    $scope.createdEvent;
    $scope.listEvents = {};
    $scope.checkInMaster;
    $scope.checkInList = {};
    $scope.event;

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

    $scope.listEvents = function() {
        console.log("Getting list of events");
        $http.get("/getListOfEvents.json")
        .then(
            function successCallBack(response) {
                console.log(response.data);
                console.log("retrieving events...");
                $scope.createdEvent = response.data;
                $scope.listEvents = $scope.createdEvent.responseEventContainer;

            },
            function errorCallBack(response) {
                console.log("Unable to retrieve events");
            });
         console.log("Done with the callback");
    };

    $scope.attendingEvent = function(event) {
        console.log("Attempting to check in to event...");
        console.log(event);
//        $scope.getSpecificEvent(event.id);
        var userAndEvent = {
            user: $scope.user,
            event: event
        }
        console.log(userAndEvent);
        $http.post("/checkIn.json", userAndEvent)
        .then(
            function successCallBack(response) {
                console.log(userAndEvent);
                console.log(response.data);
                console.log("checking in");
                $scope.checkInMaster = response.data;
                $scope.checkInList = $scope.checkInMaster.myEvent;
            },
            function errorCallBack(response) {
                console.log("unable to check in");
            });
         console.log("Done with the callback");
    };

    $scope.getSpecificEvent = function(eventID) {
        console.log("About to get event with ID " + eventID);
        $http.get("/getSpecificEvent.json?eventID=" + eventID)
        .then(
            function successCallBack(response) {
                console.log(response.data);
                console.log("retrieving event...");
                $scope.event = response.data;
            },
            function errorCallBack(response) {
                console.log("Unable to retrieve event");
            });
         console.log("Done with the callback");
    };

});