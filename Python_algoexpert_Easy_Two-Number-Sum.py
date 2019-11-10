'''
  Author: Tom Murphy
  Last Modified: 2019-11-09 18:42
'''

# https://www.algoexpert.io/questions/Two%20Number%20Sum

def twoNumberSum(array, targetSum):
    # Write your code here.
	result = []
	for num in array:
		if targetSum-num in array and num != targetSum-num:
			result.append(num)
	result.sort()
	return result