(function () {

    var matchmakingController = function ($scope, $http, $modalInstance, matchMakingService) {
        $http.get('http://comp490.duckdns.org/matchFinder').success(function (response) {
         $scope.userMatches = response;   
            
        });
        
        
        $scope.tab = {
            matchmaking: true,
            match: false
        }
        $scope.chooseTab = function(requestTab){
            $scope.tab = matchMakingService.tabs(requestTab);
        };
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
            console.log(jsonObject)
            $http.post('http://comp490.duckdns.org/matchFinder', jsonObject).success(function (response) {
             
                console.log(response);
                $scope.close();
            })

        };

    };


    matchmakingController.$inject = ['$scope', '$http', '$modalInstance', 'matchMakingService'];

    angular.module('nexusApp')
        .controller('matchmakingController', matchmakingController);

}());