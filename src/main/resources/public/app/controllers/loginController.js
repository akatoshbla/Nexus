(function () {

        var loginController = function ($scope, $http, $modal, $location, $rootScope ) {
            //allow animation for modal
            $scope.animationsEnabled = true;
            //function to close modal
              $scope.cancel = function () {
            $modalInstance.dismiss();
        };
            //function to open modal and assign partial and controller to it
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
                

             // if loginStatus is undefined assign false to it
                if($rootScope.loginStatus === undefined){
                $rootScope.loginStatus = false;
                }
                //function to logout
                $rootScope.logout = function (something) {
                    $rootScope.loginStatus = false;
                    console.log($scope.loginStatus);
                    $location.path("/login");
                };
                //login if username and password are valid
                $scope.login = function (credentials) {
                    console.log(credentials)
                    $rootScope.username = credentials.username;
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
            
            //dependancy injection for minification
            loginController.$inject = ['$scope', '$http', '$modal', '$location' , '$rootScope'];
            // declare controller as part of the app
            angular.module('nexusApp')
                .controller('loginController', loginController);

        }());