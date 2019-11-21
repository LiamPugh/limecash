var app = angular.module('accountMaintenanceScreen', []);
app.controller('accountMaintenanceScreen', function($scope, $http) {

    $http.get("/getAllAccounts")
        .then(function (response) {
            $scope.accounts = response.data;
        });

    $scope.saveAccounts = function(){
        alert("In save");
        $http.post("/saveAllAccounts",$scope.accounts).then(
            alert("Saved accounts successfully!")
        );
    };

    $scope.deleteAccount = function(id){
        $http.get("/deleteAccount/" + id).then(
            window.location.reload()
        );
    };

    $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };

    function changeValue(id, property, event){
        switch(property){
            case "name":
                $scope.accounts[i].name = event;
                break;
        }
    }

    $scope.saveAccounts = function(){
        $http.post("/saveAllAccounts",$scope.accounts);
    };

    $scope.addAccount = function(){
        $http.get("/addNewAccount").then(window.location.reload());
    };

});

