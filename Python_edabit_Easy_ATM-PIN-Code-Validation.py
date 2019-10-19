'''
  Author: Tom Murphy
  Last Modified: 2019-10-18 22:00
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

def validate_PIN_entry():
  # ask for and validate user input
  flag = 'a'
  while type(flag) is not int:
    try:
      user_input = int(input('Please enter a 4-digit or 6-digit PIN: '))
      # prevent neg int inputs
      if user_input < 0:
        print('Negative numbers are not accepted')
      # prevent PINs that are less than 4-digits
      elif user_input >= 0 and user_input < 1000:
        print('PIN contains too few digits')
      # prevent 5-digits PINs
      elif user_input >= 10000 and user_input < 100000:
        print('5-digit PINs are not accepted')
      # prevent PINs that are greater than 6-digits
      elif user_input >= 1000000:
        print('PIN contains too many digits')
      else:
        flag = user_input
    except ValueError:
      # Handle the exception
      print('Only integers are accepted')
  print('PIN has been set to ' + str(user_input))

print()
validate_PIN_entry()
