/**
 * Created by Phil on 2/28/2016.
 */

(function () {
    var api = function ($http,$q) {
        var deferred = $q.defer();

    this.summonerInfo = function(summonerName){

        var summoner = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+ summonerName +"?api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76";

        $http.get(summoner).success(function (response) {
            deferred.resolve(response);
            console.log(response);
            console.log(deferred);
        })

        return deferred.promise;
    }

};
     api.$inject = ['$http','$q'];
      angular.module('nexusApp').service('api', api);  
}());
