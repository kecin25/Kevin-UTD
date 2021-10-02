#HomeWork 2 by Kevin Boudreaux KCB180002 9/15/2021

.data		#storage for variables
	input: .space 128
	WordCount: .word 0
	CharacterCount: .word 0
	words: .asciiz " words "
	Characters: .asciiz " characters "
	msg1: .asciiz "Enter a sentence: "
	goodbye: .asciiz "Program ending"
	endLine: .word 10
.text

start:	li $v0, 54		#runs message 1 in a popout box with the input from user going into input
	la $a0, msg1
	la $a1, input
	la $a2, 128
	syscall
	
	
	la $s1, input		#putting input into s1 and checking to see if it is empty
	lb $t0, ($s1)		#grabbing first character of the string
	bnez $t0, output	#if not empty go to output stage
	
	li $v0, 59		#if the string is empty the program goes here
	la $a0, goodbye
	syscall
	li $v0, 10
	syscall
	
	
output:	jal counter
	
	la $a0, input		#printing out sentence
	li $v0, 4
	syscall
	
	lw $a0, WordCount	#printing out num of words and characters as well as 2 endline characters to make output organized
	la $v0, 1
	syscall
	la $a0, words
	la $v0, 4
	syscall
	lw $a0, CharacterCount
	la $v0, 1
	syscall
	la $a0, Characters
	la $v0, 4
	syscall
	la $a0, endLine
	la $v0, 4
	syscall
	la $a0, endLine
	la $v0, 4
	syscall
	
	
	sw $zero, WordCount	#reseating word count, characer count, and input to prevent over counting and being stuck in a infinte loop
	sw $zero, CharacterCount
	sw $zero, input
	
	j start			#goes back to the start for a new sentence
	
	
	

counter:			#s1 is address of given string
	li $t5, ' '		#used to check to see if current location is a space
	li $t3, 1		#used to keep track of number of words
	li $t4, 0		#used to keep track number of characters
	li $t6, 0 		#t6 is the counter
	li $t9, 10		#t10 is used as a breakpoint checker to keep the loop from going out of bounds
	
loop:	addi $t1, $t6, 0 	#i = i + 1
	add $t1, $t1, $s1	#address = string[0] + i
	lb $t0, ($t1) 		#get character from string address
	
	beq $t0, $t9, loopEnd 	#checks to see if at end of line
	
	bne $t0, $t5, noSpace 	#if current character is not a space jump to no space
	addi $t3, $t3, 1	#if current character is a space add 1 to word counter	
	
noSpace: addi $t4, $t4, 1	#add one to character counter

	addi $t6, $t6, 1	#add one to loop counter
	j loop
	
	
loopEnd:
	sw $t3, WordCount	#stores word counter into words
	sw $t4, CharacterCount	#stores character counter into characters
	jr $ra			#jumps back to output
	
