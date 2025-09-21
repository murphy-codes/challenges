// Source: https://leetcode.com/problems/design-movie-rental-system/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-21
// At the time of submission:
//   Runtime 536 ms Beats 94.37%
//   Memory 183.30 MB Beats 59.15%

/****************************************
* 
* You have a movie renting company consisting of `n` shops. You want to implement
* _ a renting system that supports searching for, booking, and returning movies.
* _ The system should also support generating a report of the currently
* _ rented movies.
* Each movie is given as a 2D integer array entries where
* _ `entries[i] = [shop_i, movie_i, price_i]` indicates that there is a copy of
* _ movie `movie_i` at shop `shop_i` with a rental price of `price_i`. Each shop
* _ carries at most one copy of a movie `movie_i`.
* The system should support the following functions:
* • Search: Finds the cheapest 5 shops that have an unrented copy of a given movie.
* __ The shops should be sorted by price in ascending order, and in case of a tie,
* __ the one with the smaller `shop_i` should appear first. If there are less than 5
* __ matching shops, then all of them should be returned. If no shop has an unrented
* __ copy, then an empty list should be returned.
* • Rent: Rents an unrented copy of a given movie from a given shop.
* • Drop: Drops off a previously rented copy of a given movie at a given shop.
* • Report: Returns the cheapest 5 rented movies (possibly of the same movie ID) as
* __ a 2D list `res` where `res[j] = [shop_j, movie_j]` describes that the `j^th`
* __ cheapest rented movie `movie_j` was rented from the shop `shop_j`. The movies
* __ in `res` should be sorted by price in ascending order, and in case of a tie,
* __ the one with the smaller `shop_j` should appear first, and if there is still
* __ tie, the one with the smaller `movie_j` should appear first. If there are fewer
* __ than 5 rented movies, then all of them should be returned. If no movies are
* __ currently being rented, then an empty list should be returned.
* Implement the `MovieRentingSystem` class:
* • `MovieRentingSystem(int n, int[][] entries)` Initializes the `MovieRentingSystem`
* __ object with `n` shops and the movies in `entries`.
* • `List<Integer> search(int movie)` Returns a list of shops that have an unrented
* __ copy of the given `movie` as described above.
* • `void rent(int shop, int movie)` Rents the given `movie` from the given `shop`.
* • `void drop(int shop, int movie)` Drops off a previously rented
* __ `movie` at the given `shop`.
* • `List<List<Integer>> report()` Returns a list of cheapest rented
* __ movies as described above.
* Note: The test cases will be generated such that `rent` will only be called if
* _ the shop has an unrented copy of the movie, and `drop` will only be called if
* _ the shop had previously rented out the movie.
* Example 1:
* Input
* ["MovieRentingSystem", "search", "rent", "rent", "report", "drop", "search"]
* [[3, [[0, 1, 5], [0, 2, 6], [0, 3, 7], [1, 1, 4], [1, 2, 7], [2, 1, 5]]], [1], [0, 1], [1, 2], [], [1, 2], [2]]
* Output
* [null, [1, 0, 2], null, null, [[0, 1], [1, 2]], null, [0, 1]]
* Explanation
* MovieRentingSystem movieRentingSystem = new MovieRentingSystem(3, [[0, 1, 5], [0, 2, 6], [0, 3, 7], [1, 1, 4], [1, 2, 7], [2, 1, 5]]);
* movieRentingSystem.search(1);  // return [1, 0, 2], Movies of ID 1 are unrented at shops 1, 0, and 2. Shop 1 is cheapest; shop 0 and 2 are the same price, so order by shop number.
* movieRentingSystem.rent(0, 1); // Rent movie 1 from shop 0. Unrented movies at shop 0 are now [2,3].
* movieRentingSystem.rent(1, 2); // Rent movie 2 from shop 1. Unrented movies at shop 1 are now [1].
* movieRentingSystem.report();   // return [[0, 1], [1, 2]]. Movie 1 from shop 0 is cheapest, followed by movie 2 from shop 1.
* movieRentingSystem.drop(1, 2); // Drop off movie 2 at shop 1. Unrented movies at shop 1 are now [1,2].
* movieRentingSystem.search(2);  // return [0, 1]. Movies of ID 2 are unrented at shops 0 and 1. Shop 0 is cheapest, followed by shop 1.
* Constraints:
* • `1 <= n <= 3 * 10^5`
* • `1 <= entries.length <= 10^5`
* • `0 <= shop_i < n`
* • `1 <= movie_i, price_i <= 10^4`
* • Each shop carries at most one copy of a movie `movie_i`.
* • At most `10^5` calls in total will be made to `search`, `rent`, `drop`, and `report`.
* 
****************************************/

