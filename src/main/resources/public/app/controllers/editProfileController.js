(function () {

    var editProfileController = function ($scope, $http, $modalInstance, $location) {
        //start by having the modal not shown
        this.showModal = false;

        this.showView = false;

        this.counter = 1;
        //function to reverse value of toggleDialog
        $scope.toggleDialog = function () {

            this.showModal = !this.showModal;

        }
        //close the modal instance
        $scope.close = function () {
            $modalInstance.dismiss('cancel');
        };
    
        this.toggleView = function () {

            this.showView = !this.showView;

        }

        this.changeDisplay = function () {

            this.counter++;

        }
        //function that changes real name
        $scope.changeRealName = function (credentials) {

        };
       //validation function for email
      
    }

    editProfileController.$inject = ['$scope', '$http', '$modalInstance', '$location'];

    angular.module('nexusApp')
        .controller('editProfileController', editProfileController);


}());