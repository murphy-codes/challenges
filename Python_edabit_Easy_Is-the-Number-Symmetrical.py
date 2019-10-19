'''
  Author: Tom Murphy
  Last Modified: 2019-10-18 20:50
'''

# https://edabit.com/challenge/zqMREZ2MQd9M5jNfM
# Is the Number Symmetrical?
# Create a function that takes a number as an argument and returns True or False depending on whether the number is symmetrical or not. A number is symmetrical when it is the same as its reverse.

def is_symmetrical(user_inp):
  target = str(user_inp)
  result = True
  i = 0
  j = len(target)-1
  while i <= j:
    if target[i] != target[j]:
      result = False
    i+=1
    j-=1
  return result

#print(is_symmetrical('bob'))
print(is_symmetrical(7227))     # ? True
print(is_symmetrical(12567))    # ? False
print(is_symmetrical(44444444)) # ? True
print(is_symmetrical(9939))     # ? False
print(is_symmetrical(1112111))  # ? True