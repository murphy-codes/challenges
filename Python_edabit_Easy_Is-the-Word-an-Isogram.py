'''
  Author: Tom Murphy
  Last Modified: 2019-10-18 21:00
'''

# https://edabit.com/challenge/vTGXrd5ntYRk3t6Ma
# Is the Word an Isogram?
# An isogram is a word that has no repeating letters, consecutive or nonconsecutive. Create a function that takes a string and returns either True or False depending on whether or not it's an "isogram".

def is_isogram(word):
  chars = dict()
  result = True
  for i in word:
    l = i.lower()
    if l in chars:
      chars[l] = chars[l]+1
      result = False
    else:
      chars[l] = 1
  return result

print(is_isogram("Algorism")) # ? True
print(is_isogram("PasSword")) # ? False
print(is_isogram("Consecutive")) # ? False