angular.module('os.administrative.order.returnspecimen', [])
  .controller('ReturnSpecimenCtrl', function($scope, $state, $translate, order, SpecimensHolder, SpecimenEvent,
    DistributionOrder, Alerts) {
    function init() {
      $scope.items = [];
      $scope.returnEvent = {};
      $scope.tableCtrl = {};
      $scope.order = order;

      if (angular.isArray(SpecimensHolder.getSpecimens())) {
        $scope.items = SpecimensHolder.getSpecimens();
        SpecimensHolder.setSpecimens(null);
      } else {
        $state.go('order-detail.overview', {orderId: order.id});
        return;
      }

      loadReturnEvent();
    }

    function loadReturnEvent() {
      SpecimenEvent.getEvents().then(function(events) {
        $scope.returnEvent = events.filter(function(event) {
          return event.name == 'SpecimenReturnEvent';
        })[0];

        initOpts();
      });
    }

    function getSpecimenOpts(items) {
      return items.map(
        function(item) {
          return {
            key: {
              id: item.id,
              objectId: item.specimen.id,
              label: item.specimen.label,
            },
            appColumnsData: {},
            records: []
          };
        }
      );
    }

    function initOpts() {
      var opts = {
        formId            : $scope.returnEvent.formId,
        appColumns        : [],
        tableData         : getSpecimenOpts($scope.items),
        idColumnLabel     : $translate.instant('specimens.title'),
        mode              : 'add',
        allowRowSelection : false,
        onValidationError : onValidationError
      };

      $scope.returnOpts = opts;
    }

    function onValidationError() {
      Alerts.error('common.form_validation_error');
    }

    $scope.copyFirstToAll = function() {
      $scope.tableCtrl.ctrl.copyFirstToAll();
    }

    $scope.returnSpecimen = function() {
      var tableCtrl = $scope.tableCtrl.ctrl;
      var data = tableCtrl.getData();
      if (!data) {
        return;
      }

      $scope.order.returnSpecimen(data).then(
        function(savedData) {
          $scope.back();
          Alerts.success('orders.specimen_returned');
        }
      );
    }

    init();
  });
