(function () {

        var loginController = function ($scope, $http, $modal, $modalInstance ) {

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
                

            

                $scope.loginStatus = false;

                $scope.logout = function (something) {
                    $scope.loginStatus = false;
                    console.log($scope.loginStatus);
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
                            $scope.loginStatus = true;
                        } else {
                            $scope.loginStatus = false;
                            $scope.error = "The credientials entered were incorrect";
                        }
                        console.log($scope);
                    })
                };

            };

            loginController.$inject = ['$scope', '$http', '$modal'];

            angular.module('nexusApp')
                .controller('loginController', loginController);

        }());