#switch(k)
#{
# case 0:f=i+j;break;
# case 1:f=g+h;break;
# case 2:f=g-h;break;
# case 3:f=i-j;break;
# }

# first translate to if-else
# if-else
# if(k==0) f=i+j;
# else if(k==1) f=g+h;
# else if(k==2) f=g-h;
# else if(k==3) f=i-j;

# f：$s0;       g->$s1;       h->$s2;        i->$s3;        j->$s4;      k->$s5
    li $s5, 2
    bnez $s5, L1    # branch k!=0
    add $s0, $s3, $s4  # k==0, f = i+j
    j Exit

L1:
    addi $t0, $s5, -1  #$t0 = k-1
    bnez $t0, L2  # branch k!=1
    add $s0, $s1, $s2 # f=g+h
    j Exit
L2:
    addi $t0, $s5, -2 #t0 = k-2
    bnez $t0, L3 # branch k!=2
    sub $s0, $s1, $s2 # k==2, f=g-h
    j Exit
L3:
    addi $t0, $s5, -3
    bne $t0, $0, Exit # branch k!=3
    sub $s0, $s3, $s4  # k==3, f=i-j
Exit: