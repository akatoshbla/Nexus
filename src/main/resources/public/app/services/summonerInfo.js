/**
 * Created by Phil on 2/28/2016.
 */
angularModule.service('api', ['$http', function($http) {

    var result;

    this.summonerInfo = function(summonerName){

        var summoner = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+ summonerName +"?api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76";

        $http.get(summoner).success(function (response) {

            console.log(response);
            result = response;
            console.log(result);
        })

        return result;
    }

}]);
//var summonApp = angular.module('summonApp',[]);
//summonApp.factory('summonerInfo', function(summonerName){
//    var summoner = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+ summonerName +"?api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76";
//    $http.get(summoner).success(function (response) {
//
//
//        $scope.summonerName;
//        console.log(response);
//        $scope.sumInfo = response;
//        console.log($scope.data);
//    })
//})
