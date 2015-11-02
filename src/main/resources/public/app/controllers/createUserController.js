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
                "password2": sha256_digest(credentials.password2),
                "email": credentials.email
            };
            if(validateEmail(credentials.email)){
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
                $scope.error = "Passwords do not match";
            }
        }
        else{
             $scope.error = "Invalid Email";
        }
        };
       //validation function for email
        function validateEmail(email) {
            var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            return re.test(email);
        }
    }

    createUserController.$inject = ['$scope', '$http', '$modalInstance', '$location'];

    angular.module('nexusApp')
        .controller('createUserController', createUserController);


}());