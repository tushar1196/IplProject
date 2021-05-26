var matches= [];
var deliveries = [];

const MATCH_WINNER = 10;
const MATCH_TOSS_WINNER = 6;
const SEASON = 1;
const MATCH_ID = 0;
const MATCH_CITY = 2;






getMatchesData();
getDeliveriesData();












































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

    
