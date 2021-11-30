lockit:
    ll $s2, 0($s1)      # Load linked to get lock value.
    bne $s2, $ lockit   # Spin if not got lock = 0.
    addiu s2, $0, 1     # Set lock to 1.
    sc $s2, 0($s1)      # Store conditional lock value.
    beq $s2, $0, lockit # Try again if store conditional
                        # failed (i.e. not atomic).