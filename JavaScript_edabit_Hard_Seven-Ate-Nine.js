/*
  Author: Tom Murphy
  Last Modified: 2019-11-21 12:10
*/

// https://edabit.com/challenge/dHAk8zPwacSev6Hmv
// Seven Ate Nine
// A number can eat the number to the right of it if it's smaller than itself. After eating that number, it becomes the sum of itself and that number. Your job is to create a function that returns the final array after the leftmost element has finished "eating".
// Note: If given an empty string as an input, return [0, 0, 0].

function nomNom(slevin) {
  var eating = true;
  while (eating) {
    if (Array.isArray(slevin) == false) {
      eating = false;
      slevin = [0, 0, 0]
    } else if (slevin.length == 0) {
      eating = false;
      slevin = [0, 0, 0]
    } else if (slevin[0] <= slevin[1]) {
      eating = false;
    } else if (slevin.length > 1 && slevin[0] > slevin[1]) {
      slevin[0]+=slevin[1];
      slevin.splice(1,1);
      if (slevin.length == 1) {
        eating = false;
      }
    }
  }
  return slevin;
}

console.log(nomNom([5, 3, 7])); // ? [8, 7] ? [15]
  // 5 eats 3 to become 8
  // 8 eats 7 to become 15
console.log(nomNom([5, 3, 9])); // ? [8, 9]
  // 5 eats 3 to become 8
  // 8 cannot eat 9, since 8 < 9
console.log(nomNom([1, 2, 3])); // ? [1, 2, 3]
console.log(nomNom([2, 1, 3])); // ? [3, 3]
console.log(nomNom([8, 5, 9])); // ? [22]