angular
  .module('ocrApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/participants/:raceId', {
    	  templateUrl: 'views/participants.html',
    	  controller: 'ParticipantsCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });