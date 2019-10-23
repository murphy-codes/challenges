'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 14:51
'''

# https://edabit.com/challenge/WS6hR6b9EZzuDTD26
# Words With Duplicate Letters
# Given a common phrase, return False if any individual word in the phrase contains duplicate letters. Return True otherwise.

def no_duplicate_letters(phrase):
  result = True
  words = phrase.split()
  i = 0
  while result and i < len(words):
    chars = dict()
    for l in words[i]:
      # Use to make result case-sensitive, replacing l with j above
      # l = j.lower()    
      if l in chars:
        chars[l] = chars[l]+1
        result = False
      else:
        chars[l] = 1
    i+=1
  return result

print(no_duplicate_letters("Fortune favours the bold.")) 
# ? True
print(no_duplicate_letters("You can lead a horse to water, but you can't make him drink.")) 
# ? True
print(no_duplicate_letters("Look before you leap.")) 
# ? False
# Duplicate letters in "Look" and "before".
print(no_duplicate_letters("An apple a day keeps the doctor away.")) 
# ? False
# Duplicate letters in "apple", "keeps", "doctor", and "away".