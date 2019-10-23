'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 15:19
'''

# https://edabit.com/challenge/D6XfxhRobdQvbKX4v
# First Before Second Letter
# You are given three inputs: a string, one letter, and a second letter. Write a function that returns True if every instance of the first letter occurs before every instance of the second letter.

def first_before_second(phrase, lttr1, lttr2):
  result = True
  lttr1_i = phrase.rfind(lttr1)
  lttr2_i = phrase.find(lttr2)
  if lttr1_i >= lttr2_i:
    result = False
  # Decide how to handle characters not found
  if lttr2_i == -1:
    result = True
  return result

print(first_before_second("a rabbit jumps joyfully", "a", "j")) # ? True
# Every instance of "a" occurs before every instance of "j".
print(first_before_second("knaves knew about waterfalls", "k", "w")) # ?  True
print(first_before_second("happy birthday", "a", "y")) # ? False
# The "a" in "birthday" occurs after the "y" in "happy".
print(first_before_second("precarious kangaroos", "k", "a")) # ? False