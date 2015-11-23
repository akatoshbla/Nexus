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
                //function to open modal and assign partial and controller to it
            $rootScope.editProfilePic = function (size) {

                var modalInstance = $modal.open({
                    animation: $scope.animationsEnabled,
                    templateUrl: 'app/views/editProfilePic.html',
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
            $scope.LoadProfile = function () {
                console.log("here");
                //function that is run at controller launch to get profile data
                $http.get('http://comp490.duckdns.org/profile').success(function (response) {

                         console.log("here2");
                        $rootScope.profile = {
                            join: response.joined,
                            lastSeen: response.lastOnline,
                            realName: response.realName,
                            forumLvl: 'Lurker',
                            shares: response.shares,
                            likes: response.likes,
                            posts: response.posts,
                            followers: response.followers,
                            description: response.userDesc,
                            //currentGame: 'Counter Strike: Global offensive',
                            userName: response.userName,
                            profilePic: '/profile/philtran.jpg'

                        }
                    }
                )};
            

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
                $scope.LoadProfile();


            };

            profileController.$inject = ['$rootScope', '$scope', '$http', '$modal', '$location'];

            angular.module('nexusApp')
                .controller('profileController', profileController);

        }());