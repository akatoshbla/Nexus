(function () {

    var profileController = function ($rootScope, $scope, $http, $modal, $location) {
        //allow animation for modal
        $scope.animationsEnabled = true;
        //function to close modal
        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
       

        //function to open modal and assign partial and controller to it
        $rootScope.editProfile = function (size) {

            var modalInstance = $modal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'app/views/editProfile.html',
                controller: 'editProfileController',
                size: size,
                resolve: {
                    items: function () {
                        return $scope.items;
                    }
                }
            });
        }
        $scope.editAbout = function (size) {

            var modalInstance = $modal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'app/views/editAbout.html',
                controller: 'editProfileController',
                size: size,
                resolve: {
                    items: function () {
                        return $scope.items;
                    }
                }
            });
        }


        $rootScope.profile = {
            join: 'Oct 25, 2015',
            lastSeen: 'Oct 25, 2015',
            realName: 'Phillip Tran',
            forumLvl: 'Lurker',
            shares: '88',
            likes: '888',
            posts: '8',
            followers: '8',
            description: 'Just a dude enjoying video games, dont be afraid to message me and friend me!',
            //currentGame: 'Counter Strike: Global offensive',
            userName: 'wond3rBread',
            profilePic: '/profile/philtran.jpg'

        }

        $scope.currentGame = [
            {
                gameName: 'Counter Strike: Global Offensive',
                gameLinks: '#'
            }
        ]

        $scope.links = [
                {
                    websiteName: 'Facebook',
                    socialLinks: '#'
            },
                {

                    websiteName: 'Twitter',
                    socialLinks: '#'
            }

        ]
            //favgame object?....
        $scope.friends = [
            {
                friendName: 'Liltwix',
                friendLink: '',
                friendPic: '/profile/alexhall.jpg'
            },
            {
                friendName: 'akatoshbla',
                friendLink: '',
                friendPic: '/profile/davidknopp.jpg'
            },
            {
                friendName: 'whitenoize',
                friendLink: '',
                friendPic: '/profile/bendluzak.jpg'
            }
        ]

        $scope.gamePlay = [
            {
                gameName: 'Counter Strike: Global Offensive',
                gamePic: '/games/cs-go-logo.jpg',
                gameForum: '#'
            },
            {
                gameName: 'Hearthstone',
                gamePic: '/games/hearthstone-logo.jpg',
                gameForum: '#'
            },
            {
                gameName: 'League of Legends',
                gamePic: '/games/lol-logo.jpg',
                gameForum: '#'
            }
        ]


    };

    profileController.$inject = ['$rootScope', '$scope', '$http', '$modal', '$location'];

    angular.module('nexusApp')
        .controller('profileController', profileController);

}());