(function () {

    var matchmakingController = function ($scope, $http, $modalInstance) {
     //close the modal instance
      $scope.close = function () {
          console.log('closed');
        $modalInstance.dismiss('cancel');
        };

    };
    matchmakingController.$inject = ['$scope', '$http', '$modalInstance'];

    angular.module('nexusApp')
        .controller('matchmakingController', matchmakingController);

}());