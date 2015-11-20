(function () {

    var editProfileController = function ($rootScope,$scope, $http, $modalInstance, $location) {
        //start by having the modal not shown
        this.showModal = false;

        this.showView = false;

        this.counter = 1;
        //function to reverse value of toggleDialog
        $scope.toggleDialog = function () {

            this.showModal = !this.showModal;
        }
        
        $scope.editRealName = function() {
      
            $http.post('http://comp490.duckdns.org/realName', $scope.newRealName).success(function(response){
                $rootScope.profile.realName = $scope.newRealName;
                console.log(response);
                $scope.close();
            })
        };
        $scope.editAboutUser = function() {
          
            $http.post('http://comp490.duckdns.org/userDesc', $scope.summary).success(function(response){
                $rootScope.profile.description = $scope.summary;
                console.log(response);
                $scope.close();
            })
        };
        
        //close the modal instance
        $scope.close = function () {
            console.log('closed');
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

    editProfileController.$inject = ['$rootScope','$scope', '$http', '$modalInstance', '$location'];

    angular.module('nexusApp')
        .controller('editProfileController', editProfileController);


}());