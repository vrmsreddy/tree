angular.module('ocrApp')
  .controller('ParticipantsCtrl', function ($scope, $http, $routeParams) {
	  $http({
          method: 'GET',
          url: '/ms-races/participants/' + $routeParams.raceId
      }).then(function (response) {
    	  $scope.participants = response.data;
    	  $scope.currentServer = response.headers(["server-name"]);
      }, function(response) {
    	  console.error('Error requesting participants.')
      });
  });