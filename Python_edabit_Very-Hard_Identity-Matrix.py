'''
  Author: Tom Murphy
  Last Modified: 2022-06-22 15:31


# https://edabit.com/challenge/QN4RMpAnktNvMCWwg
# Identity Matrix

An identity matrix is defined as a square matrix with 1s running from the top left of the square to the bottom right. The rest are 0s. The identity matrix has applications ranging from machine learning to the general theory of relativity.

Create a function that takes an integer n and returns the identity matrix of n x n dimensions. For this challenge, if the integer is negative, return the mirror image of the identity matrix of n x n dimensions.. It does not matter if the mirror image is left-right or

Examples
id_mtrx(2) ➞ [
  [1, 0],
  [0, 1]
]

id_mtrx(-2) ➞ [
  [0, 1],
  [1, 0]
]

id_mtrx(0) ➞ []

Notes
Incompatible types passed as n should return the string "Error".
'''

def id_mtrx(n):
  if type(n) != int:
    return "Error"
  i, j = 0, 0
  total = abs(n)
  neg = n < 0
  matrix = []
  while i < total:
    row = []
    while j < total:
      if j == i:
        if neg:
          row.insert(0, 1)
        else: 
          row.append(1)
      else:
        if neg:
          row.insert(0, 0)
        else: 
          row.append(0)
      j+=1
    matrix.append(row)
    j = 0
    i+=1
  return matrix

def print_mtrx(matrix):
  matrix_str = "["
  for row in matrix:
    matrix_str += "\n  " + str(row)
  if len(matrix) > 0:
    matrix_str += "\n"
  matrix_str += "]"
  return matrix_str

print("id_mtrx(2) ➞ " + print_mtrx(id_mtrx(2)))
print("\nid_mtrx(-2) ➞ " + print_mtrx(id_mtrx(-2)))
print("\nid_mtrx(0) ➞ " + print_mtrx(id_mtrx(0)))

print("\n\nid_mtrx(3) ➞ " + print_mtrx(id_mtrx(3)))
print("\nid_mtrx(-3) ➞ " + print_mtrx(id_mtrx(-3)))
print("\nid_mtrx(4) ➞ " + print_mtrx(id_mtrx(4)))
print("\nid_mtrx(-4) ➞ " + print_mtrx(id_mtrx(-4)))