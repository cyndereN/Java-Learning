.data
	message: .asciiz "After while loop is done, i = "
	space: .asciiz ", "
	
.text
	main:
		#i = 0
		addi $t0, $0, 0
		
		while:
		
			bgt $t0, 10, exit
			jal printMessage
			addi $t0, $t0, 1     #i++
			
			j while
			
		exit:
				li $v0, 4
				la $a0, message
				syscall
				
				li $v0, 1
				add $a0, $t0, $0
				syscall
		
			li $v0, 10
			syscall
		
	printMessage:
		li $v0, 1
		add $a0, $t0, $0
		syscall
		
		li $v0, 4
		la $a0, space
		syscall
		
		jr $ra
		
			