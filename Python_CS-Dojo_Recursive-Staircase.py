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
  result = 0
  stepping = True
  if n == 0:
    result += 1
    stepping = False
  i = 0
  while i < len(x) and stepping:
    if x[i] <= n:
      result += diff_num_ways(n-x[i], x)
      i+=1
    else:
      stepping = False
  return result

print(num_ways(4))
print(diff_num_ways(4, [1,3,5]))