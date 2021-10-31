# Calculating average (Version 1)
li $8, 173
li $9, 191
addu $10, $8, $9
srl $10, $10, 1
li $v0, 36 #syscall 36 -- print unsigned int
move $a0, $10 # a0 = $10
syscall #print $10