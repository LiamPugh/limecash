var app = angular.module('limecashMonth', []);
app.controller('monthController', function($scope, $http) {

    function get(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }

    $scope.month = get('month');

    $http.get("/getMonthsBudgets/" + $scope.month)
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

app.controller('transMonthController', function($scope, $http) {

    function get(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }

    $scope.month = get('month');

    $http.get("/getAllTransactionsForMonth/" + $scope.month)
        .then(function (response) {
            $scope.transactions = response.data;
            console.log(response.data);
        });
});