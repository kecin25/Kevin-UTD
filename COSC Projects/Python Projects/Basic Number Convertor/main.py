def printing_conversion(string):
    words = string.split(' ')
    output = ""
    numbers = \
        {
            "one": 1,
            "two": 2,
            "three": 3,
            "four": 4,
            "five": 5,
            "six": 6,
            "seven": 7,
            "eight": 8,
            "nine": 9,
            "zero": 0,

            "1": "one",
            "2": "two",
            "3": "three",
            "4": "four",
            "5": "five",
            "6": "six",
            "7": "seven",
            "8": "eight",
            "9": "nine",
            "0": "zero"
        }
    for word in words:
        output += str(numbers.get(word, word)) + " "
    print(output)


def adding_numbers(numOne, numTwo, numThree):
    return numOne + numTwo + numThree


string = input(">")
printing_conversion(string)

print(adding_numbers(numThree=5, numOne=3, numTwo=2))  # key word arguments make it not matter what order you pass


