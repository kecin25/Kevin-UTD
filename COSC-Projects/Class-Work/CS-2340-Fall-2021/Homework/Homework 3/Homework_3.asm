#Homework 3 by Kevin Boudreaux KCB180002
#BMI Index using Mips
.include "Macro.asm"
.data
height: .word 0
weight: .word 0
low_bmi: .double 18.5
normal_bmi: .double 25
high_bmi: .double 30
name: .space 64

.text
print_string("What is your name? ")	#asks user for name using Macro extenstion

li $v0, 8
la $a0, name
la $a1, 64
syscall		#syscall getting input for name

print_string("What is your hight in inches: ")
li $v0, 5
syscall		#syscall getting input for height
sw $v0, height

print_string("Now enter your weight in pounds (round to a whole number): ")
li $v0, 5
syscall		#syscall getting input for weight
sw $v0, weight

lw $t0, weight
li $t1, 703
mul $t0, $t0, $t1	#multipling weight and 703 togehter

mtc1.d $t0, $f0		#moving number from above into $f0
cvt.d.w $f0, $f0	#converting number into a double

l.d $f2, height		
cvt.d.w $f2, $f2	#coverting height from an int to a double
mul.d $f2, $f2, $f2	#squarring the height
div.d $f0, $f0, $f2	#dividing the weight*703 by the height squared

li $t0, 0	#loop counter
la $t2, name	#holds characters
li $t3, 10	#beq condition that checks for \n character
loop: 
add $t1, $t2, $t0	#moves pointer to next character in name
lb $t4, ($t1)		#coverts address to byte
beq $t4, $t3, loopEnd	#checks to see if currently looking at \n
addi $t0, $t0, 1	#adds one to counter
j loop

loopEnd:
sb $zero, ($t1)		#converts \n to \0

la $t0, name
print_name($t0)		#prints name

print_string(", your bmi is: ")		#prints BMI
print_double($f0)
print_string("\n")

ldc1 $f2, low_bmi		#checks to see if bmi is considered low
c.lt.d $f0, $f2
bc1t lowBMI

ldc1 $f2, normal_bmi		#checks to see if bmi is considered normal
c.lt.d $f0, $f2
bc1t normalBMI

ldc1 $f2, high_bmi		#checks to see if bmi is considered high
c.lt.d $f0, $f2
bc1t highBMI

print_string("This is considered obese. \n")		#if non the above then bmi is considered obese
j Exit

lowBMI:		#jumps here if bmi is low
print_string("This is considered underwieght. \n")
j Exit

normalBMI:	#jumps here if bmi is normal
print_string("This is considered normal weight. \n")
j Exit

highBMI:	#jumps here if bmi is high
print_string("This is considered overweight. \n")

Exit:	
li $v0, 10
syscall
