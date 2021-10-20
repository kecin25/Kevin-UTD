# Homework 4 - Bitmap
# By Kevin Boudreaux - KCB180002
# 10/18/2021
#Instructions:
# Go to Tools -> Bitmap Display
#	set pixal dim to 4x4
#	set display dim to 256x256
#
# Go to Tool -> Keyobard and Display MMIO Simulator
#	use w for up, d for left, s for down, a for left, and space to quite
# Connect to MIPS and run

#constants
.eqv WIDTH 64		#weidth && height = 256 / 4 =64
.eqv HEIGHT 64
.eqv MEM 0x10010000	#mem address of pixel (0,0)

#colors
.eqv RED 	0x00FF0000
.eqv GREEN 	0x0000FF00
.eqv BLUE	0x000000FF
.eqv WHITE	0x00FFFFFF
.eqv YELLOW	0x00FFFF00
.eqv CYAN	0x0000FFFF
.eqv MAGENTA	0x00FF00FF

	###Used Registers###
	# $a0 = x cordinate
	# $a1 = y cordinate
	# $a2 = color
	# $a3 = color tracker for loop
	# $s0 = tracker used to support color movement
	# $s1 = exact location to draw color
	# $t0 = loop bounds
	# $t1 = loop counter
	# $t2 = used to store $a0 when calling syscall 32 to make program sleep for 5 ms
	# $t3 = used to see if there is input that is needed to be addressed
	# $t4 = value of input
.text
main:
	###setting up starting position	###
	addi $a0, $0, WIDTH	#$a0 = x = WIDTH
	sra $a0, $a0, 1		#$a0 = $a0/2
	addi $a1, $0, HEIGHT	#$a1 = y = HEIGHT
	sra $a1, $a1, 1		#$a1 =$a1/2
	addi $a2, $0, RED	#$a2 = red
	li $a3, 0		#a3 = color tracker for loop
	li $s0, 0		#s0 = tracker to support color movement
	jal Start_Loop
finish_loop:
	li $v0, 10
	syscall
###############################################################################################
Start_Loop:
	li $t1, 0			#t1 = loop counter
	li $t0, 7			#t0 = counter bounds
LoopE:				
	addi $a0, $a0, 1		#adding 1 to mem location to go right
	addi $t1, $t1, 1		#adding 1 to counter
	jal Color_change		#updates color based on $a3 value
	addi $a3, $a3, 1		#adding 1 to color tracker
	bne $a3, 7, resetE		#checking to see if $a3 is out of bounds for color
	subi $a3, $a3, 7		#if so, puts it back in bounds by subtracking 7
resetE:
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exitE		#checking bounds of loop
	j LoopE				#looping back to start
exitE:
	li $t1, 0			#resetting loop counter
	addi $a3, $s0, 0		#reseting color tracker
LoopS:
	addi $a1, $a1, 1		#adding a1 to go down a row
	addi $t1, $t1, 1		#addign 1 to counter
	jal Color_change		#updates color based on $a3 value
	addi $a3, $a3, 1		#adding 1 to color tracker
	bne $a3, 7, resetS		#checking to see if $a3 is out of bounds for color
	subi $a3, $a3, 7		#if so, puts it back in bounds by subtracking 7
resetS:
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exitS		#checking bounds of loop
	j LoopS
exitS:
	li $t1, 0			#resetting loop counter
	addi $a3, $s0, 0		#reseting color tracker
LoopW:
	subi $a0, $a0, 1		#subtracting 1 to mem location to go right
	addi $t1, $t1, 1		#adding 1 to counter
	jal Color_change		#updates color based on $a3 value
	addi $a3, $a3, 1		#adding 1 to color tracker
	bne $a3, 7, resetW		#checking to see if $a3 is out of bounds for color
	subi $a3, $a3, 7		#if so, puts it back in bounds by subtracking 7
resetW:
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exitW		#checking bounds of loop
	j LoopW				#looping back to start
exitW:
	li $t1, 0			#resetting loop counter
	addi $a3, $s0, 0		#reseting color tracker
LoopN:
	subi $a1, $a1, 1		#subtracting 1 to go up a row
	addi $t1, $t1, 1		#addign 1 to counter
	jal Color_change		#updates color based on $a3 value
	addi $a3, $a3, 1		#adding 1 to color tracker
	bne $a3, 7, resetN		#checking to see if $a3 is out of bounds for color
	subi $a3, $a3, 7		#if so, puts it back in bounds by subtracking 7
