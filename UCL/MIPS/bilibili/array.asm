.data
	myArray:    .word 5:12
.text
    addi $s0, $0, 4
    addi $s1, $0, 10
    addi $s2, $0, 12
    
    # index = $t0
    addi $t0, $0, 0
    
    sw $s0, myArray($t0)
    addi $t0, $t0, 4
    sw $s1, myArray($t0)
    addi $t0, $t0, 4
    sw $s2, myArray($t0)
    addi $t0, $t0, 4
    
    