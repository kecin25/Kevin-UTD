#HomeWork 1 by Kevin Boudreaux KCB180002 8/30/2021

.data 			#storage for variables
	a: .word 0
	b: .word 0
	c: .word 0
	output1: .word 0
	output2: .word 0
	output3: .word 0
	name: .space 20
	msg1: .asciiz "What is your Name? "
	msg2: .asciiz "Enter a number between 1-100: "
	msg3: .asciiz "Your lucky numbers are: "
	space: .asciiz " "

.text			#core of code
	#runs message 1
	li $v0, 4
	la $a0, msg1
	syscall 	
	
	#gets name from user
	la $a0, name
	li $a1, 20
	li $v0, 8
	syscall
	
	#asking for first number from user
	la $a0, msg2
	li $v0, 4
	syscall
	
	#getting first number from user
	li $v0, 5
	syscall
	sw $v0, a
	
	#asking for second number from user
	la $a0, msg2
	li $v0, 4
	syscall
	
	#getting second number from user
	li $v0, 5
	syscall
	sw $v0, b
	
	#asking for third number from user
	la $a0, msg2
	li $v0, 4
	syscall
	
	#getting third number from user
	li $v0, 5
	syscall
	sw $v0, c
	
	#setting up $t1, $t2, $t3 for finding solutions
	lw $t1, a
	lw $t2, b
	lw $t3, c
	
	#finding solution 1
	add $t0, $t1, $t1
	sub $t0, $t0, $t3
	addi $t0, $t0, 4
	sw $t0, output1
	
	#finding solution 2
	sub $t0, $t2, $t3
	add $t0, $t0, $t1
	addi $t0, $t0, -2 
	sw $t0, output2
	
	#finding solution 2
	addi $t0, $t1, 3
	sub $t0, $t0 $t2
	addi $t0, $t0, 1
	add $t0, $t0, $t3
	addi $t0, $t0, 3
	sw $t0, output3
	
	#resetting $a0 to given name
	la $a0, name
	
	#saying users name
	li $v0, 4
	syscall
	
	#printing msg3
	la $a0, msg3
	la $v0, 4
	syscall
	
	#printing calculated numbers
	lw $a0, output1
	la $v0, 1
	syscall
	
	la $a0, space
	la $v0, 4
	syscall
	
	lw $a0, output2
	la $v0, 1
	syscall
	
	la $a0, space
	la $v0, 4
	syscall
	
	lw $a0, output3
	la $v0, 1
	syscall
	
exit:	#exits the program safely
	li $v0, 10
	syscall
	
#	Test Case 1

#	What is your Name? Kevin
#	Enter a number between 1-100: 1
#	Enter a number between 1-100: 2
#	Enter a number between 1-100: 3
#	Kevin
#	Your lucky numbers are: 3 -2 9

#	Test Case 2

#	What is your Name? Boudreaux
#	Enter a number between 1-100: 33
#	Enter a number between 1-100: 72
#	Enter a number between 1-100: 89
#	Kevin
#	Your lucky numbers are: -19 14 57
