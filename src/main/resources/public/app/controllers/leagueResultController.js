/**
 * Created by Phil on 2/28/2016.
 * Controller for summonerPage to pass data to html
 */
(function () {

    var leagueResultController = function ($scope, $http, sumInfo) {

        var chosenChampion = [];

        $scope.summonerInfo = sumInfo.getResultForRanked();
        //console.log({info: $scope.summonerInfo});
        //console.log($scope.summonerInfo);

        $scope.summonerName = sumInfo.getSummonerName();

        $scope.champJson = sumInfo.getChamps();

        //$scope.chosenChamp = sumInfo.chosenOne();
        //console.log($scope.chosenChamp);

        $scope.chosenOne = function (selectChamps){
            angular.forEach(summonerInfo.champions,function(champ){
                if(champ.id === selectChamps){
                    chosenChampion.push(champ);
                    console.log(champ);
                }
            })
            return chosenChampion;
        }

        console.log(chosenChampion);
    };

    leagueResultController.$inject = ['$scope','$http', 'sumInfo'];

    angular.module('nexusApp')
        .controller('leagueResultController', leagueResultController);

}());
