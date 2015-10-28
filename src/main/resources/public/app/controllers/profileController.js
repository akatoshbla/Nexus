(function () {

    var profileController = function ($scope) {
        $scope.profile = {
            join: 'Oct 25, 2015',
            lastSeen: 'Oct 25, 2015',
            realName: 'Phillip Tran',
            forumLvl: 'Lurker',
            shares: '88',
            likes: '888',
            posts: '8',
            followers: '8',
            description: 'Just a dude enjoying video games, dont be afraid to message me and friend me!',
            favGame: 'Counter Strike: Global offensive',
            userName: 'wond3rBread',
            profilePic: '/profile/philtran.jpg'

        }

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
                friendLink: '#',
                friendPic: '/profile/alexhall.jpg'
            },
            {
                friendName: 'akatoshbla',
                friendLink: '#',
                friendPic: '/profile/davidknopp.jpg'
            },
            {
                friendName: 'whitenoize',
                friendLink: '#',
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

    profileController.$inject = ['$scope'];

    angular.module('nexusApp')
        .controller('profileController', profileController);

}());