from collections import Counter

def solution(str1, str2):
    
    def tokenizer(text):
        tokens = []
        text = text.lower()
        for i in range(len(text) - 1):
            if text[i].isalpha() and text[i+1].isalpha():
                tokens.append(text[i:i+2])
        return tokens
        
    counter1, counter2 = Counter(tokenizer(str1)), Counter(tokenizer(str2))
    
    intersection_len = sum((counter1 & counter2).values())
    union_len = sum((counter1 | counter2).values())
    
    return 65536 if union_len == 0 else int(intersection_len / union_len * 65536)
