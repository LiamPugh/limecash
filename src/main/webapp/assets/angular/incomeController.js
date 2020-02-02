var app = angular.module('limecashIncome', []);
app.service('yearSharedData', function () {

    this.year = 0;
    this.setYear = function (year){
        document.cookie = "year=" + year;
    };
    this.getYear = function(){
        var x = document.cookie.split(";");
        for(a = 0; a < x.length; a++){
            if(x[a].substring(0,5) === "year="){
                return x[a].substring(5);
            }
        }
    };
    this.initYear = function(){
        var pass = false;
        var x = document.cookie.split(";");
        for(a = 0; a < x.length; a++){
            if(x[a].substring(0,5) === "year="){
                pass = true;
            }
        }
        if(!pass){
            console.log("INIT");
            this.setYear("2019");
        }
    };
});
app.controller('incomeController', function ($scope, $http, yearSharedData) {

    $scope.markCompleted = function (incomeName, monthNumber) {
        $http.get("/submitIncomeCompleted/" + incomeName + "/" + monthNumber + "/2020").then($scope.getIncomes);
    };

    $scope.markNotCompleted = function (incomeName, monthNumber) {
        $http.get("/submitIncomeNotCompleted/" + incomeName + "/" + monthNumber + "/2020").then($scope.getIncomes);
    };

    $scope.deleteBudget = function(id){
        $http.get("/deleteBudget/"+id)
            .then(window.location.reload());
    };


    $scope.getIncomes = function () {
        $http.get("/getAllIncomes/" + yearSharedData.getYear())
            .then(function (response) {
                $scope.budgets = response.data;
            });
    };

    $scope.addIncome = function(){
        $http.get("/addIncome/" + yearSharedData.getYear())
            .then(function (response) {
                window.location.reload();
            });
    };

    $scope.saveBudgets = function(){
        $http.post("/setAllBudgetAreas/" + yearSharedData.getYear(), $scope.budgets).then(
            window.location.reload()
        );
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
});