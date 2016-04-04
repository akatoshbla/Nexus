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

        //$scope.chosenChamp = sumInfo.chosenOne();
        //console.log($scope.chosenChamp);

        $scope.chosenOne = function (selectChamps){
            //console.log(selectChamps);
            $scope.chosenChampion = [];
            var sumJson = sumInfo.getResultForRanked();
            //console.log(sumJson);
            angular.forEach(sumJson.champions,function(data){
                //console.log(data.id);
                if(data.id == selectChamps){
                    $scope.chosenChampion.push(data);
                    //console.log(chosenChampion);
                }
            })
            console.log($scope.chosenChampion);
        }

        console.log(chosenChampion);
    };

    leagueResultController.$inject = ['$scope','$http', 'sumInfo'];

    angular.module('nexusApp')
        .controller('leagueResultController', leagueResultController);

}());
