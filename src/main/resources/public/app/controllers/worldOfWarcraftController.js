(function () {
    var key = 't5bqm7xpt3kzu4u3jxrasec9pje5nvrp';
    var secret = '86dFFh3zDwGkejhkeDmUhjB3HKj2aeeQ';
  
    var worldOfWarcraftController = function ($scope, $http, wowTabService) {
          $scope.tabs = {
          arena2v2: true,
          arena3v3: false,
          arena5v5: false,
          rbg: false    
    }
          console.log($scope.tabs);

         $scope.characterLookup = function (realm, characterName) {
           var  host = "https://us.api.battle.net/wow/character/";
            var request = host + realm.replace(" " , "-") + '/' + characterName + "?fields=guild,items,pvp,appearance,progression&locale=en_US&&apikey=t5bqm7xpt3kzu4u3jxrasec9pje5nvrp";
             $http.get(request).success( function(response){
                 console.log(response);
                 $scope.characterInfo = response;
             })  
        } ;
      $scope.tabChange = function(requestTab){
          $scope.tabs = wowTabService.tabsPvp(requestTab);
             
      }
    }

    worldOfWarcraftController.$inject = ['$scope' , '$http', 'wowTabService'];

    angular.module('nexusApp')
        .controller('worldOfWarcraftController', worldOfWarcraftController);

}());