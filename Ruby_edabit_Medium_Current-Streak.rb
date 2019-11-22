=begin
  Author: Tom Murphy
  Last Modified: 2019-11-21 17:28
=end

# https://edabit.com/challenge/xmyZzaKiouqqzsCZz
# Current Streak
=begin
  Create a function that takes the current date (e.g. "2019-09-30"), an array of date hashes and returns the "current streak" (i.e. number of consecutive days in a row).
Notes:
  The array of dates is sorted chronologically.
  The today parameter will always be greater than or equal to the last date in the array.
  An empty array should return 0.
=end

require 'date'

def current_streak(current, dates)
  streak = 0
  i = -1
  while i >= -(dates.length)
    #puts dates[i]
    #puts dates[i].["date"]
    #x = dates[i]
    #puts x["date"]
    #puts dates[i]["date"]
    if dates[i]["date"] == current
      streak+=1
      current = (Date.parse(current)-1).to_s
    else
      i = -(dates.length)
    end
    i-=1
  end
  streak
end

puts current_streak("2019-09-23", [
  {
    "date" => "2019-09-18"
  },
  {
    "date" => "2019-09-19"
  },
  {
    "date" => "2019-09-21"
  },
  {
    "date" => "2019-09-22"
  },
  {
    "date" => "2019-09-23"
  }
]) # ? 3

puts current_streak("2019-09-25", [
  {
    "date" => "2019-09-16"
  },
  {
    "date" => "2019-09-17"
  },
  {
    "date" => "2019-09-21"
  },
  {
    "date" => "2019-09-22"
  },
  {
    "date" => "2019-09-23"
  }
]) # ? 0