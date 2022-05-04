.data
	numbers: .word 0:500
	new_line: .asciiz"\n"
.text
	main:

		li   $v0, 5         #   scanf("%d", &numbers[i]);
    		syscall 

		la   $s0, numbers
		sw   $v0, 0($s0)
		
		# print new line
		li $v0, 4
		la $a0, new_line
		syscall
	
		jal collatz_function
	
	

	li $v0, 10  # terminate excecution
	syscall
	
	collatz_function:
		addi $sp, $sp, -8
		sw $s0, 0($sp)
		sw $ra, 4($sp)
		
		li $t0, 0
		la $t2, numbers
		while:
			mul $t1,$t0, 4  
			add $t3, $t1, $t2  # address of numbers[i]
			lw $t4, ($t3) 
			
			move $a0, $t4
			jal print_value
			
			beq $t4, 1, done
			
			addi $t0, $t0, 1
			mul $t1,$t0, 4  
			add $t3, $t1, $t2  # address of numbers[i]
			
			
			li $t6, 2
			div $t4, $t6
			mfhi $t5
			beqz $t5, even
			# odd
			mul $t4, $t4, 3
			addi $t4, $t4, 1
			sw $t4, ($t3)
			j while
		even:
			div $t4, $t4, 2
			sw $t4, ($t3)
			j while
		done:
			lw $s0, 0($sp)
			lw $ra, 4($sp)
			addi $sp, $sp, 8
			jr $ra
	
	print_value:
		li $v0, 1
		syscall
		
		li $v0, 11
		li $a0, ' '
		syscall
		
		jr $ra