/**
 * Created by Phil on 2/1/2016.
 */
(function(){
    var sumSearchController = function ($scope, $http){
        console.log($scope);
        console.log($http);
        var mastery = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/gho5tone";
        console.log($scope.sumName);
           $scope.search = function(){
            console.log(sumName);
            var summoner = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+ $scope.sumName+ "?api_key=6de076c3-3dc7-4efc-9566-a5dfae3003b3";

            $http.get(summoner).success(function(response){
            //$scope.sumId = response.id;
            //$scope.sumName = response.name;
            //$scope.profileIcon = response.profileIconId;
            //$scope.sumLvl = response.summonerLevel;
            //$scope.revisDate = response.revisionDate;

            console.log("response:", response);
            $scope.info = response[$scope.sumName];
        });
        }
    };



    sumSearchController.$inject = ['$scope', '$http'];
    // declare controller as part of the app
    angular.module('nexusApp')
        .controller('sumSearchController', sumSearchController);


}());