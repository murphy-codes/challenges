// Source: https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21
// At the time of submission:
//   Runtime 35 ms Beats 71.55%
//   Memory 46.62 MB Beats 67.74%

/****************************************
* 
* You have information about `n` different recipes. You are given a string array `recipes` 
* _ and a 2D string array `ingredients`. The `i^th` recipe has the name `recipes[i]`, and 
* _ you can create it if you have all the needed ingredients from `ingredients[i]`. 
* _ A recipe can also be an ingredient for other recipes, i.e., `ingredients[i]` may contain 
* _ a string that is in `recipes`.
* You are also given a string array `supplies` containing all the ingredients that you 
* _ initially have, and you have an infinite supply of all of them.
* Return a list of all the recipes that you can create. 
* _ You may return the answer in any order.
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Set<String> available = new HashSet<>(Arrays.asList(supplies));
        Map<String, List<String>> dependencyGraph = new HashMap<>();
        Map<String, Integer> recipeInDegree = new HashMap<>();

        // Build the graph: ingredient -> recipes that depend on it
        for (int i = 0; i < recipes.length; i++) {
            String recipe = recipes[i];
            for (String ingredient : ingredients.get(i)) {
                if (!available.contains(ingredient)) {
                    dependencyGraph.computeIfAbsent(ingredient, k -> new ArrayList<>()).add(recipe);
                    recipeInDegree.put(recipe, recipeInDegree.getOrDefault(recipe, 0) + 1);
                }
            }
        }

        // Recipes with no unknown ingredients can be made immediately
        Deque<String> queue = new ArrayDeque<>();
        for (String recipe : recipes) {
            if (!recipeInDegree.containsKey(recipe)) {
                queue.offer(recipe);
            }
        }

        List<String> possibleRecipes = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            possibleRecipes.add(current);

            List<String> dependents = dependencyGraph.get(current);
            if (dependents != null) {
                for (String dependent : dependents) {
                    recipeInDegree.merge(dependent, -1, Integer::sum);
                    if (recipeInDegree.get(dependent) == 0) {
                        queue.offer(dependent);
                    }
                }
            }
        }

        return possibleRecipes;
    }
}