class MovieRentingSystem {
    // This solution uses TreeSets with a custom comparator to maintain sorted
    // order by price, shopId, then movieId. Available copies are grouped by
    // movie, while rented copies are tracked globally. A hash map provides
    // O(1) access to any (shop,movie) pair. Each operation runs in O(log N)
    // due to TreeSet inserts/removals, and queries fetch at most 5 results.

    // Represents one copy of a movie in a shop with a fixed price
    private static class MovieCopy {
        final int shopId;
        final int movieId;
        final int price;
        MovieCopy(int shopId, int movieId, int price) {
            this.shopId = shopId;
            this.movieId = movieId;
            this.price = price;
        }
    }

    // Comparator: order by price ↑, then shop ↑, then movie ↑
    private static final Comparator<MovieCopy> MOVIE_ORDER =
        (a, b) -> {
            int cmp = Integer.compare(a.price, b.price);
            if (cmp != 0) return cmp;
            cmp = Integer.compare(a.shopId, b.shopId);
            if (cmp != 0) return cmp;
            return Integer.compare(a.movieId, b.movieId);
        };

    // Map movieId -> available copies (sorted)
    private final Map<Integer, TreeSet<MovieCopy>> availableCopies = new HashMap<>();
    // All rented copies (globally sorted)
    private final TreeSet<MovieCopy> rentedCopies = new TreeSet<>(MOVIE_ORDER);
    // Fast lookup for (shopId, movieId) -> MovieCopy
    private final Map<Long, MovieCopy> copyLookup = new HashMap<>();

    // Pack (shopId, movieId) into a single long key for quick lookup
    private static long encodeKey(int shopId, int movieId) {
        return (((long) shopId) << 32) ^ (movieId & 0xffffffffL);
    }

    public MovieRentingSystem(int n, int[][] entries) {
        for (int[] e : entries) {
            int shopId = e[0], movieId = e[1], price = e[2];
            MovieCopy copy = new MovieCopy(shopId, movieId, price);
            copyLookup.put(encodeKey(shopId, movieId), copy);
            availableCopies
                .computeIfAbsent(movieId, k -> new TreeSet<>(MOVIE_ORDER))
                .add(copy);
        }
    }

    // Return up to 5 shops offering this movie (cheapest first)
    public List<Integer> search(int movieId) {
        List<Integer> result = new ArrayList<>(5);
        TreeSet<MovieCopy> copies = availableCopies.get(movieId);
        if (copies == null || copies.isEmpty()) return result;
        Iterator<MovieCopy> it = copies.iterator();
        for (int i = 0; i < 5 && it.hasNext(); i++) {
            result.add(it.next().shopId);
        }
        return result;
    }

    // Rent a movie: move from availableCopies -> rentedCopies
    public void rent(int shopId, int movieId) {
        long key = encodeKey(shopId, movieId);
        MovieCopy copy = copyLookup.get(key);
        if (copy == null) return; // defensive check
        TreeSet<MovieCopy> copies = availableCopies.get(movieId);
        if (copies != null) copies.remove(copy);
        rentedCopies.add(copy);
    }

    // Drop a rented movie: move from rentedCopies -> availableCopies
    public void drop(int shopId, int movieId) {
        long key = encodeKey(shopId, movieId);
        MovieCopy copy = copyLookup.get(key);
        if (copy == null) return; // defensive check
        rentedCopies.remove(copy);
        availableCopies
            .computeIfAbsent(movieId, x -> new TreeSet<>(MOVIE_ORDER))
            .add(copy);
    }

    // Return up to 5 rented movies [shopId, movieId] (cheapest first)
    public List<List<Integer>> report() {
        List<List<Integer>> result = new ArrayList<>(5);
        Iterator<MovieCopy> it = rentedCopies.iterator();
        for (int i = 0; i < 5 && it.hasNext(); i++) {
            MovieCopy copy = it.next();
            result.add(Arrays.asList(copy.shopId, copy.movieId));
        }
        return result;
    }
}

/**
 * Your MovieRentingSystem object will be instantiated and called as such:
 * MovieRentingSystem obj = new MovieRentingSystem(n, entries);
 * List<Integer> param_1 = obj.search(movie);
 * obj.rent(shop,movie);
 * obj.drop(shop,movie);
 * List<List<Integer>> param_4 = obj.report();
 */