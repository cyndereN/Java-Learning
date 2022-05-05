.data

.text
    li $s0, 10
    li $s1, -4
    addi $s2, $0, 2000
    
    mul $t0, $s0, $s1
    
    mult $s0, $s2
    mflo $t0
    
    sll $t0, $s1, 3  # *2^3 => *8
    
    li $v0, 1
    add $a0, $0, $t0
    syscall
    