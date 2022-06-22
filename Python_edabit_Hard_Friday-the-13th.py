'''
  Author: Tom Murphy
  Last Modified: 2022-06-22 14:29
'''

# https://edabit.com/challenge/Xkc2iAjwCap2z9N5D
# Friday the 13th
# Given the month and year as numbers, return whether that month contains a Friday 13th.

'''
Examples
  has_friday_13(3, 2020) ➞ True
  has_friday_13(10, 2017) ➞ True
  has_friday_13(1, 1985) ➞ False
'''

# Notes
# • January will be given as 1, February as 2, etc ...
# • Check Resources for some helpful tutorials on Python's datetime module.

import datetime

def has_friday_13(m, y):
  dt = datetime.datetime(y, m, 13)
  return dt.strftime('%A') == "Friday"

print("has_friday_13(3, 2020) ➞ " + str(has_friday_13(3, 2020)))   # True
print("has_friday_13(10, 2017) ➞ " + str(has_friday_13(10, 2017))) # True
print("has_friday_13(1, 1985) ➞ " + str(has_friday_13(1, 1985)))   # False