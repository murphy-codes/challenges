'''
  Author: Tom Murphy
  Last Modified: 2019-10-23 21:33
'''

# https://edabit.com/challenge/FSRLWWcvPRRdnuDpv
# Jake's Meal Time
# Jake is a very habitual person. He eats breakfast at 7:00 a.m. each morning, lunch at 12:00 p.m. and dinner at 7:00 p.m. in the evening. Create a function that takes in the current time as a string and determines the duration of time before Jake's next meal. Represent this as a list with the first and second elements representing hours and minutes, respectively.

def time_to_eat(current_time):
  # Pull hours, minutes, & meridiem from string arg
  hour = int(current_time.split(':')[0])
  min = int(current_time.split(':')[1].split()[0])
  # Account for meridiem (AM vs PM)
  if current_time.split(':')[1].split()[1] == 'p.m.':
    hour+=12
  # Calculate remaning minutes until next meal
  current_min = 60*hour + min
  meal_times = [420,720,1140]
  if current_min <= meal_times[0]:
    remaining_min = meal_times[0] - current_min
  elif current_min <= meal_times[1]:
    remaining_min = meal_times[1] - current_min
  elif current_min <= meal_times[2]:
    remaining_min = meal_times[2] - current_min
  else: #if current_min > meal_times[2] #(dinner)
    remaining_min = meal_times[0] + 1440 - current_min
  # Turn minutes back into H+M, & return
  r_min = remaining_min % 60
  r_hour = int(remaining_min - r_min)//60
  time_until_meal = [r_hour, r_min]
  return time_until_meal

print(time_to_eat("2:00 p.m.")) # ? [5, 0]
# 5 hours until the next meal, dinner
print(time_to_eat("5:50 a.m.")) # ? [1, 10]
# 1 hour and 10 minutes until the next meal, breakfast
print(time_to_eat("8:15 p.m.")) # ? [10, 45]
# 10 hours and 45 minutes until the next meal, breakfast