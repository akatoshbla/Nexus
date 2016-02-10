(function () {

    var leagueController = function ($scope, $http, $location) {

            // url for champ including my access key
        var champListUrl = "https://na.api.pvp.net/api/lol/na/v1.2/champion?api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76"

        //function that is run at controller launch to get free champs
        $http.get(champListUrl).success(function (response) {
            $scope.champions = response.champions;
            $scope.freeChamps = [];
            $scope.imageObject = [];
            $scope.champData = [];
            $scope.combinedObject = [];
            //add each champ to champData
            angular.forEach($scope.champions, function (champ) {
                if (champ.freeToPlay === true) {

                    $scope.freeChamps.push(champ);
                    $http.get("https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion/" + champ.id + "?champData=image&api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76").success(function (data) {

                        $scope.champData.push(data);
                    })

                }

            })
            $scope.combinedObject.push($scope.champData);
       

        })



        //   API calls and json creation
        var mastery = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/gho5tone";

           $scope.search = function(){
            var summoner = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+ $scope.sumName+ "?api_key=6de076c3-3dc7-4efc-9566-a5dfae3003b3";

            $http.get(summoner).success(function(response){

            //$scope.profileIcon = response.profileIconId;

            console.log("response:", response);
            $scope.detail = response;
        });
        }
       

    };

    leagueController.$inject = ['$scope', '$http', '$location'];

    angular.module('nexusApp')
        .controller('leagueController', leagueController);

}());