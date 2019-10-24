'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 16:57
'''

# https://edabit.com/challenge/HrQoXJYqpYZ2Rqvtb
# Product of Digits of Sum
# Create a function that takes numbers as arguments, adds them together, and returns the product of digits until the answer is only 1 digit long.

def explode(number):
  values = str(number)
  nums = []
  for i in values:
    nums.append(int(i))
  return nums

def sum_dig_prod(*argv):
  # If no arguements are provided, output an error message and return None
  if len(argv) == 0:
    print('Error: Please supply at least 1 integer')
    return None
  value = 0
  # Sum up values
  for arg in argv:
    value += arg
  # Pi sum values (multiplication) until values is 1 digit long
  while len(str(value)) > 1:
    vals = explode(value)
    value = 1
    for i in vals:
      value *= i
  return value

#print(sum_dig_prod())                 # ? None

print(sum_dig_prod(16, 28))           # ? 6
print(sum_dig_prod(0))                # ? 0
print(sum_dig_prod(1, 2, 3, 4, 5, 6)) # ? 2