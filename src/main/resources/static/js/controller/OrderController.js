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
    $http.post('/api/orders/page/size').success(function (response) {
        $scope.pageSize = parseInt(response);
    });

    $scope.currentPage = 0;

    this.firstPage = function () {
        return $scope.currentPage == 0;
    };
    this.lastPage = function () {
        var lastPageNum = Math.ceil($scope.ordersCount / $scope.pageSize - 1);
        return $scope.currentPage == lastPageNum;
    };

    this.numberOfPages = function () {
        return Math.ceil($scope.ordersCount / $scope.pageSize);
    };
    this.pageBack = function () {
        $scope.currentPage--;
        loadOrders();
    };
    this.pageForward = function () {
        $scope.currentPage++;
        loadOrders();
    };
    //endregion

    this.updateSearchQuery = function() {
        $http.post('/api/orders/searchquery', 'infoSearchQuery=' + $scope.searchQuery).success(function () {
            loadOrders();
        });
    };

    function loadOrders() {
        $http.post('/api/orders/page/' + $scope.currentPage).success(function (data) {
            $scope.ordersList = data;
        });
        $http.post('/api/orders/count').success(function (response) {
            $scope.ordersCount = parseInt(response);
        });
    }

    loadOrders();

    this.editOrderModal = function (order) {
        $scope.currentOrder = angular.copy(this.emptyOrder);
        if (order) {
            $scope.currentOrder = order;
        }

        $('#orderFormModal').modal('show');
    };

    this.saveOrder = function () {
        $http.post('/api/orders/save', $scope.currentOrder)
            .then(function successCallback() {
                if (!$scope.currentOrder.id) {
                    loadOrders();
                }
                $('#orderFormModal').modal('hide');
            }, function errorCallback(response) {
                console.log("Something goes wrong: ", response);
                alert("Сохранение сделки прошло как-то так себе.");
            });
    };

    this.removeOrder = function (order) {
        $http.post('/api/orders/remove', order).then(function successCallback() {
            loadOrders();
        });
    };

    this.addEmptyClient = function (order) {
        order.clients.push(angular.copy(this.emptyClient));
    };

    this.removeLastClient = function (order) {
        order.clients.pop();
    };

    //this.getFilteredOrders = function () {
    //    var searchQuery = this.searchQuery;
    //    var filteredOrders = [];
    //
    //    if (this.searchQuery) {
    //        $scope.ordersList.forEach(function (order) {
    //            if (order.info.indexOf(searchQuery) > -1) {
    //                filteredOrders.push(order);
    //            }
    //        });
    //    }
    //    else filteredOrders = $scope.ordersList;
    //    filteredOrders.sort(function (a, b) {
    //        return a.id < b.id;
    //    });
    //
    //    return filteredOrders;
    //};

});