/**
 * Main application controller.
 * Using to control all actions with orders (and customers, because I don't know, how to separate clients and orders).
 *
 * Created by Неволин on 29.11.2015.
 */

app.controller('OrderController', function ($scope, $http) {
    this.emptyClient = {id: null, firstName: null, lastName: null, orderId: null};
    this.emptyOrder = {id: null, info: null, clients: [this.emptyClient]};
    $scope.searchQuery = "";


    //region Paginator
    $scope.currentPage = 0;

    this.pageBack = function () {
        $scope.currentPage--;
        $scope.updateOrders();
    };
    this.pageForward = function () {
        $scope.currentPage++;
        $scope.updateOrders();
    };
    //endregion

    $scope.updateOrders = function() {
        $http.get('/api/orders/page/?page=' + $scope.currentPage + "&" + 'infoSearchQuery=' + $scope.searchQuery)
            .success(function (data) {
                $scope.ordersPage = data;
            });
    };

    $scope.updateOrders();

    this.editOrderModal = function (order) {
        $scope.currentOrder = angular.copy(this.emptyOrder);
        if (order) {
            $scope.currentOrder = order;
        }

        $('#orderFormModal').modal('show');
    };

    this.saveOrder = function () {
        console.log($scope.currentOrder);
        $http.post('/api/orders/save', $scope.currentOrder)
            .then(function successCallback() {
                if (!$scope.currentOrder.id) {
                    $scope.updateOrders();
                }
                $('#orderFormModal').modal('hide');
            }, function errorCallback(response) {
                console.log("Something goes wrong: ", response);
                alert("Сохранение сделки прошло как-то так себе.");
            });
    };

    this.removeOrder = function (order) {
        $http.post('/api/orders/remove', order).success(function() {
            $scope.updateOrders();
        });
    };

    this.addEmptyClient = function (order) {
        order.clients.push(angular.copy(this.emptyClient));
    };

    this.removeLastClient = function (order) {
        order.clients.pop();
    };

});