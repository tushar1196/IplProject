var matches = [];
var deliveries = [];
const MATCH_WINNER = 10;
const MATCH_TOSS_WINNER = 6;
const SEASON = 1;
const MATCH_ID = 0;
const MATCH_CITY = 2;
const DELIVERY_BLOWING_TEAM = 3;
const DELIVERY_TOTAL_RUN = 17;
const DELIVERY_EXTRA_RUN = 16;
const DELIVERY_BLOWER = 8;
const DELIVERY_BALL = 5;
const DELIVERY_OVER = 4;
const DELIVERY_MATCH_ID = 0;

getMatchesData();
getDeliveriesData();
findNumberOfMatchesPlayedPerSeason(matches);
findNumberOfMatchesWonByTeam(matches);
findExtraRunsConcededPerTeamIn2016(matches, deliveries);
findMostEconomicalBlowerIn2015(matches, deliveries);
findMatchesPlayedInEachCity(matches);

function findMostEconomicalBlowerIn2015(matches, deliveries) {
    console.log("\n Most economical blower is =");
    let deliveriesDataIn2015 = [];
    let matchIdIn2015 = [];
    let oversBolledByBlower = [];
    let totalRunsGaveByBlower = [];
    let economyRateOfBlower = [];

    matches.forEach(match => {
        if (match.season == 2015) {
            matchIdIn2015.push(match.id);
        }
    });
    let blowerNameIn2015 = new Set();
    deliveries.forEach(delivery => {
        if (matchIdIn2015.includes(delivery.matchId)) {
            deliveriesDataIn2015.push(delivery);
            blowerNameIn2015.add(delivery.blower);
        }
    });
    deliveriesDataIn2015.forEach(delivery => {

        if (oversBolledByBlower[delivery.blower] == undefined) {
            let overs = (parseInt(delivery.over) * 6 + parseInt(delivery.ball)) / 6;
            oversBolledByBlower[delivery.blower] = overs;
        } else {
            let overs = (6 * (parseInt(delivery.over) + oversBolledByBlower[delivery.blower])) / 6;
            oversBolledByBlower[delivery.blower] = overs;
        }
    });
    deliveriesDataIn2015.forEach(delivery => {
        if (totalRunsGaveByBlower[delivery.blower] == undefined) {
            totalRunsGaveByBlower[delivery.blower] = parseInt(delivery.totalRuns);
        } else {
            totalRunsGaveByBlower[delivery.blower] = totalRunsGaveByBlower[delivery.blower] + parseInt(delivery.totalRuns);
        }
        blowerNameIn2015.forEach(blowerName => {
            if (totalRunsGaveByBlower[blowerName] == undefined || oversBolledByBlower[blowerName] == undefined) {
            } else {
                let object = new Object();
                let economy = totalRunsGaveByBlower[blowerName] / oversBolledByBlower[blowerName];
                object.blowerName = blowerName;
                object.economy = economy;
                economyRateOfBlower.push(object);
            }
        });
    });
    economyRateOfBlower.sort(function (a, b) { return a.economy - b.economy });
    let entry = economyRateOfBlower.pop();
    console.log(entry.blowerName + " = " + entry.economy);
}

function findMatchesPlayedInEachCity(matches) {
    console.log("Number of matches in city");
    let numberOfMatchesPlayedInCity = [];
    matches.shift();
    matches.pop();

    matches.forEach(match => {
        if (match.city.length > 0) {
            if (numberOfMatchesPlayedInCity[match.city] == undefined) {
                numberOfMatchesPlayedInCity[match.city] = 1;
            } else {
                numberOfMatchesPlayedInCity[match.city] = numberOfMatchesPlayedInCity[match.city] + 1;
            }
        }
    });
    console.log(numberOfMatchesPlayedInCity);
}

function findNumberOfMatchesWonByTeam(matches) {
    console.log("\nNo of matches won by team ");
    matches.shift();
    matches.pop();
    let numberOfMatchesWonByTeam = [];

    matches.forEach(match => {
        if (match.winner.length > 0) {
            if (numberOfMatchesWonByTeam[match.winner] == undefined) {
                numberOfMatchesWonByTeam[match.winner] = 1;
            } else {
                numberOfMatchesWonByTeam[match.winner] = numberOfMatchesWonByTeam[match.winner] + 1;
            }
        };
    });
    console.log(numberOfMatchesWonByTeam);
}

function findNumberOfMatchesPlayedPerSeason(matches) {
    console.log("\nNo of matches played per season");
    matches.shift();
    matches.pop();
    let numberOfMatchesPlayedPerSeason = new Map();
    matches.forEach(match => {
        let season = match.season.toString();
        if (numberOfMatchesPlayedPerSeason.get(season) == undefined) {
            numberOfMatchesPlayedPerSeason.set(season, 1);
        } else {
            numberOfMatchesPlayedPerSeason.set(season, numberOfMatchesPlayedPerSeason.get(season) + 1);
        }
    });
    console.log(numberOfMatchesPlayedPerSeason);
}

function findExtraRunsConcededPerTeamIn2016(matches, deliveries) {
    console.log("Extra Runs Conceded Per Team In 2016:");

    let deliveriesDataIn2016 = [];
    let matchIdsIn2016 = [];
    let extraRunsConceded = [];

    matches.forEach(match => {
        if (match.season == 2016) {
            matchIdsIn2016.push(match.id);
        }
    });
    deliveries.forEach(delivery => {
        if (matchIdsIn2016.includes(delivery.matchId)) {
            deliveriesDataIn2016.push(delivery);
        }
    });
    deliveriesDataIn2016.forEach(delivery => {
        if (extraRunsConceded[delivery.blowingTeam] == undefined) {
            extraRunsConceded[delivery.blowingTeam] = parseInt(delivery.extraRuns);
        } else {
            extraRunsConceded[delivery.blowingTeam] = extraRunsConceded[delivery.blowingTeam] + parseInt(delivery.extraRuns);
        }
    });
    console.log(extraRunsConceded);
}

function getDeliveriesData() {
    const fs = require('fs');
    var deliveriesDataInBuffer = fs.readFileSync("deliveries.csv");
    var deliveriesDataArray = deliveriesDataInBuffer.toString().split(/\r?\n/);
    deliveriesDataArray.forEach(element => {
        var delivery = {};
        delivery.matchId = element.split(",")[DELIVERY_MATCH_ID];
        delivery.blowingTeam = element.split(",")[DELIVERY_BLOWING_TEAM];
        delivery.totalRuns = element.split(",")[DELIVERY_TOTAL_RUN];
        delivery.extraRuns = element.split(",")[DELIVERY_EXTRA_RUN];
        delivery.blower = element.split(",")[DELIVERY_BLOWER];
        delivery.ball = element.split(",")[DELIVERY_BALL];
        delivery.over = element.split(",")[DELIVERY_OVER];
        deliveries.push(delivery);
    });
}

function getMatchesData() {
    const fs = require('fs');
    var matchesDataInBuffer = fs.readFileSync("matches.csv");
    var matchesDataArray = matchesDataInBuffer.toString().split(/\r?\n/);
    matchesDataArray.forEach(element => {
        var match = {};
        match.id = element.split(",")[MATCH_ID];
        match.season = element.split(",")[SEASON];
        match.city = element.split(",")[MATCH_CITY];
        match.winner = element.split(",")[MATCH_WINNER];
        match.toss_winner = element.split(",")[MATCH_TOSS_WINNER];
        matches.push(match);
    });
}