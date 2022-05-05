.data
	message: .asciiz "Enter the value of PI: "
	zeroAsFloat: .float 0.0
	
	prompt: .asciiz "Enter the value of e: "

	userInput: .space 20
		
.text
    lwc1 $f4, zeroAsFloat
    
    # Display message
    li $v0, 4
    la $a0, message
    syscall
    
    li $v0, 6
    syscall # stored in $f0
    
    # Display value
    li $v0, 2
    add.s $f12, $f0, $f4
    syscall
    
    #----------------------------------------
    li $v0, 4
    la $a0, prompt
    syscall
    
    # get double
    li $v0, 7
    syscall
    
    li $v0, 3
    add.d $f12, $f0, $f4
    syscall
    
    #----------------------------------------
    # Get input as text
    li $v0, 8
    la $a0, userInput
    li $a1, 20   # length in bytes of input
    syscall
    
    # displays 
    li $v0, 4
    la $a0, userInput
    syscall
    
   	# terminate
   	li $v0, 10
   	syscall
    
    