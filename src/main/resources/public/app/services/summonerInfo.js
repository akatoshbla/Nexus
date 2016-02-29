/**
 * Created by Phil on 2/28/2016.
 */

(function () {
    var api = function ($http, $q) {
        var deferred = $q.defer();
        var result;
        this.summonerInfo = function (summonerName) {

            var summoner = 'https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/' + summonerName + '?api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76';

            $http.get(summoner).success(function (response) {
                //using summoner ID to get other info

               // $http.get('https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/'+ response.window[summonerName] +'/ranked?season=SEASON2016&api_key=6de076c3-3dc7-4efc-9566-a5dfae3003b3');
                deferred.resolve(response);
                console.log(response);
                console.log(deferred);
            })

            result = deferred.promise;
            return deferred.promise;
        }

        this.getResult = function(){
            return result.$$state.value;
        }

    };
    api.$inject = ['$http', '$q'];
    angular.module('nexusApp').service('api', api);
}());
