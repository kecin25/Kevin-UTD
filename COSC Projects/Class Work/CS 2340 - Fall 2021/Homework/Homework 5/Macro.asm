 .macro allocate_heap
 	li $v0, 9
 	li $a0, 1024			#creates heap size of 1024 bits
 	syscall
 	sw $v0, p
 .end_macro
  ################################################################
  .macro print_int (%x)
	li $v0, 1
	add $a0, $zero, %x
	syscall
.end_macro
################################################################
.macro print_str (%str)
    .data
	macro_str:	.asciiz %str
    .text
    	li	$v0, 4
    	la	$a0, macro_str
    	syscall
.end_macro
################################################################
.macro print_char(%x)
	li $v0, 11
	la $a0, (%x)
	syscall
.end_macro
################################################################
.macro read_str 
	li $v0, 8
	la $a0, file			#reading input into file 
	la $a1, 128			#reads up to 128 bits
	syscall
	lb $t0, ($a0)			#if what was entered is empty
	beq $t0, 10, exit
.end_macro
################################################################
.macro file_open
	li $v0, 13
	la $a0, file
	li $a1, 0
	li $a2, 0
	syscall
	ble $v0, -1, error		#if error opening file print out error
	sw $v0, file_location		#saves file descriptor
.end_macro
################################################################
.macro file_read
	li $v0, 14
	lw $a0, file_location		#file descriptor
	la $a1, buffer			#address of buffer from which to read
	li $a2, 1024			#buffer length (how much to read)
	syscall
.end_macro
################################################################
.macro file_close
	li $v0, 16
	la $a0, file
	syscall
.end_macro
################################################################
.macro file_fix
	li $t1, 0 			#counter
	la $t0, file 			#address of file
loop:
	addi $t2, $t1, 0 		#i=i+1
	add $t2, $t2, $t0		#address = string[0] + i
	lb $t3, ($t2)
	beq $t3, 10, found		#found endline character from input
	addi $t1, $t1, 1
	j loop
found:
	li $t4, 0
	sb $t4, ($t2)
.end_macro
 ################################################################
.macro file_print
	li $v0, 4
	la $a0, buffer
	syscall
.end_macro
 ################################################################
 .macro get_origional_size
 	li $t1, 0 			#counter
 	la $t0, buffer			#address of the buffer
 loop1:
 	addi $t2, $t1, 0 		#i=i+1
	add $t2, $t2, $t0		#address = string[0] + i
	lb $t3, ($t2)			#loads bit of current location
	beq $t3, $zero, endloop1	#checks to see if at end of input from file
	addi $t1, $t1, 1		#adds one to counter
	j loop1
endloop1:
	sw $t1, origional_file_size
.end_macro
 ################################################################
  .macro compress
 	 la $t1, buffer
 	 lw $t2, p			#starting point of heap
 	 li $t0, 1 			#letter counter
 	 li $t6, 0			#true counter
 	 lw $t3, origional_file_size	#size of the file
 loop:	bge $t6, $t3, compress_end	#for loop that ends when reaching file size
 	lb $t4, ($t1)			#loads first character
 	sb $zero, ($t1)			#replaces character with \0 since it was already read
 	lb $t5, 1($t1)			#loads second character
 	sb $zero, ($t1)			#replaces character with \0 since it was already read
 	bne $t4, $t5, dif		#if the two characters are different go to dif
 	addi $t1, $t1, 1		#if characters are the same add one to position in buffer
 	addi $t0, $t0, 1		#add one to letter counter
 	addi $t6, $t6, 1		#add one true counter
 	j loop
 dif:
 	sb $t4, ($t2)			#if characrers are differnet, store the first character into heap
 	addi $t2, $t2, 1		#move heap over by one bit
 	sb $t0, ($t2)			#store the number that represents the amount of times the character shows up in a row
 	addi $t2, $t2, 1		#move heap over by one bit
 	li $t0, 1			#reseat the letter counter
 	addi $t6, $t6, 1		#add one to true counter
 	addi $t1, $t1, 1		#add one to buffer position
 	j loop
 compress_end:	
 .end_macro
  ################################################################
 .macro get_compressed_size
 	li $t1, 0 			#counter
 	lw $t4, p
 	la $t0, ($t4)			#address of pointer
 loop1:
 	addi $t2, $t1, 0 		#i=i+1
	add $t2, $t2, $t0		#address = string[0] + i
	lb $t3, ($t2)			#gets bit at address
	beq $t3, $zero, endloop1	#if the bit is zero end the loop
	addi $t1, $t1, 1		#else add one to counter
	j loop1
endloop1:
	sw $t1, compressed_file_size	#store counter into compressed_file_size
.end_macro
  ################################################################
  .macro compressed_print
  	li $t1, 0 			#counter
  	lw $t4, p
  	la $t0, ($t4)			#address of pointer
  	lw $t2, compressed_file_size	#how many itterations the loop will run
loop2:
	beq $t1, $t2, endloop2		#if counter is equal to size of compressed file then end loop
	addi $t1, $t1, 2		#add one to counter
	lb $t3, ($t0)			#load bit from current address
	addi $t0, $t0, 1		#move address position over by 1
	lb $t5, ($t0)			#load current bit from address
	sb $t5, byte_holder		
	lw $t5, byte_holder		#converts the bit into a word
	print_char($t3)			#prints character
	print_int($t5)			#prints number of times the character appears in order
	addi $t0, $t0, 1		#adds one to the counter
	j loop2
endloop2:  	
.end_macro
################################################################
  .macro uncompressed_print
  	lw $t0, p			
  	lb $t2, ($t0)			#address of pointer
loop3:
	beq $t2, $zero, endloop3
	li $t1, 0			#counter
  	lb $t2, ($t0)			#gets bit at current address
  	sb $zero, ($t0)			#replaces bit at current address with \0 to clean heap
  	addi $t0, $t0, 1		#moves address position over by one
  	lb $t3, ($t0)			#gets bit at current address 
  	sb $zero, ($t0)			#replaces bit at current address with \0 to clean heap
  	addi $t0, $t0, 1		#moves address position over by one
loop4:
	beq $t3, $t1, loop3		#loops the number of times the character appears in order
	print_char($t2)			#print character
	addi $t1, $t1, 1		#add one to loop counter
	j loop4
endloop3:
  .end_macro
