# abs_diff(a, b) calculates the difference between 2 integers

abs_diff:
    sub $v0, $a0, $a1
    bgez $v0, return
    sub $v0, $al, $a0
return:
    jr $ra