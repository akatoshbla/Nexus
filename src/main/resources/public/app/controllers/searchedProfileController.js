(function () {

    var searchedProfileController = function ($scope, $http) {

        //add friend to user database
        $scope.addFriend = function(){

        }

        $scope.friendSearch = function(){

        }


    };

    searchedProfileController.$inject = ['$scope'];

    angular.module('nexusApp')
        .controller('searchedProfileController', searchedProfileController);

}());