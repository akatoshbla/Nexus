(function () {


    var app = angular.module('nexusApp', ['ngRoute', 'angularModalService', 'ui.bootstrap']);


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
            })
            .when('/league', {
                controller: 'leagueController',
                templateUrl: 'app/views/league.html'
            })
            .when('/aboutUs', {
                controller: 'aboutUsController',
                templateUrl: 'app/views/aboutUs.html'
            })
            .otherwise({
                redirectTo: '/'
            });

    });


}());