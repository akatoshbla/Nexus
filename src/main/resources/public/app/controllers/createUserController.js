(function () {

    var createUserController = function ($scope, $http, $modalInstance, $location) {
        this.showModal = false;

        this.showView = false;

        this.counter = 1;

        $scope.toggleDialog = function () {

            this.showModal = !this.showModal;

        }
        $scope.close = function () {
            $modalInstance.dismiss('cancel');
        };
        this.toggleView = function () {

            this.showView = !this.showView;

        }

        this.changeDisplay = function () {

            this.counter++;

        }
        $scope.signUp = function (credentials) {
           
            var userInfo = {

                "username": credentials.username,
                "password": sha256_digest(credentials.password),
                "password2": sha256_digest(credentials.password2)

            };
            console.log(userInfo);
            if (credentials.password === credentials.password2) {
                $http.post('http://comp490.duckdns.org/create', userInfo).success(function (response) {
                    $scope.response = response;
                    console.log($scope.response.result);
                    if ($scope.response.result == true && credentials.password === credentials.password2) {
                        $scope.loginStatus = true;
                        $modalInstance.dismiss('cancel');
                        $location.path("/");
                        $scope.close();
                    } else {
                        $scope.loginStatus = false;
                        $scope.error = "The credientials entered were incorrect";
                    }
                    console.log($scope);


                })
            } else {
                $scope.error = "Passwords do not match"
            }
        };

    }

    createUserController.$inject = ['$scope', '$http', '$modalInstance', '$location'];

    angular.module('nexusApp')
        .controller('createUserController', createUserController);


}());