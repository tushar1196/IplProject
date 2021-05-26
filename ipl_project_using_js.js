var matches= [];
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
findExtraRunsConcededPerTeamIn2016(matches, deliveries);


































function getDeliveriesData() {
    const fs = require('fs');
    var deliveriesDataInBuffer = fs.readFileSync('deliveries.csv');
    var deliveriesDataArray = deliveriesDataInBuffer.toString().split(/\r?\n/);
    deliveriesDataArray.forEach(element => {
        var delivery={};
        delivery.matchId=element.split(",")[DELIVERY_MATCH_ID];
        delivery.blowingTeam=element.split(",")[DELIVERY_BLOWING_TEAM];
        delivery.totalRuns=element.split(",")[DELIVERY_TOTAL_RUN];
        delivery.extraRuns=element.split(",")[DELIVERY_EXTRA_RUN];
        delivery.blower=element.split(",")[DELIVERY_BLOWER];
        delivery.ball=element.split(",")[DELIVERY_BALL];
        delivery.over=element.split(",")[DELIVERY_OVER];
        deliveries.push(delivery);
    });
}

function getMatchesData() {
    const fs = require('fs');
    var matchesDataInBuffer = fs.readFileSync('matches.csv');
    var matchesDataArray = matchesDataInBuffer.toString().split(/\r?\n/);
    matchesDataArray.forEach(element => {
        var match={};
        match.id=element.split(",")[MATCH_ID];
        match.season=element.split(",")[SEASON];
        match.city=element.split(",")[MATCH_CITY];
        match.winner=element.split(",")[MATCH_WINNER];
        match.toss_winner=element.split(",")[MATCH_TOSS_WINNER];
        matches.push(match);
    });
}

    
