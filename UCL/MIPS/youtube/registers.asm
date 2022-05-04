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
		
		# print value
		li $v0, 1
		move $a0, $s0
		syscall
		
	#This line is going to signal end of program
	li $v0, 10
	syscall
	
	increase_register:
		addi $sp, $sp, -4  # allocate 4 bytes in the stack
		sw $s0, 0($sp)    # save s0
		
		addi $s0, $s0, 30
		
		# print new value in funciton
		li $v0, 1
		move $a0, $s0
		syscall 
		
		lw $s0, 0($sp)
		addi $sp, $sp, 4
		
		jr $ra
		