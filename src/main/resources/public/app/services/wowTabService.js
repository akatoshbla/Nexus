(function () {

    var wowTabService = function () {  
       
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

            } else if (requestTab === "3v3") {
                tabResult = {
                    arena2v2: false,
                    arena3v3: true,
                    arena5v5: false,
                    rbg: false
                }

            } else if (requestTab === "rbg") {
                tabResult = {
                    arena2v2: false,
                    arena3v3: false,
                    arena5v5: false,
                    rbg: true
                }

            }
            return tabResult;
        };

        this.getGender = function (gender) {
            if (gender === 0) {
                gender = 'Male';
            } else {
                gender = 'Female';
            }
            return gender;
        };
        
        this.getRace = function (race) {
            if (race === 1) {
                race = 'Human';
            } else if (race === 2) {
                race = 'Orc';
            } else if (race === 3) {
                race = 'Dwarf';
            } else if (race === 4) {
                race = 'Night Elf';
            } else if (race === 5) {
                race = 'Undead';
            } else if (race === 6) {
                race = 'Tauren';
            } else if (race === 7) {
                race = 'Gnome';
            } else if (race === 8) {
                race = 'Troll';
            } else if (race === 9) {
                race = 'Goblin';
            } else if (race === 10) {
                race = 'Blood Elf';
            } else if (race === 11) {
                race = 'Draenei';
            } else if (race === 22) {
                race = 'Worgen';
            } else if (race === 24 || race === 25 || race === 26) {
                race = 'Pandaren';
            }
            return race;
        };
        
        this.getFaction = function (faction) {
            if (faction === 0) {
                faction = "Alliance";
            } else {
                faction = "Horde";
            }
            return faction;
        };
        
        this.getClass = function (currentClass) {
            var classArray = ["Warrior", "Paladin", "Hunter", "Rogue", "Priest", "Death Knight", "Shaman", "Mage", "Warlock", "Monk", "Druid"];
            currentClass = classArray[currentClass - 1];
            return currentClass;

        };
        
    }


    wowTabService.$inject = [];
    angular.module('nexusApp')
        .service('wowTabService', wowTabService);

}());