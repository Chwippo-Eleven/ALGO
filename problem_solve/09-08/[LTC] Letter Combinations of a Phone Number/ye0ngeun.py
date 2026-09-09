class Solution:
    def letterCombinations(self, digits: str) -> List[str]:
        digit_letter = {
            '2': 'abc',
            '3': 'def',
            '4': 'ghi',
            '5': 'jkl',
            '6': 'mno',
            '7': 'pqrs',
            '8': 'tuv',
            '9': 'wxyz'
        }

        answer = []

        def dfs(index, current):
            if index == len(digits):
                answer.append(current)
                return
            
            for letter in digit_letter[digits[index]]:
                dfs(index + 1, current + letter)
        
        dfs(0, "")

        return answer
