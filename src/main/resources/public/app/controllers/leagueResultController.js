/**
 * Created by Phil on 2/28/2016.
 * Controller for summonerPage to pass data to html
 */
(function () {

    var leagueResultController = function ($scope, $http, api) {

        $scope.summonerInfo = api.getResultForRanked();
        //console.log({info: $scope.summonerInfo});
        console.log($scope.summonerInfo);

        $scope.champs = api.getChamps();
        console.log($scope.champs);

    };

    leagueResultController.$inject = ['$scope','$http', 'api'];

    angular.module('nexusApp')
        .controller('leagueResultController', leagueResultController);

}());
