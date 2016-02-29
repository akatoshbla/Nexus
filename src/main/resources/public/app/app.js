(function () {


    var app = angular.module('nexusApp', ['ngRoute', 'angularModalService', 'ui.bootstrap', 'ngFileUpload', 'ngImgCrop']);


    app.config(function ($routeProvider, $httpProvider) {

        $routeProvider
            .when('/', {
                controller: 'loginController',
                templateUrl: 'app/views/login.html'
            }).when('/login', {
                controller: 'loginController',
                templateUrl: 'app/views/login.html'
            })
            .when('/verify', {
                controller: 'verifyController',
                templateUrl: 'app/views/verify.html'
            })
            .when('/profile', {
                controller: 'profileController',
                templateUrl: 'app/views/profile.html'
            })
            .when('/messages', {
                controller: 'messageController',
                templateUrl: 'app/views/messages.html'
            }).when('/searchedProfile', {
                controller: 'searchedProfileController',
                templateUrl: 'app/views/searchedProfile.html'
            })
            .when('/league', {
                controller: 'leagueController',
                templateUrl: 'app/views/league.html'
            })
            .when('/aboutUs', {
                controller: 'aboutUsController',
                templateUrl: 'app/views/aboutUs.html'
            })
            .when('/worldOfWarcraft', {
                controller: 'worldOfWarcraftController',
                templateUrl: 'app/views/worldOfWarcraft.html'
            })
            .when('/diablo3', {
                controller: 'diablo3Controller',
                templateUrl: 'app/views/diablo3.html'
            })
            .when('/summonerPage',{
                controller: 'leagueResultController',
                templateUrl: 'app/views/summonerPage.html'
            })
            .otherwise({
                redirectTo: '/'
            });

    });


}());