/**
 * Main application controller.
 * Using to control all actions with orders (and customers, because I don't know, how to separate clients and orders).
 *
 * Created by Неволин on 29.11.2015.
 */

app.controller('OrderController', function ($scope, $http) {
    this.emptyClient = { id: null, firstName: null, lastName: null, orderId: null };
    this.emptyOrder = { id: null, info: null };
    this.emptyDeal = {order: this.emptyOrder, clients: [ this.emptyClient ] };
    this.searchQuery = null;

    // On load fetching deals (order + clients) list.
    $http.post('/api/orders/').success(function (data) {
        $scope.dealsList = data;
    });

    this.editDealModal = function(deal) {
        $scope.currentDeal = angular.copy(this.emptyDeal);
        if (deal) {
            $scope.currentDeal = deal;
        }

        $('#dealFormModal').modal('show');
    };

    this.saveDeal = function() {
        $http.post('/api/orders/save', $scope.currentDeal.order)
            .then(function successCallback(response) {
                if (!$scope.currentDeal.order.id) {
                    $scope.currentDeal.order = response.data;
                    saveCurrentDealClients();
                    $scope.dealsList.push($scope.currentDeal);
                }
                else {
                    saveCurrentDealClients();
                }

                $('#dealFormModal').modal('hide');
            }, function errorCallback(response) {
                console.log("Something goes wrong: ", response);
                alert("Сохранение сделки прошло как-то так себе.");
            });
    };

    function saveCurrentDealClients() {
        $scope.currentDeal.clients.forEach(function (client) {
            if (!client.orderId) {
                client.orderId = $scope.currentDeal.order.id;
            }
            $http.post('/api/clients/save', client);
        });
    }

    this.removeDeal = function(deal) {
        $http.post('/api/orders/remove', deal.order);

        var index = $scope.dealsList.indexOf(deal);
        if (index > -1) {
            $scope.dealsList.splice(index, 1);
        }
    };

    this.addEmptyClient = function(deal) {
        deal.clients.push(angular.copy(this.emptyClient));
    };

    this.removeLastClient = function(deal) {
        deal.clients.pop();
    };

    this.getFilteredDeals = function() {
        var searchQuery = this.searchQuery;
        var filteredDeals = [];

        if (this.searchQuery) {
            $scope.dealsList.forEach(function(deal) {
                if (deal.order.info.indexOf(searchQuery) > -1) {
                    filteredDeals.push(deal);
                }
            });
        }
        else filteredDeals = $scope.dealsList;
        filteredDeals.sort(function(a, b) {
            return a.order.id < b.order.id;
        });

        return filteredDeals;
    };

    //region Paginator
    this.currentPage = 0;
    this.itemsPerPage = 5;

    this.firstPage = function() {
        return this.currentPage == 0;
    };
    this.lastPage = function() {
        var lastPageNum = Math.ceil(this.getFilteredDeals().length / this.itemsPerPage - 1);
        return this.currentPage == lastPageNum;
    };
    this.numberOfPages = function() {
        return Math.ceil(this.getFilteredDeals().length / this.itemsPerPage);
    };
    this.startItem = function() {
        return this.currentPage * this.itemsPerPage;
    };
    this.pageBack = function() {
        this.currentPage--;
    };
    this.pageForward = function() {
        this.currentPage++;
    };
    //endregion
});