

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
// findNumberOfMatchesPlayedPerSeason(matches);
findNumberOfMatchesWonByTeam(matches);
// findExtraRunsConcededPerTeamIn2016(matches, deliveries);




function findNumberOfMatchesWonByTeam(matches) {
    console.log("\nNo of matches won by team ");
    matches.shift();
    matches.pop();
    let numberOfMatchesWonByTeam = [];

    matches.forEach(match => {
        if (match.winner.length>0) {
            if (numberOfMatchesWonByTeam[match.winner] == undefined) {
                numberOfMatchesWonByTeam[match.winner]= 1;
            } else {
                numberOfMatchesWonByTeam[match.winner]= numberOfMatchesWonByTeam[match.winner] + 1;
            }
        };    
    });    
    console.log(numberOfMatchesWonByTeam);
}











































function findNumberOfMatchesPlayedPerSeason(matches) {
    console.log("\n Number Of Matches Played Per Season:");
    let numberOfMatchesPerSeason = [];
    
    matches.forEach(match => {
        if(numberOfMatchesPerSeason[match.season] == undefined) {
            numberOfMatchesPerSeason[match.season] = 1;
        } else  {
            numberOfMatchesPerSeason[match.season] = numberOfMatchesPerSeason[match.season] + 1;
        }
    });
    for(var key in numberOfMatchesPerSeason) {
        if (key.length==4) {
            console.log(key +" = "+ numberOfMatchesPerSeason[key]);      
        }
    }
}

function findExtraRunsConcededPerTeamIn2016(matches, deliveries) {

    console.log( "Extra Runs Conceded Per Team In 2016:");

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
            extraRunsConceded[delivery.blowingTeam] = extraRunsConceded[delivery.blowingTeam]+ parseInt(delivery.extraRuns);
        }
        
    });
    console.log(extraRunsConceded);
}

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

    
