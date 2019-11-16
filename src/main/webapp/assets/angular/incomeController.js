var app = angular.module('limecashIncome', []);
app.controller('incomeController', function ($scope, $http) {

    $scope.markCompleted = function (incomeName, monthNumber) {
        $http.get("/submitIncomeCompleted/" + incomeName + "/" + monthNumber + "/2019").then($scope.getIncomes);
    };

    $scope.markNotCompleted = function (incomeName, monthNumber) {
        $http.get("/submitIncomeNotCompleted/" + incomeName + "/" + monthNumber + "/2019").then($scope.getIncomes);
    };

    $scope.getIncomes = function () {
        $http.get("/getAllIncomes/2019")
            .then(function (response) {
                $scope.budgets = response.data;
            });
    };

    $scope.getIncomes();
    $scope.range = function (min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };


    $scope.setYear = function () {
        console.log("Setting year: " + $scope.year);
        yearSharedData.setYear($scope.year);
    };

    // yearSharedData.initYear();
    // $scope.year = yearSharedData.getYear();
});