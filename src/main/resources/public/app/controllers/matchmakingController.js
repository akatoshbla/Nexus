(function () {

    var matchmakingController = function ($scope, $http) {
     //close the modal instance
        $scope.close = function () {
            console.log('closed');
            $modalInstance.dismiss('cancel');
        };

    };
    matchmakingController.$inject = ['$scope'];

    angular.module('nexusApp')
        .controller('matchmakingController', matchmakingController);

}());