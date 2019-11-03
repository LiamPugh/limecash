var app = angular.module('limecashMonth', []);
app.controller('monthController', function($scope, $http) {

    function get(name){
        if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }

    $http.get("/getAllBuckets")
        .then(function (response) {
            $scope.month = get('month');
        });

    $scope.name = "";
    $scope.date = "";
    $scope.value = "";
    $scope.quantity = "";
    $scope.incoming = "";
    $scope.complete = "";
    $scope.accountImpacted = "";
});
