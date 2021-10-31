# Summing up all squares of 0,...,100
add $8, $0, $0 #$8 will hold the sum
addi $9, $0, 100 #We'll do $8 += ($9)^2 for $9 = 100, 99, ...
sum_loop:
mult $9, $9
mflo $10
add $8, $10, $8
addi $9, $9, -1
bne $9, $0, sum_loop

li $v0, 1 #syscall 1 -- print int
move $a0, $8 # a0 = $8
syscall #print $8