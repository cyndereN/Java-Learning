.data
    vals: .word 4,5,8,0
 
.text
    .global main
    
    main:
        la $t4, vals
        li $t2, 0
        lw $t1, 0($t4)
        add $t2, $t2, $t1
        lw $t1, 4($t4)
        add $t2, $t2, $t1
        lw $t1, 8($t4)
        add $t2, $t2, $t1
        sw $t2, 12($t4)
        
        lw $a0, 12($t4)
	    li $v0, 1
	    syscall 