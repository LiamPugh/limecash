var app = angular.module('limecashBudgets', []);
app.controller('budgetTable',
    function($scope, $http) {
        $http.get("/getAllBudgetRows")
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

