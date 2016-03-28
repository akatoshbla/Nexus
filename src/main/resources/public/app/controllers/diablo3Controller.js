(function () {

    var diablo3Controller = function ($scope, $http) {

        $scope.battlenetLookup = function (id, hero) {
            $http.get('https://us.api.battle.net/d3/profile/' + id + '/?locale=en_US&apikey=t5bqm7xpt3kzu4u3jxrasec9pje5nvrp').success(function (response) {
                if (response.code == "NOTFOUND") {
                    $scope.error = true;
                    $scope.errorText = "Battlenet Id not found, make sure format is name-####";
                } else {
                    $scope.error = false;
                }
                $scope.battlenet = response;
                console.log($scope.battlenet);

            })
        };

        $scope.heroLookup = function (id, heroId) {
            $http.get('https://us.api.battle.net/d3/profile/' + id + '/hero/' + heroId + '?locale=en_US&apikey=t5bqm7xpt3kzu4u3jxrasec9pje5nvrp').success(function (response) {
                $scope.heroInfo = response;
                if (response.code == "NOTFOUND") {
                    $scope.error = true;
                    $scope.errorText = "hero not found";
                } else {
                    $scope.error = false;
                   
                }
                console.log($scope.heroInfo);
            })
        };



    }

    diablo3Controller.$inject = ['$scope', '$http'];

    angular.module('nexusApp')
        .controller('diablo3Controller', diablo3Controller);

}());