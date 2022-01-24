class LinkedList:
    def __init__(self, x=0, y=0, next=None):  # default constructor
        self.x = x  # self is the same as this in C++
        self.y = y
        self.next = next
    def details(self):
        print(self.x)

class Point(LinkedList):  # inheritance from LinkedList class
    pass  # pass means nothing happens, allows us creating class copies in inheritance


