'''
  Author: Thomas Murphy
  Last Modified: 2019-10-18 19:45
'''


'''
# https://edabit.com/challenge/rZToTkR5eB9Zn4zLh
# Return the Sum of Two Numbers
# Create a function that takes two numbers as arguments and return their sum.
def addition(x, y):
  return x+y

print(addition(3, 2))   # ? 5
print(addition(-3, -6)) # ? -9
print(addition(7, 3))   # ? 10
'''

'''
# https://edabit.com/challenge/FQyaaJx7orS7tiwz8
# Minutes to Seconds
# Write a function that takes an integer minutes and converts it to seconds.
def convert(minutes):
  return minutes*60

print(convert(5)) # ? 300
print(convert(3)) # ? 180
print(convert(2)) # ? 120
'''

'''
# https://edabit.com/challenge/KjCS7occ9hfu5snpb
# Return the Next Number from the Integer Passed
# Create a function that takes a number as an argument, increments the number by +1 and returns the result.
def addition(x):
  return x+1

print(addition(0))  # ? 1
print(addition(9))  # ? 10
print(addition(-3)) # ? -2
'''

'''
# https://edabit.com/challenge/aWLTzrRsrw7RakYrN
# Area of a Triangle
# Write a function that takes the base and height of a triangle and return its area.
def tri_area(x, y):
  area = x*y/2
  if area.is_integer():
    return int(area)
  else:
    return area

print(tri_area(3, 2))   # ? 3
print(tri_area(7, 4))   # ? 14
print(tri_area(10, 10)) # ? 50
print(tri_area(7, 11))  # ? 38.5
'''

'''
# https://edabit.com/challenge/KWoj7kWiHRqJtG6S2
# Return the Remainder from Two Numbers
# There is a single operator in Python, capable of providing the remainder of a division operation. Two numbers are passed as parameters. The first parameter divided by the second parameter will have a remainder, possibly zero. Return that value.
def remainder(x, y):
  return x%y

print(remainder(1, 3)) # ? 1
print(remainder(3, 4)) # ? 3
print(remainder(5, 5)) # ? 0
print(remainder(7, 2)) # ? 1
'''

'''
# https://edabit.com/challenge/nyeNvKWdDFKRAk4Da
# Seconds in Hours
# Write a function that converts hours into seconds.
def how_many_seconds(hours):
  return hours*3600

print(how_many_seconds(2))  # ? 7200
print(how_many_seconds(10)) # ? 36000
print(how_many_seconds(24)) # ? 86400
'''

'''
# https://edabit.com/challenge/Zerwo2AENbvRZTe83
# Maximum Edge of a Triangle
# Create a function that finds the maximum range of a triangles third edge.
def next_edge(x, y):
  return x+y-1

print(next_edge(8, 10)) # ? 17
print(next_edge(5, 7))  # ? 11
print(next_edge(9, 2))  # ? 10
'''

'''
# https://edabit.com/challenge/hEQ3rBrKrztQK8qAd
# Return the First Element in a List
# Create a function that takes a list and returns the first element.
def get_first_value(user_list):
  return user_list[0]

print(get_first_value([1, 2, 3]))     # ? 1
print(get_first_value([80, 5, 100]))  # ? 80
print(get_first_value([-500, 0, 50])) # ? -500
'''

'''
# https://edabit.com/challenge/xWSjvoH7mEkSnqS7H
# To the Power of _____
# Create a function that takes a base number and an exponent number and returns the calculation.
def calculate_exponent(x, y):
  return x**y

print(calculate_exponent(5, 5))   # ? 3125
print(calculate_exponent(10, 10)) # ? 10000000000
print(calculate_exponent(3, 3))   # ? 27
'''

'''
# https://edabit.com/challenge/QzXtDnSZL6y4ZcEvT
# The Farm Problem
# You've got chickens (2 legs), cows (4 legs) and pigs (4 legs) on your farm. Return the total number of legs on your farm.
def animals(chicken, cows, pigs):
  return 4*(cows+pigs) + 2*chicken

print(animals(2, 3, 5)) # ? 36
print(animals(1, 2, 3)) # ? 22
print(animals(5, 2, 8)) # ? 50
'''


