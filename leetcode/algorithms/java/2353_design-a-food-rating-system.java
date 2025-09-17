// Source: https://leetcode.com/problems/design-a-food-rating-system/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-16
// At the time of submission:
//   Runtime 196 ms Beats 60.61%
//   Memory 73.92 MB Beats 61.21%

/****************************************
* 
* Design a food rating system that can do the following:
* • Modify the rating of a food item listed in the system.
* • Return the highest-rated food item for a type of cuisine in the system.
* Implement the `FoodRatings` class:
* • `FoodRatings(String[] foods, String[] cuisines, int[] ratings)` Initializes
* __ the system. The food items are described by `foods`, `cuisines` and `ratings`,
* __ all of which have a length of `n`.
* _   • `foods[i]` is the name of the `i^th` food,
* _   • `cuisines[i]` is the type of cuisine of the `i^th` food, and
* _   • `ratings[i]` is the initial rating of the `i^th` food.
* • `void changeRating(String food, int newRating)` Changes the rating of the
* __ food item with the name `food`.
* • `String highestRated(String cuisine)` Returns the name of the food item that
* __ has the highest rating for the given type of `cuisine`. If there is a tie,
* __ return the item with the lexicographically smaller name.
* Note that a string `x` is lexicographically smaller than string `y` if `x` comes
* _ before `y` in dictionary order, that is, either `x` is a prefix of `y`, or if
* _ `i` is the first position such that `x[i] != y[i]`, then `x[i]` comes before
* _ `y[i]` in alphabetic order.
*
* Example 1:
* Input
* ["FoodRatings", "highestRated", "highestRated", "changeRating", "highestRated", "changeRating", "highestRated"]
* [[["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], ["korean", "japanese", "japanese", "greek", "japanese", "korean"], [9, 12, 8, 15, 14, 7]], ["korean"], ["japanese"], ["sushi", 16], ["japanese"], ["ramen", 16], ["japanese"]]
* Output
* [null, "kimchi", "ramen", null, "sushi", null, "ramen"]
* Explanation
* FoodRatings foodRatings = new FoodRatings(["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], ["korean", "japanese", "japanese", "greek", "japanese", "korean"], [9, 12, 8, 15, 14, 7]);
* foodRatings.highestRated("korean"); // return "kimchi"
* // "kimchi" is the highest rated korean food with a rating of 9.
* foodRatings.highestRated("japanese"); // return "ramen"
* // "ramen" is the highest rated japanese food with a rating of 14.
* foodRatings.changeRating("sushi", 16); // "sushi" now has a rating of 16.
* foodRatings.highestRated("japanese"); // return "sushi"
* // "sushi" is the highest rated japanese food with a rating of 16.
* foodRatings.changeRating("ramen", 16); // "ramen" now has a rating of 16.
* foodRatings.highestRated("japanese"); // return "ramen"
* // Both "sushi" and "ramen" have a rating of 16.
* // However, "ramen" is lexicographically smaller than "sushi".
*
* Constraints:
* • 1 <= n <= 2 * 10^4
* • n == foods.length == cuisines.length == ratings.length
* • 1 <= foods[i].length, cuisines[i].length <= 10
* • `foods[i]`, `cuisines[I]` consist of lowercase English letters.
* • 1 <= ratings[i] <= 10^8
* • All the strings in `foods` are distinct.
* • `food` will be the name of a food item in the system across all calls to `changeRating`.
* • `cuisine` will be a type of cuisine of at least one food item in the system across all calls to `highestRated`.
* • At most `2 * 10^4` calls in total will be made to `changeRating` and `highestRated`.
* 
****************************************/

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class FoodRatings {
    // This solution uses two maps for efficient updates & lookups:
    // 1) foodMap: maps food name → Food object (cuisine + rating).
    // 2) cuisineMap: maps cuisine → TreeSet of foods sorted by rating
    //    (higher first, then lexicographically smaller if tied).
    // changeRating removes & reinserts in O(log n). highestRated
    // retrieves the top food in O(1). Overall complexity: O(log n)
    // per update, O(1) per query. Space: O(n).

    private static class Food {
        String name;
        String cuisine;
        int rating;

        Food(String name, String cuisine, int rating) {
            this.name = name;
            this.cuisine = cuisine;
            this.rating = rating;
        }
    }

    private Map<String, Food> foodMap;                   // food → details
    private Map<String, TreeSet<Food>> cuisineMap;       // cuisine → sorted foods

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodMap = new HashMap<>();
        cuisineMap = new HashMap<>();

        for (int i = 0; i < foods.length; i++) {
            Food food = new Food(foods[i], cuisines[i], ratings[i]);
            foodMap.put(foods[i], food);

            cuisineMap.computeIfAbsent(cuisines[i], k -> new TreeSet<>(
                (a, b) -> a.rating == b.rating
                        ? a.name.compareTo(b.name)
                        : b.rating - a.rating
            )).add(food);
        }
    }

    public void changeRating(String food, int newRating) {
        Food f = foodMap.get(food);
        TreeSet<Food> set = cuisineMap.get(f.cuisine);

        set.remove(f);       // O(log n)
        f.rating = newRating;
        set.add(f);          // O(log n)
    }

    public String highestRated(String cuisine) {
        return cuisineMap.get(cuisine).first().name; // O(1)
    }
}

/**
 * Your FoodRatings object will be instantiated and called as such:
 * FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
 * obj.changeRating(food,newRating);
 * String param_2 = obj.highestRated(cuisine);
 */