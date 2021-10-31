# Calculating average (Version 2)
li $8, 3501006752
li $9, 794337423
and $10, $8, $9
xor $11, $8, $9
srl $11, $11, 1
add $10, $10, $11
li $v0, 36 #syscall 36 -- print unsigned int
move $a0, $10 # a0 = $10
syscall #print $10