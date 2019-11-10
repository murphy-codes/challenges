'''
  Author: Tom Murphy
  Last Modified: 2019-11-09 19:56
'''

# https://edabit.com/challenge/bYDPYX9ajWC6PNGCp
# Track the Robot (Part 2)
'''
This robot roams around a 2D grid. It starts at (0, 0) facing North. After each time it moves, the robot rotates 90 degrees clockwise. Given the amount the robot has moved each time, you have to calculate the robot's final position.

To illustrate, if the robot is given the movements 20, 30, 10, 40 then it will move:

    20 steps North, now at (0, 20)
    30 steps East, now at (30, 20)
    10 steps South. now at (30, 10)
    40 steps West, now at (-10, 10)

...and will end up at coordinates (-10, 10).
'''

def track_robot(*moves):
  d = 0
  position = [0,0]
  for move in moves:
    if d == 0: # 'N'
      position[1] += move
    elif d == 1: # 'E'
      position[0] += move
    elif d == 2: # 'S'
      position[1] -= move
    else: # d == 3: # 'W'
      position[0] -= move
    d+=1
    if d == 4:
      d = 0
  return position

print(track_robot(20, 30, 10, 40)) # ? [-10, 10]
print(track_robot())               # ? [0, 0]
# No movement means the robot stays at (0, 0).
print(track_robot(-10, 20, 10))    # ? [20, -20]
# The amount to move can be negative.