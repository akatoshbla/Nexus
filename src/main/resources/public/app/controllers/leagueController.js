
(function() {

    var leagueController = function ($scope, $http) {

var champListUrl = "https://na.api.pvp.net/api/lol/na/v1.2/champion?api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76"

        
   $http.get(champListUrl).success( function(response) {
      $scope.champions =  response.champions;
   $scope.freeChamps = [];
   $scope.imageObject = [];  
       $scope.champData =[];
       $scope.combinedObject = [];
          angular.forEach($scope.champions, function(champ){
                  if(champ.freeToPlay === true){
              
                  $scope.freeChamps.push(champ); 
                 $http.get("https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion/" + champ.id+ "?champData=image&api_key=d22b06f5-db0b-4886-a43f-86ff2d96ee76").success( function(data) {
                 
                     $scope.champData.push(data);
                 })
                 
                  }
              
               })
          $scope.combinedObject.push($scope.champData);
       
          
  
            console.log( $scope.combinedObject);
     
   })
   
    };
    
    leagueController.$inject = ['$scope', '$http'];

    angular.module('nexusApp')
      .controller('leagueController', leagueController);
    
}());