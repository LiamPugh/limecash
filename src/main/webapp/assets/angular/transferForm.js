var app = angular.module('transferForm', []);
app.controller('submitController', function($scope, $http) {

    $scope.transObj = {
        transferType:"",
        from : "",
        to : "",
        value : ""
    };

    $http.get("/getAllAccounts")
        .then(function (response) {
            $scope.accounts = response.data;
        });

    $http.get("/getAllBuckets")
        .then(function (response) {
            $scope.buckets = response.data;
        });

    $http.get("/getAllAreas")
        .then(function (response) {
            $scope.areas = response.data;
        });

    $scope.submitForm = function submitForm(){
        $http.post("/addTransfer",$scope.transObj);
        window.location.href = '/index.html';
    }

});

