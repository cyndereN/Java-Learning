# if branch

.data
	message: .asciiz "The number is less than the other"
	message2: .asciiz "Nothing happened"
	
.text
	main:
		addi $t0, $0, 25
		addi $t1, $0, 20
		
		slt $s0, $t0, $t1
		bnez $s0, numbersLess
		
		li $v0, 4
		la $a0, message2
		syscall
		
		li $v0, 10
		syscall
		
	numbersLess:
		li $v0, 4
		la $a0, message
		syscall