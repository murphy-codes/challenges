// Source: https://leetcode.com/find-all-possible-recipes-from-given-supplies/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21

/****************************************
* 
* You have information about `n` different recipes. You are given a string array `recipes` and a 2D string array `ingredients`. The `i^th` recipe has the name `recipes[i]`, and you can create it if you have all the needed ingredients from `ingredients[i]`. A recipe can also be an ingredient for other recipes, i.e., `ingredients[i]` may contain a string that is in `recipes`.
* You are also given a string array `supplies` containing all the ingredients that you initially have, and you have an infinite supply of all of them.
* Return a list of all the recipes that you can create. You may return the answer in any order.
* Note that two recipes may contain each other in their ingredients.
*
* Example 1:
* Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
* Output: ["bread"]
* Explanation:
* We can create "bread" since we have the ingredients "yeast" and "flour".
*
* Example 2:
* Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
* Output: ["bread","sandwich"]
* Explanation:
* We can create "bread" since we have the ingredients "yeast" and "flour".
* We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
*
* Example 3:
* Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
* Output: ["bread","sandwich","burger"]
* Explanation:
* We can create "bread" since we have the ingredients "yeast" and "flour".
* We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
* We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".
*
* Constraints:
* • n == recipes.length == ingredients.length
* • 1 <= n <= 100
* • 1 <= ingredients[i].length, supplies.length <= 100
* • 1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
* • `recipes[i]`, `ingredients[i][j]`, and `supplies[k]` consist only of lowercase English letters.
* • All the values of `recipes` and `supplies` combined are unique.
* • Each `ingredients[i]` does not contain any duplicate values.
* 
****************************************/

import java.util.List;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

class Solution {
    // Uses topological sorting (BFS) to determine which recipes can be made.
    // Builds an adjacency list mapping ingredients to dependent recipes.
    // Tracks the in-degree (remaining required ingredients) for each recipe.
    // Processes available supplies in a queue, reducing in-degree for recipes.
    // When a recipe's in-degree reaches 0, it is added to the queue & result.
    // Time Complexity: O(N + M), where N = recipes, M = total ingredients.
    // Space Complexity: O(N + M) for graph storage and tracking in-degree.
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // Adjacency List: ingredient -> list of recipes that depend on it
        Map<String, List<String>> graph = new HashMap<>();
        // In-degree map: recipe -> number of remaining needed ingredients
        Map<String, Integer> inDegree = new HashMap<>();
        // Set of all supplies for quick lookup
        Set<String> available = new HashSet<>();
        
        // Initialize available supplies
        for (String supply : supplies) {
            available.add(supply);
        }

        // Build graph and in-degree map
        for (int i = 0; i < recipes.length; i++) {
            String recipe = recipes[i];
            inDegree.put(recipe, ingredients.get(i).size()); // Count needed ingredients

            for (String ingredient : ingredients.get(i)) {
                graph.putIfAbsent(ingredient, new ArrayList<>());
                graph.get(ingredient).add(recipe);
            }
        }

        // BFS Queue: Start with all initially available supplies
        Queue<String> queue = new LinkedList<>(available);
        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            String ingredient = queue.poll();

            // If this ingredient is a recipe we can make, add to result
            if (inDegree.containsKey(ingredient)) {
                result.add(ingredient);
            }

            // Process all recipes that depend on this ingredient
            if (graph.containsKey(ingredient)) {
                for (String recipe : graph.get(ingredient)) {
                    inDegree.put(recipe, inDegree.get(recipe) - 1);

                    // If all ingredients are now available, we can make this recipe
                    if (inDegree.get(recipe) == 0) {
                        queue.offer(recipe);
                    }
                }
            }
        }

        return result;
    }
}
