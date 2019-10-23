'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 14:35
'''

# https://edabit.com/challenge/YfoKQWNeYETb9PYpw
# Calculate the Profit
# You work for a manufacturer, and have been asked to calculate the total profit made on the sales of a product. You are given a dictionary containing the cost price per unit (in dollars), sell price per unit (in dollars), and the starting inventory. Return the total profit made, rounded to the nearest dollar. Assume all of the inventory has been sold.

def profit(values):
  total_profit = int(values['inventory'] * (values['sell_price'] - values['cost_price']))
  return total_profit

print(profit({
  "cost_price": 32.67,
  "sell_price": 45.00,
  "inventory": 1200
})) # ? 14796

print(profit({
  "cost_price": 225.89,
  "sell_price": 550.00,
  "inventory": 100
})) # ? 32411

print(profit({
  "cost_price": 2.77,
  "sell_price": 7.95,
  "inventory": 8500
})) # ? 44030