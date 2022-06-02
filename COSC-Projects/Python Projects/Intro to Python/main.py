year = 2022
print("year of being written:", year)

name = input("give me your name? ")
print("hello " + name + "\n")

birthYear = input("what year were you born? ")
age = 2022 - int(birthYear)
print("you are ", age, " years old\n")

# getting input and printing it out
print("adding two numbers.")
print("Sum: " + str(float(input("First: ")) + float(input("Second: "))) + "\n")
# if else statement
if "a" in name:
    print("you have an a in your name\n")
else:
    print("you do not have an a in your name\n")
# talking about math operators
print("to use mod, use //: 10//3 = " + str(10 // 3))
print("for normal division, use /: 10/3 = " + str(10 / 3))
print("to use exponents, use **: 10**3 = " + str(10 ** 3))
print("for normal multiplication, use *: 10*3 = " + str(10 * 3) + "\n")

# talking about logical operators and if statements
if 10 == 10 and 11 == 11:
    print("'==' works to compare. instead of doing && use 'and', 'or' is used instead of ||, 'not' is used as ! ")
    print("as long as the code is implemented it will run in a if statement instead of using {}")
    print("elif is used as else if \n")

# weight converter

weight = int(input("Weight: "))
type = str(input("(K)g or (L)bs: "))

if type == 'l' or type == 'L' or type == "lbs" or type == "Lbs":
    pounds = 1
elif type == 'k' or type == 'K' or type == "kg" or type == "Kg":
    pounds = 2
else:
    pounds = 0
if pounds == 1:
    print("Weight in Kg: " + str(weight * .45) + "\n")
elif pounds == 2:
    print("Weight in Lbs: " + str(weight / .45) + "\n")
else:
    print("please enter k or l next time\n")

i = 0
while i < 50:
    j = 0
    for j in range(50 - i // 2):
        print(" ", end="")  # end means it prints without a new line character
    for j in range(i):
        print("*", end="")
    print()
    i = i + 1

i = 1
while i <= 1_000:  # _ not needed but helps readability
    print(i)
    i = i + 1

i = 1
while i < 10:
    print(i * '*')  # same as above except no need for the end. doing this prints out the char or str as many times i.
    i = i + 1

list = ["one", "two", "three", "four", "five"]

print(list)
print(list[-1])  # python won't give an error if out of bounds but instead role to the other end of the list and
# continue counting
print(list[1:3])  # 1:3 prints index starting at 1 and goes up to but excludes the ending index of 3
i = 0
for i in range(len(list)):  # len is used to get length of a list
    print(list[i])
    i = i + 1
list.append("six")
print(list)

list.insert(3, "three point five")  # inserts given str into position 3, kicking everything one to the right
print(list)
list.append("one")
list.remove("one")  # removes the first 'one' found
print(list)

if "one" in list:  # in is used to check to see if something is in a list
    list.clear()  # empties the list

for counter in list:
    print(counter)  # counter holds everything in the list one object at a time

numbers = range(5, 10, 2)  # Store numbers from the first num going up to but excluding the second num, 3rd value is
# used to step, ex 2 num at a time
print(numbers)
for num in numbers:
    print(num)

numbers = (1, 2, 3)  # () represents tuples which means it can not be changed
x = numbers[0]
y = numbers[1]
z = numbers[2]

x, y, z = numbers  # same as 3 lines above but in one line

# classes in Python are called Dictionaries
customer = \
    {
        "name": "John Smith",
        "age": 30,
        "isVerified": True
    }
print(customer.get("name"))
print(customer.get("birthdate"))
print(customer.get("birthdate", "Jan 1 1980"))  # Jan 1 1980 acts a default value if none is found
customer["birthdate"] = "Jan 5th 1995"
try:
    age = int(input("Age: "))
    income = 20000
    risk = income / age
    print(age)
except ZeroDivisionError:
    print("age can not be zero")
except ValueError:
    print("please enter a whole number")

import File2  # imports the 2nd file

# can also import single functions by doing "from (fileName) import (functionName)

point1 = File2.Point(10, 10)
point1.z = 10  # creates an integer 'z' that has the value of 10 in point1
point1.x = 11
point1.details()
