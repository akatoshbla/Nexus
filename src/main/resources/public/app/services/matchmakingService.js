(function () {

        var matchMakingService = function () {

            this.tabs = function (requestTab) {
                var tabResult;
                if (requestTab === "matchmaking") {
                    tabResult = {
                        matchmaking: true,
                        match: false

                    }
                } else {
                    tabResult = {
                        matchmaking: false,
                        match: true
                    }
                }


                return tabResult;
            };

        }

    


    matchMakingService.$inject = []; angular.module('nexusApp')
    .service('matchMakingService', matchMakingService);

}());