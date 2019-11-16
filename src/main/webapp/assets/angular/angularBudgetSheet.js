var app = angular.module('limecashBudgets', []);
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
app.controller('budgetTable',
    function($scope, $http, yearSharedData) {
        $http.get("/getAllBudgetRows/"+yearSharedData.getYear())
            .then(function (response) {
                $scope.budgets = response.data;
            });

        $scope.saveBudgets = function(){
            $http.post("/setAllBudgetAreas", $scope.budgets)
        };

        $scope.updateBudgetValue = function(name,number,event){
            if(event.keyCode === 13) {
                for (var i = 0; i < $scope.budgets.length; i++) {
                    if ($scope.budgets[i].budgetName === name) {
                        $scope.budgets[i].months[number] = parseFloat(event.target.textContent);
                        event.target.textContent = event.target.textContent.toString();
                    }
                }
            }
        };
    }
);

