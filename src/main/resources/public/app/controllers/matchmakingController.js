(function () {

    var matchmakingController = function ($scope, $http, $modalInstance) {
        //close the modal instance
        $scope.close = function () {
            console.log('closed');
            $modalInstance.dismiss('cancel');
        };

        $scope.sendMatchDate = function () {
            var jsonObject = {
                date: $scope.date,
                startTime: $scope.startTime,
                finishTime: $scope.finishTime
            }
            $http.post('http://comp490.duckdns.org/matchFinder', jsonObject).success(function (response) {
             
                console.log(response);
                $scope.close();
            })

        }

    };


    matchmakingController.$inject = ['$scope', '$http', '$modalInstance'];

    angular.module('nexusApp')
        .controller('matchmakingController', matchmakingController);

}());