resetN:
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exitN		#checking bounds of loop
	j LoopN
exitN:	
	addi $s0, $s0, 1		#adds 1 to $s0 (color movement tracker)
	bne $s0, 7, jump		#if it is not equal to 7 skips over resetting it to zero
	li $s0, 0
jump:
	###check for input###
	lw $t3, 0xffff0000 		#t3 holds if input available
	beq $t3, 0, Start_Loop		#if there is no input jump to start of loop
	
	lw $t4, 0xffff0004		#stores value of input
	beq $t4, 32, finish_loop	#input = space
	li $t1, 0			#resets counter
	li $a2, 0			#sets the color to black
	###erases the current box###
Erase_east:
	addi $a0, $a0, 1		#adding 1 to go right
	addi $t1, $t1, 1		#addign 1 to counter
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exit_east		#checking bounds of loop
	j Erase_east
exit_east:
	li $t1, 0			#resets counter
	li $a2, 0			#sets the color to black
Erase_south:
	add $a1, $a1, 1			#adding 1 to go down a row
	addi $t1, $t1, 1		#addign 1 to counter
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exit_south	#checking bounds of loop
	j Erase_south	
exit_south:
	li $t1, 0			#resets counter
	li $a2, 0			#sets the color to black
Erase_west:
	subi $a0, $a0, 1		#subtracting 1 to go left
	addi $t1, $t1, 1		#addign 1 to counter
	jal draw_pix			#drawing at new location
	beq $t1, $t0, exit_west		#checking bounds of loop
	j Erase_west
exit_west:
	li $t1, 0			#resets counter
	li $a2, 0			#sets the color to black	
Erase_north:
	subi $a1, $a1, 1		#subtracting 1 to go up a row
	addi $t1, $t1, 1		#addign 1 to counter
	jal draw_pix			#drawing at new location
	beq $t1, $t0, direction		#checking bounds of loop
	j Erase_north			
direction:				#checks to see which direction the square needs to move										
	beq $t4, 119, north		#input = w
	beq $t4, 100, east		#input = d
	beq $t4, 115, south		#input = s
	beq $t4, 97, west		#input = a
north:
	subi $a0, $a0, HEIGHT		#removes HEIGHT from a0 to go up a row
	j exit
east:
	addi $a0, $a0, 1		#adds 1 to a0 to go to the right
	j exit
south:
	addi $a0, $a0, HEIGHT		#adds HEIGHT to a0 to go down a row
	j exit
west:
	subi $a0, $a0, 1		#removes 1 from a0 to go to the left
exit:
	j Start_Loop			#jumps back to the start of the loop
###############################################################################################
Color_change:				#changes the color based on the $a3 value 
	beq $a3, 0, red			#if 0 color becomes red
	beq $a3, 1, green		#if 1 color becomes green
	beq $a3, 2, blue		#if 2 color becomes blue
	beq $a3, 3, white		#if 3 color becomes white
	beq $a3, 4, yellow		#if 4 color becomes yellow
	beq $a3, 5, cyan		#if 5 color becomes cyan
	beq $a3, 6, magenta		#if 6 color becomes magenta
red:
	addi $a2, $0, RED
	jr $ra
green:
	addi $a2, $0, GREEN
	jr $ra
blue:
	addi $a2, $0, BLUE
	jr $ra
white:
	addi $a2, $0, WHITE
	jr $ra
yellow:
	addi $a2, $0, YELLOW
	jr $ra
cyan:
	addi $a2, $0, CYAN
	jr $ra
magenta:
	addi $a2, $0, MAGENTA
	jr $ra
###############################################################################################
draw_pix:
	#s1 = address = MEM + 4(x + y*WIDTH)
	mul $s1, $a1, WIDTH		#$s1 = y * WIDTH
	add $s1, $s1, $a0		#$s1 += x
	mul $s1, $s1, 4			#getting word offset
	add $s1, $s1, MEM		#adding base MEM location
	sw $a2, 0($s1)			#storing the color at the memory location
	
	addi $t2, $a0, 0		#stores a0 into 0 to hold onto it while setting system sleep call
	li $a0, 5			#makes the program sleeps for roughly 5 ms
	li $v0, 32
	syscall
	addi $a0, $t2, 0		#restores value into a0 to continue running program
	jr $ra