'''
# https://edabit.com/challenge/WKJwo2xDNjKxwtGoH
# String to Integer and Vice Versa
# Write two functions:
# 1. to_int() : A function to convert a string to an integer.
# 2. to_str() : A function to convert an integer to a string.
  # Well... we could just use str() and int()... but alrighty..

import math
#as per SO q2189800
def num_digits(n):
  if n > 0:
    return int(math.log10(n))+1
  elif n == 0:
    return 1
  else:
    return int(math.log10(-n))+2 # +1 if you don't count the '-'

#as per SO q39644638
def get_digit(number, n):
  return number // 10**n % 10

def to_int(str_in):
  #inty = int(str_in)
  #as per SO q46406072
  inty = 0
  for digit in str_in:
      inty *= 10
      for d in '0123456789':
        inty += digit > d
  if str_in[0] == '-':
    inty = -1*inty
  return inty

def to_str(int_in):
  #stringy = str(int_in)
  neg = False
  if int_in < 0:
    neg = True
    int_in = -1*int_in
  digits = num_digits(int_in)
  stringy = ""
  idjits = {0:48, 1:49, 2:50, 3:51, 4:52, 5:53, 6:54, 7:55, 8:56, 9:57}
  i = digits-1
  while i >= 0:
    stringy = stringy +chr(idjits[get_digit(int_in, i)])
    i-=1
  if neg:
    stringy = '-' + stringy
  return stringy

print(type(to_int("77")), to_int("77"))     # ? 77
print(type(to_int("532")), to_int("532"))   # ? 532
print(type(to_int("-426")), to_int("-426")) # ? -426
print(type(to_str(77)), to_str(77))       # ? "77"
print(type(to_str(532)), to_str(532))     # ? "532"
print(type(to_str(-426)), to_str(-426))   # ? "-426"
'''

'''
# https://edabit.com/challenge/Rx2pkSA9dCmtwS8xt
# Is the Number Less than or Equal to Zero?
# Create a function that takes a number as its only argument and returns True if it's less than or equal to zero, otherwise return False.
def less_than_or_equal_to_zero(value):
  if value <= 0:
    return True
  else:
    return False

print(less_than_or_equal_to_zero(5))  # ? False
print(less_than_or_equal_to_zero(0))  # ? True
print(less_than_or_equal_to_zero(-2)) # ? True
'''

'''
# https://edabit.com/challenge/PjcKZRx8YE5KzRN63
# Hours and Minutes to Seconds
# Write a function that takes two integers (hours, minutes) and converts them to seconds.
def convert(h, m):
  return h*3600 + m*60

print(convert(1, 3)) # ? 3780
print(convert(2, 0)) # ? 7200
print(convert(0, 0)) # ? 0
'''

'''
# https://edabit.com/challenge/P9cJCAqjveWkJ4pSn
# Are the Numbers Equal?
# Create a function that takes two integers and checks if they are equal.
def is_equal(x, y):
  if x == y:
    return True
  else:
    return False

print(is_equal(5, 6))   # ? False
print(is_equal(1, 1))   # ? True
print(is_equal("1", 1)) # ? False
'''

'''
# https://edabit.com/challenge/49pyDP8dE3pJ2dYMW
# Check if an Integer is Divisible By Five
# Create a function that returns True if an integer is divisible by 5, and false otherwise.
def divisible_by_five(n):
  if n % 5 == 0:
    return True
  else:
    return False

print(divisible_by_five(5))  # ? True
print(divisible_by_five(-55)) # ? True
print(divisible_by_five(37))  # ? False
'''

'''
# https://edabit.com/challenge/A7hyDnb72prWryeuY
# Find the Largest Number in a List
# Create a function that takes a list of numbers. Return the largest number in the list.
def findLargestNum(user_list):
  largest = 0
  for n in user_list:
    if n > largest:
      largest = n
  return largest

print(findLargestNum([4, 5, 1, 3]))         # ? 5
print(findLargestNum([300, 200, 600, 150])) # ? 600
print(findLargestNum([1000, 1001, 857, 1])) # ? 1001
'''

'''
# https://edabit.com/challenge/pKyeEDkNqZraqS3rW
# Testing K^K == N?
# Write a function that returns True if k^k == n for input (n, k)
def k_to_k(n, k):
  if k**k == n:
    return True
  else:
    return False

print(k_to_k(4, 2))         # ? True
print(k_to_k(387420489, 9)) # ? True
print(k_to_k(3124, 5))      # ? False
print(k_to_k(17, 3))        # ? False
'''

