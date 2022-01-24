#Homework 5 by Kevin Boudreaux KCB180002
#Compression using Mips
#11/15/2021
#Instructions:
#	(if using MARS) Make sure the file you want to see compressed is in the same location as the MARS.exe
#	follow the prompts given in the console when running the program
.include "Macro.asm"
.data
file_location: .space 128
buffer: .space 1024
file: .asciiz "test.txt"			#file name
origional_file_size: .word 0
compressed_file_size: .word 0
p: .word 0					#pointer to heap
byte_holder: .word 0

.text
start:

	allocate_heap				#saves area in mem for heap
	print_str("file Name: ")	
	read_str				#reads user input and stores it in file
	file_fix				#removes the \n at the end of the user input
	file_open				#opens file and saves file descriptor in file_loaction
	file_read				#prints file into buffer
	file_close				#closes the file
	print_str("\nOriginal data: \n")
	file_print				#prints everything in the buffer
	get_origional_size			#gets the size of the file pre-compressed
	print_str("\nCompressed data: \n")
	compress				#compresses the file, storing it in the heap
	get_compressed_size			#gets the sized of the compressed file
	compressed_print			#prints the compressed file from the heap
	print_str("\nUnompressed data: \n")
	uncompressed_print			#uncompresses the file from the heap
	
	print_str("\nOrigional file size: ")
	lw $t0, origional_file_size
	print_int($t0)				#prints the file's origional size
	print_str("\nCompressed file size: ")
	lw $t0, compressed_file_size
	print_int($t0)				#prints the compressed file's size
	print_str("\n")	
	j start
error:
	print_str("error 404: file not found\n")
exit:
print_str("program ending")
li $v0, 10
syscall