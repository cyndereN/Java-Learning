.data 
	newLine: .asciiz"\n"

.text
	main:
		# t is for callee, temporary
		addi $s0, $0, 10
		
		jal increase_register
		
		# print new line
		li $v0, 4
		la $a0, newLine
		syscall
		
		jal print_value
		# print value
		# li $v0, 1
		# move $a0, $s0
		# syscall
		
	#This line is going to signal end of program
	li $v0, 10
	syscall
	
	increase_register:
		addi $sp, $sp, -8  # allocate 4 bytes in the stack
		sw $s0, 0($sp)    # save s0
		sw $ra, 4($sp)    # store current return address
		
		addi $s0, $s0, 30
		
		# nested procedure
		jal print_value 
		
		lw $s0, 0($sp)
		lw $ra, 4($sp)
		addi $sp, $sp, 4
		
		jr $ra
	
	print_value:
		# print new value in s0
		li $v0, 1
		move $a0, $s0
		syscall 
		
		jr $ra 
	
	