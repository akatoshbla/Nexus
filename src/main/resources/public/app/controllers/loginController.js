(function () {

        var loginController = function ($scope, $http, $modal, $location, $rootScope ) {

            $scope.animationsEnabled = true;
              $scope.cancel = function () {
            $modalInstance.dismiss();
        };
            $scope.open = function (size) {

                var modalInstance = $modal.open({
                    animation: $scope.animationsEnabled,
                    templateUrl: 'app/views/createUser.html',
        
                    controller: 'createUserController',
                    size: size,
                    resolve: {
                        items: function () {
                            return $scope.items;
                        }
                    }
                });
            }
                

            
                if($rootScope.loginStatus === undefined){
                $rootScope.loginStatus = false;
                }

                $rootScope.logout = function (something) {
                    $rootScope.loginStatus = false;
                    console.log($scope.loginStatus);
                    $location.path("/login");
                };

                $scope.login = function (credentials) {
                    console.log(credentials)
                    var userInfo = {

                        "username": credentials.username,
                        "password": sha256_digest(credentials.password)

                    };
                    console.log(userInfo);

                    $http.post('http://comp490.duckdns.org/login', userInfo).success(function (response) {
                        $scope.response = response;
                        console.log($scope.response.result);
                        if ($scope.response.result == true) {
                            $rootScope.loginStatus = true;
                           $location.path("/profile");
                        } else {
                            $rootScope.loginStatus = false;
                            $scope.error = "The credientials entered were incorrect";
                        }
                        console.log($scope);
                    })
                };

            };

            loginController.$inject = ['$scope', '$http', '$modal', '$location' , '$rootScope'];

            angular.module('nexusApp')
                .controller('loginController', loginController);

        }());