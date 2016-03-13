(function () {

        var wowTabService = function () {
            var self = this;
            this.tabsPvp = function (requestTab) {
                var tabResult;
                if (requestTab === "2v2") {
                     tabResult = {
                        arena2v2: true,
                        arena3v3: false,
                        arena5v5: false,
                        rbg: false
                    }
                } else if (requestTab === "5v5") {
                    tabResult = {
                    arena2v2: false,
                    arena3v3: false,
                    arena5v5: true,
                    rbg: false
                    }

                }
                else if (requestTab === "3v3") {
                    tabResult = {
                    arena2v2: false,
                    arena3v3: true,
                    arena5v5: false,
                    rbg: false
                    }

                }
                 else if (requestTab === "rbg") {
                    tabResult = {
                    arena2v2: false,
                    arena3v3: false,
                    arena5v5: false,
                    rbg: true
                    }

                }
                return tabResult;
            }
        }



            wowTabService.$inject = [];
            angular.module('nexusApp')
                .service('wowTabService', wowTabService);

        }());