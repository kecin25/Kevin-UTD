.macro print_int (%x)
li $v0, 1
add $a0, $zero, %x
syscall
.end_macro

.macro print_float (%x)
li $v0, 2
mov.s $f12, %x
syscall
.end_macro

.macro print_double (%x)
li $v0, 3
mov.d $f12, %x
syscall
.end_macro

.macro print_string (%str)
.data
macro_str: .asciiz %str
.text
li $v0, 4
la $a0, macro_str
syscall
.end_macro

.macro print_name ($str)
#.data
#macro_str: .asciiz $str
.text
li $v0, 4
la $a0, ($str)
syscall
.end_macro