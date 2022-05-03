# int pow = 1;
# int x = 0;

# while (pow!=128) {
#     pow = pow * 2;
#     x = x + 1;  
# }

# $s0 = pow, $s1 = x

li $s0, 1
li $s1, 0

addi $t0, $0, 128

while: 
    beq $s0, $t0, done
    sll $s0, $s0, 1
    addi $s1, $s1, 1
    j while
done: