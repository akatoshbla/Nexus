/**
 * Created by Phil on 2/28/2016.
 * Api for retrieving league of legends information
 */

(function () {
    //change api to a more functional name, no more global namespace
    var sumInfo = function ($http, $q, $parse) {
        var deferred = $q.defer();
        var result;
        var lolObject;
        this.summonerRank = function (summonerName) {
            var info = summonerName.toString();
            var summoner = 'https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/'+ summonerName +'?api_key=6de076c3-3dc7-4efc-9566-a5dfae3003b3';
            $http.get(summoner).success(function (response) {
                //using summoner ID to get other info
                //console.log(response);
                $http.get('https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/'+ response[info].id +'/ranked?season=SEASON2015&api_key=6de076c3-3dc7-4efc-9566-a5dfae3003b3').success(function(res){
                    deferred.resolve(res);
                });

            })

            result = deferred.promise;
            return deferred.promise;
        }

        this.getResultForRanked = function(){
            lolObject = result.$$state.value;
            return lolObject;
        }

        this.getChamps = function () {
            console.log([lolObject.champions]);
            //add each champ to champData
            angular.forEach(lolObject.champions, function (champ) {
                    // $scope.freeChamps.push(champ);
                    $http.get("https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion/" + champ.id + "?champData=image&api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76").success(function (data) {
                        console.log(data);
                        //$scope.champData.push(data);
                    })
            })
        }

    };
    sumInfo.$inject = ['$http', '$q', '$parse'];
    angular.module('nexusApp').service('sumInfo', sumInfo);
}());
//buklaou