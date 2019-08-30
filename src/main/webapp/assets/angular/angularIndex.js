var app = angular.module('limecashIndex', []);
app.controller('indexTable', function($scope, $http) {
    $http.get("/getAllBuckets")
        .then(function (response) {
            $scope.buckets = response.data;
            $scope.totalBucket = 0;
            $scope.buckets.forEach(buckTotaller);
        });

    $http.get("/getAllAccounts")
        .then(function (response) {
            $scope.accounts = response.data;
            $scope.totalAccounts = 0;
            $scope.accounts.forEach(accountsTotaller);
        });

    $http.get("/getStartingTotal")
        .then(function (response) {
            $scope.startingTotal = response.data.value;
        });

    $http.get("/getLowestPoint")
        .then(function (response) {
            $scope.lowestPoint = response.data;
        });

    $http.get("/getFinishingTotal")
        .then(function (response) {
            $scope.finishingTotal = response.data;
        });

    $http.get("/getIncomeTotal")
        .then(function (response) {
            $scope.totalIncome = response.data;
        });

    $http.get("/getCostsTotal")
        .then(function (response) {
            $scope.totalCost = response.data;
        });


    $http.get("/getMonthlyCost")
        .then(function (response) {
            $scope.monthlyCost = response.data;
        });

    function accountsTotaller(account){
        $scope.totalAccounts += account.holding;
    }

    function buckTotaller(buck){
        $scope.totalBucket += buck.value;
    }

});

