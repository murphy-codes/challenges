'''
  Author: Tom Murphy
  Last Modified: 2019-10-18 21:49
'''

# https://edabit.com/challenge/K4Pqh67Y9gpixPfjo
# ATM PIN Code Validation
# ATM machines allow 4 or 6 digit PIN codes and PIN codes cannot contain anything but exactly 4 digits or exactly 6 digits. Your task is to create a function that takes a string and returns True if the PIN is valid and False if it's not.

import re

def is_valid_PIN(num):
  #x = re.search("^[0-9]+$", num)
  x = re.fullmatch("^[0-9]+$", num)
  if (len(num) == 4 or len(num) == 6) and x:
    return True
  else:
    return False

print(is_valid_PIN("1234"))  # ? True
print(is_valid_PIN("12345")) # ? False
print(is_valid_PIN("a234"))  # ? False
print(is_valid_PIN(""))      # ? False

user_input = input('Please enter a 4-digit or 6-digit PIN: ')
print(is_valid_PIN(user_input))	