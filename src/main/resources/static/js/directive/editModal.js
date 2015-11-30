/**
 * Directive to Bootstrap Modal for orders editing.
 * 
 * Created by Неволин on 29.11.2015.
 */

app.directive('editModal', function() {
    return {
        restrict: 'E',
        templateUrl: 'template/edit-modal.html'
    };
});