.data
dat: .word 0x48656c6c, 0x6f303030, 0x38212121 # Hello0008!!!
len: .word 3

.text
main:
	la $s0, dat     # s0 is the address of dat
	lw $s1, len      # s1 is the length of number array needed to test
 	la $s2, 0         # s2 stores index
	while:
		bge $s2, $s1, end
		mul  $s3, $s2, 4    
    	add $s3, $s0, $s3   #   calculate &dat[i] 
    	lw $a0, ($s3)   # load dat[i]
    	
    	jal parity
    	
		addi $s2, $s2, 1    #   i++
		j while
		
   	 parity:
   	 	addi $sp, $sp, -16  # allocate in the stack
   	 	sw $s2, 12($sp)    # save s2
		sw $s1, 8($sp)    # save s1
		sw $s0, 0($sp)    # save s0
		sw $ra, 4($sp)    # store current return address
   	 
   	   # main method, ^
   	 	srl $t0, $a0, 16
		xor $v0, $a0, $t0
		srl $t0, $v0, 8
		xor $v0, $v0, $t0
		srl $t0, $v0, 4
		xor $v0, $v0, $t0
		srl $t0, $v0, 2
		xor $v0, $v0, $t0
		srl $t0, $v0, 1
		xor $v0, $v0, $t0
		and $v0, $v0, 1
   	 
   	    move $a0, $v0
   	 	li $v0, 1
   	 	syscall
   	 
   	 	lw $s1, 8($sp)   #restore
   	 	lw $s0, 0($sp)
		lw $ra, 4($sp)
		lw $s2, 12($sp) 
		addi $sp, $sp, 16
		
		jr $ra		
		
	end:
		li $v0, 10  # terminate excecution
   	 	syscall 
   	 	
