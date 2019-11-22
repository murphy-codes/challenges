=begin
  Author: Tom Murphy
  Last Modified: 2019-11-22 11:45
=end

# https://edabit.com/challenge/Bgbqoyz4HADikdQJr
# No Yelling
=begin
  Create a function that transforms sentences ending with multiple question marks ? or exclamation marks ! into a sentence only ending with one.
Notes
  Only change ending punctuation - keep the exclamation marks or question marks in the middle of the sentence the same (see third example).
  Don't worry about mixed punctuation (no cases that end in something like ?!??!).
  Keep sentences that do not have question/exclamation marks the same.
=end

def no_yelling(sentence)
  continue = true
  i = -1
  while continue
    if sentence[i] == '!' || sentence[i] == '?'
      i-=1
      last = sentence[i]
    else
      continue = false
    end
  end
  if i < -1
    sentence = sentence.slice(0..i+1)
  end
  sentence
end

puts no_yelling("What went wrong?????????") # ? "What went wrong?"
puts no_yelling("Oh my goodness!!!") # ? "Oh my goodness!"
puts no_yelling("I just!!! can!!! not!!! believe!!! it!!!") # ? "I just!!! can!!! not!!! believe!!! it!"
# Only change repeating punctuation at the end of the sentence.
puts no_yelling("Oh my goodness!") # ? "Oh my goodness!"
# Do not change sentences where there exists only one or zero exclamation marks/question marks.
puts no_yelling("I just cannot believe it.") # ? "I just cannot believe it."