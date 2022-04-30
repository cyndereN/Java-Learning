# scanf("%d", &a);  $t0 : a
# scanf("%d", &b);  $t1 : b
# if (a>b) { print ("Yes"); }
# else print("No");
.data
    msg_yes:    .ascii    "Yes\0"   #注意要加\0, 不然print yes会一直读，把no也打出来
    msg_no:     .ascii    "No\0"

.text
li $v0, 5   # read int, store in v0
syscall 
move $t0, $v0  # move from v0 to t0

li $v0, 5
syscall 
move $t1, $v0

bgt $t0, $t1, sub1  # 如果t0 > t1, 跳到sub1的标识符

la $a0, msg_no
li $v0, 4       # store 4 in v0 --> print string
syscall

li $v0, 10  # terminate excecution
syscall 

sub1:
# printf("Yes");
la $a0, msg_yes 
li $v0, 4       # store 4 in v0
syscall
