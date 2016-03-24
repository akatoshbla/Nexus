(function () {

        var diablo3Controller = function ($scope, $http) {

            $scope.heroLookup = function (id, hero) {
                $http.get('https://us.api.battle.net/d3/profile/' + id + '/?locale=en_US&apikey=t5bqm7xpt3kzu4u3jxrasec9pje5nvrp').success(function (response) {
                        var heroId;
                    console.log(response);
                        for (i = 0; i < response.heroes.length; i++) {
                            if (response.heroes[i].name === hero) {
                                heroId = response.heroes[i].id;

                            }
                        }
                        $http.get('https://us.api.battle.net/d3/profile/' + id + '/hero/' + heroId + '?locale=en_US&apikey=t5bqm7xpt3kzu4u3jxrasec9pje5nvrp').success(function (response) {
                                $scope.heroInfo = response;
                                console.log($scope.heroInfo);

                            })


                        })


                }



            };









            diablo3Controller.$inject = ['$scope', '$http'];

            angular.module('nexusApp')
                .controller('diablo3Controller', diablo3Controller);

        }());