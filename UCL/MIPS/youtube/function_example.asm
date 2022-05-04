# #include <stdio.h>

# int sum_product(int a, int b);
# int product(int x, int y);

# int main(void) {
#     int z = sum_product(10, 12);
#     printf("%d\n", z);
#     return 0;
# }

# int sum_product(int a, int b) {
#    int p = product(6, 7);
#     return p + a + b;
# }

# int product(int x, int y) {
#     return x * y;
# }

.text
main:
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	li $a0, 10
	li $a1, 12
	jal sum_product
	
	move $a0, $v0
	li $v0, 1
	syscall
	
	li $a0, '\n'
	li $a0, 11
	syscall
	
	lw $ra, 0($sp)
	add $sp, $sp, 4
	
	li $v0, 0
	jr $ra
	
sum_product:
	addi $sp, $sp, -12
	sw $ra, 8($sp)
	sw $a1, 4($sp)
	sw $a0, 0($sp)
	
	li $a0, 6
	li $a1, 7
	jal product
	
	lw $a1, 4($sp)
	lw $a0, 0($sp)
	
	add $v0,$v0, $a0
	add $v0,$v0, $a1 # put result in v0 to be returned
	
	lw $ra, 8($sp)
	addi $sp, $sp, 12
	
	jr $ra
	
product:
	mul $v0, $a0, $a1
	jr $ra
	
	