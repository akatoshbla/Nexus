(function () {

    var editProfileController = function ($rootScope,$scope, $http, $modalInstance, $location, Upload, $timeout) {
        //start by having the modal not shown
        this.showModal = false;

        this.showView = false;

        this.counter = 1;
        //function to reverse value of toggleDialog
        $scope.toggleDialog = function () {

            this.showModal = !this.showModal;
        }
        
        $scope.editRealName = function() {
       var jsonObject = {
          realName: $scope.newRealName
       }
            $http.post('http://comp490.duckdns.org/realName', jsonObject).success(function(response){
                $rootScope.profile.realName = $scope.newRealName;
                console.log(response);
                $scope.close();
            })
        };
        $scope.editAboutUser = function() {
           var jsonObject = {
          userDesc: $scope.summary
       }
            $http.post('http://comp490.duckdns.org/userDesc', jsonObject).success(function(response){
                $rootScope.profile.description = $scope.summary;
                console.log(response);
                $scope.close();
            })
        };
        
           $scope.editCurrentGames = function() {
     
       
            $http.post('http://comp490.duckdns.org/currentGames', $scope.currentGames).success(function(response){
              //  $rootScope.profile.description = $scope.summary;
              //  console.log(response);
                $rootScope.profile.currentGames = response.currentGames;
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
         $scope.upload = function (dataUrl) {
        Upload.upload({
            url: 'https://angular-file-upload-cors-srv.appspot.com/upload',
            data: {
                file: Upload.dataUrltoBlob(dataUrl)
            },
        }).then(function (response) {
            $timeout(function () {
                $scope.result = response.data;
            });
        }, function (response) {
            if (response.status > 0) $scope.errorMsg = response.status 
                + ': ' + response.data;
        }, function (evt) {
            $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
        });
    }
       
      
      
    }

    editProfileController.$inject = ['$rootScope','$scope', '$http', '$modalInstance', '$location', 'Upload', '$timeout'];

    angular.module('nexusApp')
        .controller('editProfileController', editProfileController);


}());