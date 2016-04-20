(function () {

    var searchedProfileController = function ($scope, $http) {

        $scope.friendExist = "";

        //add friend to user database
        $scope.addFriend = function(){

        }

        //checks if this profile is a friend or not a friend and show button
        $scope.friendCheck = function(){

        }

        $scope.friendSearch = function(){

        }


    };

    searchedProfileController.$inject = ['$scope'];

    angular.module('nexusApp')
        .controller('searchedProfileController', searchedProfileController);

}());