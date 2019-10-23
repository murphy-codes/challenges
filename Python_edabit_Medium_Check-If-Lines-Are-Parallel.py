'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 14:45
'''

# https://edabit.com/challenge/8rEEHcmq8rRaTksd7
# Check If Lines Are Parallel
# Given two lines, determine whether or not they are parallel. Lines are represented by a list [a, b, c], which corresponds to the line ax+by=c.

def are_lines_parallel(line1, line2):
  if line1[0] == line2[0] and line1[1] == line2[1]:
    return True
  else:
    return False

print(are_lines_parallel([1, 2, 3], [1, 2, 4])) # ? true
# x+2y=3 and x+2y=4 are parallel.
print(are_lines_parallel([2, 4, 1], [4, 2, 1])) # ? false
# 2x+4y=1 and 4x+2y=1 are not parallel.
print(are_lines_parallel([0, 1, 5], [0, 1, 5])) # ? true
# Lines are parallel to themselves.

'''
def are_lines_parallel(line1, line2):
  parallel = False
  if line1[0] == line2[0] and line1[1] == line2[1]:
    parallel = True
  return parallel
'''