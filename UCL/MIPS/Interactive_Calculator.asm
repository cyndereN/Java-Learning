# Interactive calculator – add 2 numbers together
.data
str1: .asciiz “\nEnter first value: “ # Note initial newline.
str2: .asciiz "Enter second value: “
str3: .asciiz "Sum is equal to “
.text
.globl main
main: addi $v0, $0, 4 # Set system call code (print_string)
la $a0, str1 # Load address of string into $a0.
syscall # Print string.
addi $v0, $0, 5 # Set system call code (read_int)
syscall # Read integer into $v0.
add $t1, $0, $v0 # Store integer in temporary register.
addi $v0, $0, 4 # Set system call code (print_string)
la $a0, str2 # Load address of string into $a0.
syscall # Print string.
addi $v0, $0, 5 # Set system call code (read_int)
syscall # Read integer into $v0.
add $t1, $t1, $v0 # Add newly entered value to previous.
addi $v0, $0, 4 # Set system call code (print_string)
la $a0, str3 # Load address of string into $a0.
syscall # Print string.
addi $v0, $0, 1 # Set system call code (print_int)
add $a0, $0, $t1 # Print result.
syscall # Call the system service.
li $v0 10 # Exit program using system call 10.
syscall # syscall 10 (exit)