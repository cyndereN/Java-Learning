# int f, g, y; //global variableSet
# int main(void){
#    f = 2; g = 3; y = sum(f,g);
#    return y;    
# }

# int sum(int a, int b){
#     return a + b;   
# }

.data
	f:
	g:
	y:

.text
	main:
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		
		addi $a0, $0, 2
		sw $a0, f
		
		addi $a0, $0, 3
		sw $a0, g
		
		jal sum
		
		sw $v0, y
		
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		
		jr $ra    # return to os
		
	sum:
		add $v0, $a1, $a0
		jr $ra
		