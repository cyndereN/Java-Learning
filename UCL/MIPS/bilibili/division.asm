.data

.text
    addi $t0, $0, 30
    addi $t1, $0, 8
    
    div $s0, $t0, $t1
    
    div $s0, $t0, 10
    
    div $t0, $t1
    mflo $s0 # Quotient
    mfhi $s1 # Remainder
    
    
    #print
    li $v0, 1
    add $a0, $0, $s0
    syscall