# Compute up to 500 number following the input number in its Collatz sequence
 .data
output: .word 0 : 500 # "array" of 500 values for the output
size: .word 500 # size of "array" 
 .text
 
 la $t0, output # load address of output array
 la $t5, size # load address of size variable
 lw $t5, 0($t5) # load array size
 li $v0, 5 # MARS call number 5: read int
 syscall # return value in $v0
 move $t1, $v0 # store initial value in t1
 li $t6, 1 # $t6 = 1
 li $t7, 3 # $t7 = 3 
 sw $t1, 0($t0) # a[0] = input number
 addi $t1, $t5, -1 # Counter for loop, will execute (size-2) times
loop: 
 lw $t3, 0($t0) # Get value from array a[n] into $t3
 andi $t2, $t3, 1 # $t2 = t3 % 2
 beq $t2, $t6, odd # if (t2 == 1) , i.e., if a[n] was an odd number #even:
 srl $t2, $t3, 1 # $t2 = $t3 / 2 = a[n] / 2
 j updated 
odd: 
 multu $t3, $t7 # compute t3 * 3 = a[n] * 3
 mflo $t3 # move the result to $t3, i.e., $t3 = a[n] * 3
 addi $t2, $t3, 1 # Set $t2 = $t3 + 1 = a[n] * 3 + 1 
updated: # Done computing ($t2 = a[n] / 2) or ($t2 = a[n] * 3 + 1) depending on whether a[n] was even
 sw $t2, 4($t0) # Store a[n+1] in array
 addi $t0, $t0, 4 # increment address 
 addi $t1, $t1, -1 # decrement loop counter
 beq $t2, $t6, done
 bgtz $t1, loop # repeat if not finished yet.
done: 
 la $a0, output # first argument for print (array)
 subu $a1, $t5, $t1 # compute the output size
 jal print # call print routine. 
 li $v0, 10 # system call for exit
 syscall # we are out of here.
######### routine to print the numbers on one line. 
 .data
space:.asciiz " " # space to insert between numbers
head: .asciiz "The Collatz sequence is:\n"
 .text
print:
 add $t0, $zero, $a0 # starting address of array
 add $t1, $zero, $a1 # initialize loop counter to array size
 la $a0, head # load address of print heading
 li $v0, 4 # specify Print String service
 syscall # print heading
out: 
 lw $a0, 0($t0) # load next Collatz number for syscall
 li $v0, 1 # specify Print Integer service
 syscall # print Collatz number
 la $a0, space # load address of spacer for syscall
 li $v0, 4 # specify Print String service
 syscall # output string
 addi $t0, $t0, 4 # increment address
 addi $t1, $t1, -1 # decrement loop counter
 bgtz $t1, out # repeat if not finished
 jr $ra # return