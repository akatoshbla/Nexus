/**
 * Created by Phil on 2/28/2016.
 */
(function () {

    var leagueResultController = function ($scope, $http, api) {

        $scope.summonerInfo= api.getResult();
        console.log({info: $scope.summonerInfo});

    };

    leagueResultController.$inject = ['$scope','$http', 'api'];

    angular.module('nexusApp')
        .controller('leagueResultController', leagueResultController);

}());