'''
# https://edabit.com/challenge/XsJLwhAddzbxdQqr4
# Difference of Max and Min Numbers in List
# Create a function that takes a list and returns the difference between the smallest and biggest numbers.
def difference_max_min(user_list):
  i = 0
  min_num = max_num = user_list[i]
  for n in user_list:
    if min_num > user_list[i]:
      min_num = user_list[i]
    elif max_num < user_list[i]:
      max_num = user_list[i]
    i+=1
  return max_num - min_num

print(difference_max_min([10, 4, 1, 4, -10, -50, 32, 21])) # ? 82
# Smallest number is -50, biggest is 32.
print(difference_max_min([44, 32, 86, 19])) # ? 67
# Smallest number is 19, biggest is 86.
'''

'''
# https://edabit.com/challenge/ecSZ5kDBwCD3ctjE6
# Find the Smallest Number in a List
# Create a function that takes a list of numbers and returns the smallest number in the list.
def find_smallest_num(user_list):
  smallest = user_list[0]
  for n in user_list:
    if n < smallest:
      smallest = n
  return smallest

print(find_smallest_num([34, 15, 88, 2])) # ? 2
print(find_smallest_num([34, -345, -1, 100])) # ? -345
print(find_smallest_num([-76, 1.345, 1, 0])) # ? -76
print(find_smallest_num([7, 7, 7])) # ? 7
print(find_smallest_num([0.4356, 0.8795, 0.5435, -0.9999])) # ? -0.9999
'''

'''
# https://edabit.com/challenge/SNM5EZ3FePECt2HQn
# Profitable Gamble
# Create a function that takes in three arguments (prob, prize, pay) and returns true if prob * prize > pay; otherwise return false.
# To illustrate, profitable_gamble(0.2, 50, 9) should yield true, since the net profit is 1 (0.2 * 50 - 9), and 1 > 0.
def profitable_gamble(prob, prize, pay):
  if prob * prize > pay:
    return True
  else:
    return False

print(profitable_gamble(0.2, 50, 9)) # ? True
print(profitable_gamble(0.9, 1, 2))  # ? False
print(profitable_gamble(0.9, 3, 2))  # ? True
'''

'''
# https://edabit.com/challenge/NebFhjXTn8NEbhYXY
# Multiple of 100
# Create a function that takes an integer and returns True if it's divisible by 100, otherwise return False.
def divisible(n):
  if n % 100 == 0:
    return True
  else:
    return False

print(divisible(1))    # ? False
print(divisible(1000)) # ? True
print(divisible(100))  # ? True
'''

'''
# https://edabit.com/challenge/6fx8iNCHETW8KqAui
# Maximum Difference
# Given a list of integers, return the difference between the largest and smallest integers in the list.
def difference(user_list):
  i = 0
  min_num = max_num = user_list[i]
  for n in user_list:
    if min_num > user_list[i]:
      min_num = user_list[i]
    elif max_num < user_list[i]:
      max_num = user_list[i]
    i+=1
  return max_num - min_num

print(difference([10, 15, 20, 2, 10, 6]))  # ? 18 # 20 - 2 = 18
print(difference([-3, 4, -9, -1, -2, 15])) # ? 24 # 15 - (-9) = 24
'''

'''
# https://edabit.com/challenge/coRuMC4Ykksti8Z47
# Name Greeting!
# Create a function that takes a name and returns a greeting.
def hello_name(name):
  return "Hello " + name + "!"

print(hello_name("Gerald"))  # ? "Hello Gerald!"
print(hello_name("Tiffany")) # ? "Hello Tiffany!"
print(hello_name("Ed"))      # ? "Hello Ed!"
'''

'''
# https://edabit.com/challenge/cCWMeiJCP9Ef8XMq8
# Concatenating Two Integer Lists
# Create a function to concatenate two integer lists.
def concat(list_a, list_b):
  return list_a + list_b

print(concat([1, 3, 5], [2, 6, 8]))       # ? [1, 3, 5, 2, 6, 8]
print(concat([7, 8], [10, 9, 1, 1, 2]))   # ? [7, 8, 10, 9, 1, 1, 2]
print(concat([4, 5, 1], [3, 3, 3, 3, 3])) # ? [4, 5, 1, 3, 3, 3, 3, 3]
'''

'''
# https://edabit.com/challenge/C3N2JEfFQoh4cqQ98
# Compare Strings by Count of Characters
# Create a function that takes two strings as arguments and return either True or False depending on whether the total number of characters in the first string is equal to the total number of characters in the second string.
def comp(str_a, str_b):
  if len(str_a) == len(str_b):
    return True
  else:
    return False

print(comp("AB", "CD"))        # ? True
print(comp("ABC", "DE"))       # ? False
print(comp("hello", "edabit")) # ? False
'''