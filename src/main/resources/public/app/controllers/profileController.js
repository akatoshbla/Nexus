(function () {

    var profileController = function ($scope) {
        $scope.profile = {
            join: '2',
            lastSeen: '2/2/2',
            realName: '2',
            forumLvl: '2',
            shares: '2',
            likes: '2',
            posts: '2',
            followers: '2',
            description: '2',
            favGame: '2',
            userName: '2',
            profilePic: '200.jpg'

        }

        $scope.links = [
            {
                socialLinks: 'http://www.google.com',
                websiteName: 'google'
            },
            {
                socialLinks: 'http://www.google.com',
                websiteName: 'google'
            },
            {
                socialLinks: 'http://www.google.com',
                websiteName: 'google'
            }

        ]
        //favgame object?....
        $scope.friends = [
            {
                friendName: '2',
                friendLink: 'http://www.google.com',
                friendPic: '272x150.jpg'
            }
        ]

        $scope.gamePlay = [
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            },
            {
                gameName: '2',
                gamePic: '272x150.jpg',
                gameForum:'http://www.google.com'
            }
        ]


    };

    profileController.$inject = ['$scope'];

    angular.module('nexusApp')
        .controller('profileController', profileController);

}());