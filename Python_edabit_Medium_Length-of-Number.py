'''
  Author: Tom Murphy
  Last Modified: 2022-06-22 14:14
'''

# https://edabit.com/challenge/2zKetgAJp4WRFXiDT
# Length of Number
# Create a function that takes a number num and returns its length.

'''
Examples
  number_length(10) ➞ 2

  number_length(5000) ➞ 4

  number_length(0) ➞ 1
'''

# Notes
# The use of the len() function is prohibited.

def number_length(n):
  if type(n) != int:
    print("n is not a number")
    return 0
  i = 0
  for c in str(n):
    i+=1
  return i

print("number_length(10) → " + str(number_length(10)))
print("number_length(5000) → " + str(number_length(5000)))
print("number_length(0) → " + str(number_length(0)))