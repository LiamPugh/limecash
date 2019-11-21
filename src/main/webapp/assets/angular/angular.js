var app = angular.module('limecash', []);
app.service('yearSharedData', function () {

    this.year = 0;
    this.setYear = function (year){
        document.cookie = "year=" + year;
    };
    this.getYear = function(){
        var x = document.cookie.split(";");
        for(a = 0; a < x.length; a++){
            if(x[a].substring(0,6) === " year="){
                return x[a].substring(6);
            }
        }
    };
    this.initYear = function(){
        var pass = false;
        var x = document.cookie.split(";");
        for(a = 0; a < x.length; a++){
            if(x[a].substring(0,6) === " year="){
                pass = true;
            }
        }
        if(!pass){
            console.log("INIT");
            this.setYear("2019");
        }
    };
});

app.controller('indexTable', function($scope, $http, yearSharedData) {
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

    var months =  ["January","February","March","April","May","June","July",
        "August","September","October","November","December"];

    $scope.month = months[(new Date()).getMonth()];

    $http.get("/getMonthsBudgets/" + $scope.month + "/" + yearSharedData.getYear())
        .then(function (response){
            console.log("Complete");
            $scope.monthBudget = response.data;
            budgetTotaller($scope.monthBudget);
            console.log($scope.monthBudget);
        });

    function budgetTotaller(budget){
        $scope.totalBudget = 0;
        for(var i = 0; i < budget.remaining.length; i++){
            $scope.totalBudget += budget.remaining[i];
        }
    }


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

    $scope.setYear = function(){
        console.log("Setting year: " + $scope.year);
        yearSharedData.setYear($scope.year);
    };

    $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };

    yearSharedData.initYear();
    $scope.year = yearSharedData.getYear();
});
app.controller('monthController', function($scope, $http, yearSharedData) {

    function get(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }

    $scope.month = get('month');
    $scope.year = yearSharedData.getYear();

    $http.get("/getMonthsBudgets/" + $scope.month + "/" + $scope.year)
        .then(function (response) {
            $scope.monthBudget = response.data;
            console.log($scope.monthBudget);
        });

    $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };
});
app.controller('transMonthController', function($scope, $http, yearSharedData) {

    $scope.year = yearSharedData.getYear();

    $scope.deleteTransaction = function(id){
        $http.get("/deleteTransaction/" + id).then(function (response) {window.location.reload();});
    };

    function get(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }

    $scope.month = get('month');

    $http.get("/getAllTransactionsForMonth/" + $scope.month + "/" + $scope.year)
        .then(function (response) {
            $scope.transactions = response.data;
            for(var transaction in $scope.transactions){
                if(transaction.incoming){
                    transaction.incoming = "Incoming";
                }else{
                    transaction.incoming = "Outgoing";
                }
            }
            console.log(response.data);
        });
});
