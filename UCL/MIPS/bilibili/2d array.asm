.data
	mdArray:  .word 2, 5, 0
					.word 3, 7, 0
					.word 1,4, 11
	size: .word 3
	.eqv DATA_SIZE 4
	
.text
	main:
		la $a0, mdArray
		lw $a1, size
		
		jal sumDiagonal
		
		move $a0, $v0  # v0 has the sum
		li $v0, 1
		syscall
		
		li $v0, 10
		syscall
		
	sumDiagonal:
		li $v0, 0
		li $t0,0
		
		sumLoop:
			mul $t1, $t0, $a1  # t1 = rowIndex * column
			add $t1, $t1, $t0   #                                     + index
			mul $t1, $t1, DATA_SIZE #                            * DATA_SIZE
			add  $t1, $t1, $a0          # + base address
			
			lw $t2, ($t1)
			add $v0, $v0, $t2
			
			addi $t0, $t0, 1
			blt $t0, $a1, sumLoop
		
		jr $ra
		
			