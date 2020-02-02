var app = angular.module('limecashBudgets', []);
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
app.controller('budgetTable',
    function($scope, $http, yearSharedData) {
        $http.get("/getAllBudgetRows/"+yearSharedData.getYear())
            .then(function (response) {
                $scope.budgets = response.data;
            });

        $scope.saveBudgets = function(){
            $http.post("/setAllBudgetAreas/" + yearSharedData.getYear(), $scope.budgets).then(
                window.location.reload()
            );
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

        $scope.addBudget = function(){
            $http.get("/addBudget/"+yearSharedData.getYear())
                .then(window.location.reload());
        };

        $scope.range = function(min, max, step) {
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };

        $scope.deleteBudget = function(id){
            $http.get("/deleteBudget/"+id)
                .then(window.location.reload());
        }
    }
);

