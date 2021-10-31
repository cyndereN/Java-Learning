// Integers represented
// as 32-bit signed words.

int a = 64;
int b = 305419896;
int c = a + b;

MIPS assembly code
# $s0 = a
addi $s0, $0, 0x0040
# $s1 = b
lui $s1, 0x1234
ori $s1, $s1, 0x5678
# Or pseudo-instruction
li $s1, 0x12345678
# $s2 = c
add $s2, $s0, $s1

//int array[5];
//array[0] = array[0] * 2;
//array[1] = array[1] * 2;
# array base address = $s0 (say array stored at 0x12348000)
lui $s0, 0x1234 # put 0x1234 in upper half of $s0
ori $s0, $s0, 0x8000 # put 0x8000 in lower half of $s0
lw $t1, 0($s0) # $t1 = array[0]
sll $t1, $t1, 1 # $t1 = $t1 * 2
sw $t1, 0($s0) # array[0] = $t1
lw $t1, 4($s0) # $t1 = array[1]
sll $t1, $t1, 1 # $t1 = $t1 * 2
sw $t1, 4($s0) # array[1] = $t1

//if (i == j)
//f = g + h;
//else
//f = f – i;

# $s0 = f, $s1 = g, $s2 = h
# $s3 = i, $s4 = j
bne $s3, $s4, L1
addu $s0, $s1, $s2
j done
L1: subu $s0, $s0, $s3
done:


// determines the power
// of x such that 2x = 128
//int pow = 1;
//int x = 0;
//while (pow != 128) {
//pow = pow * 2;
//x = x + 1;
//}

# $s0 = pow, $s1 = x
addi $s0, $0, 1
add $s1, $0, $0
addi $t0, $0, 128
while: beq $s0, $t0, done
sll $s0, $s0, 1
addi $s1, $s1, 1
j while
done:

// add the numbers
// from 0 to 9
//int sum = 0;
//int i;
//for (i=0; i!=10; i = i+1) {
//sum = sum + i;
//}
# $s0 = i, $s1 = sum
    addi $1 $0, 0
    add $s0, $0, $0
    addi $t0, $0, 10
for: beq $0, $t0, done
    add $s1, $s1, $s0
    addi $s0, $s0, 1
    j for
done: