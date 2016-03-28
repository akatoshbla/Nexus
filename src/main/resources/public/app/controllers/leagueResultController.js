/**
 * Created by Phil on 2/28/2016.
 * Controller for summonerPage to pass data to html
 */
(function () {

    var leagueResultController = function ($scope, $http, sumInfo) {

        $scope.summonerInfo = sumInfo.getResultForRanked();
        //console.log({info: $scope.summonerInfo});
        //console.log($scope.summonerInfo);

        $scope.summonerName = sumInfo.getSummonerName();

        $scope.champJson = sumInfo.getChamps();

        $scope.chosenChamp = sumInfo.chosenChamp(summonerInfo.selectChamps.id);
        console.log($scope.chosenChamp);

    };

    leagueResultController.$inject = ['$scope','$http', 'sumInfo'];

    angular.module('nexusApp')
        .controller('leagueResultController', leagueResultController);

}());
