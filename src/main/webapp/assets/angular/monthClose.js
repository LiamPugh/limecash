var app = angular.module('limecashCloser', []);
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
app.controller('monthController', function ($scope, $http, yearSharedData) {

    $scope.takeouts = [];

    $http.get("/getAllAccounts")
        .then(function (response) {
            $scope.accounts = response.data;
            $scope.totalAccounts = 0;
            $scope.accounts.forEach(accountsTotaller);
        });

    $scope.addAllAccounts = function(){
        $scope.totalAccounts = 0.0;
        $scope.accounts.forEach(accountsTotaller);
    };

    $scope.addAllTakeOuts = function(){
        var total = 0;
        for(var i = 0; i < $scope.takeouts.length; i++){
            total += parseFloat($scope.takeouts[i]);
        }
        total += parseFloat($scope.totalDiff);
        $scope.totalTakeOuts = -total;
    };


    function accountsTotaller(account){
        $scope.totalAccounts += parseFloat(account.holding);
    }

    function diffTotaller(difference){
        $scope.totalDiff += difference;
    }

    function remTotaller(budget){
        $scope.totalRemaining += budget;
        $scope.takeouts[$scope.takeouts.length] = 0;
    }

    $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };

     function getAccDifferences(){
        $http.post("/monthClose/accDifferences",$scope.accounts)
            .then(function (response) {
                $scope.differences = response.data;
                $scope.totalDiff = 0;
                $scope.differences.forEach(diffTotaller);
                $scope.totalTakeOuts = -$scope.totalDiff;
                if(($scope.totalDiff  < 0 ) && -$scope.totalDiff > $scope.totalRemaining){
                    alert("Spending has exceeded budget! Please increase budget areas before month close");
                    window.location.href ="/index.html";
                }else{
                    if($scope.totalDiff === 0){
                        //Skip
                    }
                }

            });
    }


    $scope.progressToRemainingRealloc = function(){

    };


    $scope.diffSplitter = function(){
      alert("Function not currently written :)");
    };

    $scope.accountsDoneToggle = function(){
        if(!$scope.accountsDone){
            getAccDifferences();
        }
        $scope.accountsDone = !$scope.accountsDone;
    };

    $scope.year = yearSharedData.getYear();


    $scope.monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    $scope.date = new Date();

    $http.get("/getMonthsBudgets/" + $scope.monthNames[$scope.date.getMonth()] + "/" + $scope.year)
        .then(function (response) {
            $scope.budgets = response.data;
            console.log(response.data);
            $scope.totalRemaining = 0;
            $scope.budgets.remaining.forEach(remTotaller);
        });




});