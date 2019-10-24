'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 17:18
'''

# https://edabit.com/challenge/RbiTp6EE8ReczcStp
# Mutations Only: Zeroes to the End
# Write a function that moves all the zeroes to the end of a list. Do this without returning a copy of the input list.

def zeroes_to_end(numbers):
  i = 0
  length = len(numbers)
  while i < length:
    if numbers[i] == 0:
      numbers.append(numbers.pop(i))
      length-=1
    else:
      i+=1
  return numbers

print(zeroes_to_end([1, 2, 0, 0, 4, 0, 5])) # ? [1, 2, 4, 5, 0, 0, 0]
print(zeroes_to_end([0, 0, 2, 0, 5]))       # ? [2, 5, 0, 0, 0]
print(zeroes_to_end([4, 4, 5]))             # ? [4, 4, 5]
print(zeroes_to_end([0, 0]))                # ? [0, 0]