# Calculator Version 2.0
# This does the same as Version 1.0,
# except now it works with bytes rather
# than 32-bit words.

	.data
vals:	.byte -5, 80, 80, 0

	.text
	.globl main
main:
	la  $4, vals	# Load $4 with the address of label 'vals'.
	add $8, $0, $0	# Set sum to zero
	lbu  $9, 0($4)	# Get first number from memory
	add $8, $8, $9  # Add to sum
	lbu  $9, 1($4)	# Get second number from memory
	add $8, $8, $9	# Add to sum
	lbu  $9, 2($4)	# Get third number from memory
	add $8, $8, $9	# Add to sum
	sb  $8, 3($4)  # Save the result to memory.

	li $v0 10       # Exit program using system call 10.
	syscall         # syscall 10 (exit)

