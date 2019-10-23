'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 15:54
'''

# https://edabit.com/challenge/yPsS82tug9a8CoLaP
# Unique Character Mapping
# Write a function that returns a character mapping from a word.

def character_mapping(phrase):
  char_map = []
  mapping = dict()
  i = 0
  for l in phrase:
    if l not in mapping:
      mapping[l] = i
      i+=1
    char_map.append(mapping[l])
  return char_map

print(character_mapping("abcd"))   # ? [0, 1, 2, 3]
print(character_mapping("abb"))    # ? [0, 1, 1]
print(character_mapping("babbcb")) # ? [0, 1, 0, 0, 2, 0]
print(character_mapping("hmmmmm")) # ? [0, 1, 1, 1, 1, 1]