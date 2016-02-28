(function () {
    var key = 't5bqm7xpt3kzu4u3jxrasec9pje5nvrp';
    var secret = '86dFFh3zDwGkejhkeDmUhjB3HKj2aeeQ';

    var worldOfWarcraftController = function ($scope, $http) {
         $scope.characterLookup = function (realm, characterName) {
           var  host = "https://us.api.battle.net/wow/character/";
            var request = host + realm.replace(" " , "-") + '/' + characterName + "?fields=guild,items,pvp,appearance,progression&locale=en_US&&apikey=t5bqm7xpt3kzu4u3jxrasec9pje5nvrp";
             $http.get(request).success( function(response){
                 console.log(response);
                 $scope.characterInfo = response;
             })
            

        }
    };

    worldOfWarcraftController.$inject = ['$scope' , '$http'];

    angular.module('nexusApp')
        .controller('worldOfWarcraftController', worldOfWarcraftController);

}());