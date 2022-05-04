.data
	message: .asciiz "Hi, I am CC.\n"

.text
	main:
		jal display_message
		# Tell the system that the program is done
		
		
		addi $a1, $0, 50   #first argument
		addi $a2, $0, 100
		jal add_numbers
		
		li $v0, 1
		move $a0, $v1
		syscall
		
		# terminate
		li $v0, 10
		syscall
	
	display_message:
		li $v0, 4
		la $a0, message
		syscall
		
		jr $ra
		
	add_numbers:
		add $v1, $a1, $a2
		
		jr $ra