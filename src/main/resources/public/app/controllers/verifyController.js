(function () {

    var verifyController = function ($scope, $http) {
        console.log($scope.username);
        $http.post('http://comp490.duckdns.org/login', {
            name: $scope.username,
            password: $scope.password
        }).success(function (response) {
            $scope.response = response;
            console.log($scope.response);

        })


    };

    verifyController.$inject = ['$scope'];

    angular.module('nexusApp')
        .controller('verifyController', verifyController);

}());