(function () {

    var searchedProfileController = function ($scope, $http) {

        $scope.loadFriendProfile = function(){
            $http.get('http://comp490.duckdns.org/profile')
        }

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