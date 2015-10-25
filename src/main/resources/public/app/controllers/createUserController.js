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
            console.log(credentials)
            var userInfo = {

                "username": credentials.username,
                "password": credentials.password,
                "password2": credentials.password2

            };
            console.log(userInfo);

            $http.post('http://comp490.duckdns.org/create', userInfo).success(function (response) {
                $scope.response = response;
                console.log($scope.response.result);
                if ($scope.response.result == true && credentials.password === credentials.password2) {
                    $scope.loginStatus = true;
                    $location.path("/");
                } else {
                    $scope.loginStatus = false;
                    $scope.error = "The credientials entered were incorrect";
                }
                console.log($scope);


            })
        };

    }

    createUserController.$inject = ['$scope', '$http', '$modalInstance', '$location'];

    angular.module('nexusApp')
        .controller('createUserController', createUserController);


}());