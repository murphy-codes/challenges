'''
  Author: Tom Murphy
  Last Modified: 2019-10-24 14:26
'''

# https://edabit.com/challenge/Q7foiyejfJG6pqqws
# Word Buckets
# Write a function that divides a phrase into word buckets, with each bucket containing n or fewer characters. Only include full words inside each bucket.
'''
Notes
  Spaces count as one character.
  Trim beginning and end spaces for each word bucket (see final example).
  If buckets are too small to hold a single word, return an empty array: []
'''
def bucketize(sentence, bucket_size):
  word_buckets = []
  location = mod = 0
  if bucket_size == 0:
    #print('Error: Bucket size must be greater than 0.')
    location = len(sentence)
  while location < len(sentence):
    if mod == bucket_size:
      location+=bucket_size
    elif sentence[location] == ' ':
      location+=1
    elif location + bucket_size - mod >= len(sentence):
      while sentence[len(sentence)-mod-1] == ' ':
        mod+=1
      word_buckets.append(sentence[location:len(sentence)-mod])
      location = len(sentence)
    elif sentence[location+bucket_size-mod] == ' ':
      while sentence[location+bucket_size-mod-1] == ' ':
        mod+=1
      word_buckets.append(sentence[location:location+bucket_size-mod])
      location += bucket_size-mod
      location+=1
      mod = 0
    else:
      mod+=1
  
  #return sentence.split()
  return word_buckets

print(bucketize("she sells sea shells by the sea", 10))
# ? ["she sells", "sea shells", "by the sea"]
print(bucketize("the mouse jumped over the cheese", 7))
# ? ["the", "mouse", "jumped", "over", "the", "cheese"]
print(bucketize("fairy dust coated the air", 20))
# ? ["fairy dust coated", "the air"]
print(bucketize("a b c d e", 2))
# ? ["a", "b", "c", "d", "e"]
