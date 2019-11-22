=begin
  Author: Tom Murphy
  Last Modified: 2019-11-22 11:24
=end

# https://edabit.com/challenge/5p9H8zCeXaaoMpZ5j
# Who's The Oldest?
# Given a hash containing the names and ages of a group of people, return the name of the oldest person.
# Notes: All ages will be different.

def oldest(ages)
  oldest = ["",0]
  i = 0
  while i < ages.length
    if ages[ages.keys[i]] > oldest[1]
      oldest[1] = ages[ages.keys[i]]
      oldest[0] = ages.keys[i]
    end
    i+=1
  end
  oldest[0]
end

puts oldest({
  "Emma" => 71,
  "Jack" => 45,
  "Amy" => 15,
  "Ben" => 29
}) # ? "Emma"

puts oldest({
  "Max" => 9,
  "Josh" => 13,
  "Sam" => 48,
  "Anne" => 33
}) # ? "Sam"