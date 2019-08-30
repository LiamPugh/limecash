var app = angular.module('transForm', []);
app.controller('submitController', function($scope, $http) {

    $scope.transObj = {
        name : "",
        date : "",
        value : "",
        quantity : 1,
        incoming : false,
        complete : true,
        accountImpacted : ""
    };

    $scope.name = "";
    $scope.date = "";
    $scope.value = "";
    $scope.quantity = "";
    $scope.incoming = "";
    $scope.complete = "";
    $scope.accountImpacted = "";

    $http.get("/getMonthlyCost")
        .then(function (response) {
            $scope.monthlyCost = response.data;
        });

    $scope.submitForm = function submitForm(){
        alert("Submitting");
        $http.post("/addTransaction",$scope.transObj);
        window.location.href = '/index.html';

    }

});

