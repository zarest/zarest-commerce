/**
 * Created by meysamabl on 11/20/14.
 */

var zarestApp = angular.module('zarestApp', ['zarestControllers'
    //'ngRoute',
    //'phonecatAnimations',
    //'phonecatFilters',
    //'phonecatServices'
]);

'use strict';

/* Controllers */

var zarestControllers = angular.module('zarestControllers', []);

zarestControllers.controller('PhoneListCtrl', ['$scope', 'Phone',
    function($scope, Phone) {
        $scope.phones = Phone.query();
        $scope.orderProp = 'age';
    }]);

zarestControllers.controller('PhoneDetailCtrl', ['$scope', '$routeParams', 'Phone',
    function($scope, $routeParams, Phone) {
        $scope.phone = Phone.get({phoneId: $routeParams.phoneId}, function(phone) {
            $scope.mainImageUrl = phone.images[0];
        });

        $scope.setImage = function(imageUrl) {
            $scope.mainImageUrl = imageUrl;
        }
    }]);
