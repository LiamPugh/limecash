var app = angular.module('transForm', []);
app.controller('submitController', function($scope, $http) {

    $scope.transObj = {
        name : "",
        date : "",
        value : "",
        quantity : 1,
        incoming : false,
        complete : true,
        accountImpacted : "",
        bucketImpacted : "",
        areaImpacted : ""
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

    $http.get("/getMonthlyCost")
        .then(function (response) {
            $scope.monthlyCost = response.data;
        });

    $scope.submitForm = function submitForm(){
        if($scope.transObj.value < 0 && !$scope.transObj.incoming){
            $scope.transObj.value = 0 - $scope.transObj.value;
            $scope.transObj.incoming = true;
        } else if($scope.transObj.value < 0 && $scope.transObj.incoming){
            $scope.transObj.value = 0 - $scope.transObj.value;
        }
        $http.post("/addTransaction",$scope.transObj);
        window.location.href = '/index.html';
    }

});

