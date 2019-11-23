'''
  Author: Tom Murphy
  Last Modified: 2019-11-22 16:44
'''

# https://www.youtube.com/watch?v=5o-kdjv7FD0
# Amazon Coding Interview Question - Recursive Staircase Problem

def num_ways(n):
  if n == 0 or n == 1: return 1
  return num_ways(n-2) + num_ways(n-1)

def diff_num_ways(n, x):
  if n == 0: return 1
  ways = {0:1}
  ways[0] = 1
  for i in range(1,n+1):
    total = 0
    for j in x:
      if i - j >= 0:
        total += ways[i-j]
    ways[i] = total
  return ways[n]

print(num_ways(4))
print(diff_num_ways(4, [1,3,5]))