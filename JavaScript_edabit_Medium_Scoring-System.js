/*
  Author: Tom Murphy
  Last Modified: 2019-11-21 10:54
*/

// https://edabit.com/challenge/WvGmrCyiiZeunZLAg
// Scoring System
/*
Andy, Ben and Charlotte are playing a board game. The three of them decided to come up with a new scoring system. A player's first initial ("A", "B" or "C") denotes that player scoring a single point. Given a string of capital letters, return an array of the players' scores.

For instance, if ABBACCCCAC is written when the game is over, then Andy scored 3 points, Ben scored 2 points, and Charlotte scored 5 points, since there are 3 instances of letter A, 2 instances of letter B, and 5 instances of letter C. So the array [3, 2, 5] should be returned.

Notes:
  If given an empty string as an input, return [0, 0, 0].
*/

function calculateScores(initials) {
  var scores = [0, 0, 0];
  for (var i = 0; i < initials.length; i++) {
    if (initials.charAt(i) == "A") {
      scores[0]+=1
    } else if (initials.charAt(i) == "B") {
      scores[1]+=1
    } else if (initials.charAt(i) == "C") {
      scores[2]+=1
    }
  }

  return scores;
}

console.log(calculateScores("A")); // ? [1, 0, 0]
console.log(calculateScores("ABC")); // ? [1, 1, 1]
console.log(calculateScores("ABCBACC")); // ? [2, 2, 3]